<template>
  <div ref="map" />
</template>

<script>
import MarkerClusterer from "@googlemaps/markerclustererplus";
import {MAP_TYPES} from "../../constants/OPTIONS.js";

export default {
  name: "Heatmap",
  props: {
    center: {type: Object, default: () => { return {lat: 40.730610, lng: -73.935242}; }},
    zoom: {type: Number, default: () => 13},
    points: {type: Array, default: () => []}, // The points to generate the heatmap from
    markers: {type: Array, default: () => []}, // The markers to place on the map
  },
  data() {
    return {
      map: null, // Google Map object
      heatmap: null, // Heatmap layer
      map_markers: [], // Map marker objects
      marker_clusterer: null, // Marker clusterer instance
      neighbourhoods_layer: null,
    };
  },
  computed: {
    /**
     * The selected property ID
     */
    selected_id() {
      return this.$store.getters.selectedId;
    },

    /**
     * Google Maps API instance
     */
    maps_api() {
      return this.$store.getters.mapsApi;
    },

    /**
     * Whether to show the markers
     */
    show_markers() {
      return this.$store.getters.showMarkers;
    },

    /**
     * Whether to show the heatmap
     */
    show_heatmap() {
      return this.$store.getters.showHeatmap;
    },

    /**
     * Whether to show neighbourhood borders
     */
    show_neighbourhoods() {
      return this.$store.getters.showNeighbourhoods;
    },

    /**
     * Pixel radius of heatmap points
     */
    heatmap_radius() {
      const base_radius = this.$store.getters.heatmapRadius;
      return this.map_type === MAP_TYPES.PROPERTY ? base_radius * 3 : base_radius;
    },

    /**
     * The points to construct the heatmap from
     */
    heatmap_points() {
      return this.points.map(
        (point) => new this.maps_api.LatLng(point.lat.toFixed(6), point.lng.toFixed(6)),
      );
    },

    /**
     * The heatmap's type
     */
    map_type() {
      return this.$store.getters.mapType;
    },
  },

  watch: {
    /**
     * Animate corresponding marker on ID change
     */
    selected_id() {
      // Remove animation on all markers
      this.map_markers.forEach((marker) => {
        marker.setAnimation(null);
      });

      const selected_marker = this.map_markers.find((marker) => marker.id === this.selected_id);
      if (selected_marker) {
        if (selected_marker.getAnimation() !== null && selected_marker.getAnimation() !== undefined) {
          selected_marker.setAnimation(null);
        } else {
          selected_marker.setAnimation(this.maps_api.Animation.BOUNCE);
        }
      }
    },

    /**
     * When center changes, move map accordingly
     */
    center() {
      this.map.setCenter(this.center);
    },

    /**
     * When zoom changes, zoom map accordingly
     */
    zoom() {
      this.map.setZoom(this.zoom);
    },

    /**
     * When points change, update heatmap
     */
    heatmap_points() {
      this.heatmap.setData(this.heatmap_points);
    },

    /**
     * When show_markers property changes, update all markers
     */
    show_markers(show) {
      this.setMarkerVisibility(show);
    },

    /**
     * When show_heatmap property changes, update heatmap visibility
     */
    show_heatmap(show) {
      this.setHeatmapVisibility(show);
    },

    /**
     * When show_neighbourhoods property changes, update layer visibility
     */
    show_neighbourhoods(show) {
      this.neighbourhoods_layer.setMap(show ? this.map : null);
    },

    /**
     * When markers change, remove and place new ones
     */
    markers() {
      this.removeMarkers();
      this.placeMarkers(this.markers);
    },

    /**
     * When radius changes, update heatmap
     */
    heatmap_radius() {
      this.heatmap.set("radius", this.heatmap_radius);
    },
  },

  mounted() {
    const map_element = this.$refs.map;

    // Options for map
    const map_options = {
      zoom: this.zoom,
      center: this.center,
      mapTypeId: "roadmap",
      mapTypeControl: true,
      streetViewControl: false,
      fullscreenControl: false,
    };

    // Create map
    this.map = new this.maps_api.Map(map_element, map_options);

    this.fillMap();
  },
  methods: {
    /**
     * Fills the stored map with neighbourhood and heatmap layers and adds markers
     */
    fillMap() {
      // Add neighbourhood layer
      this.neighbourhoods_layer = new this.maps_api.KmlLayer({
        url: "https://www.google.com/maps/d/kml?forcekml=1&mid=1-_H_BR22bTWqVXbBX6FcRwNF4mpKKR4x",
        map: this.show_neighbourhoods ? this.map : null,
        preserveViewport: true,
        suppressInfoWindows: true,
        clickable: false,
        zIndex: 1,
      });

      // Options for heatmap overlay
      const heatmap_options = {
        data: this.heatmap_points,
        map: this.map,
        radius: this.heatmap_radius,
      };

      // Add heatmap to map and store locally
      this.heatmap = new this.maps_api.visualization.HeatmapLayer(heatmap_options);

      // Place markers
      this.placeMarkers(this.markers);
    },

    /**
     * Places the given markers on the map
     * @param {Array} markers
     * @example
     * // Places two markers on the map
     * placeMarkers([{lat: 12.3, lng: 3.45}, {lat: -3, lng: 4.562}])
     */
    placeMarkers(markers) {
      markers.forEach((marker) => {
        const options = {
          position: new this.maps_api.LatLng(marker.lat, marker.lng),
          visible: this.show_markers,
        };

        // Create marker and add ID from data
        const new_marker = new this.maps_api.Marker(options);
        new_marker.id = marker.id;

        // Add click listener
        new_marker.addListener("click", () => this.onMarkerClick(new_marker.id));

        // Add to local array
        this.map_markers.push(new_marker);
      });

      // Add a marker clusterer to manage the markers
      this.marker_clusterer = new MarkerClusterer(this.map, this.map_markers, {
        ignoreHidden: true, // Ignore hidden markers, so clusters aren't shown if markers are hidden
        imagePath: "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m",
      });
    },

    /**
     * Removes all markers from the map
     */
    removeMarkers() {
      this.map_markers.forEach((marker) => {
        marker.setVisible(false);
        marker.setMap(null);
      });

      // Disable clusterer
      this.marker_clusterer.setMap(null);
      this.map_markers = [];
    },

    /**
     * Toggles visibility for all markers & marker clusters
     * @param {Boolean} show - whether to show the markers
     */
    setMarkerVisibility(show) {
      // Show/hide markers
      this.map_markers.forEach((marker) => {
        marker.setVisible(show);
      });

      // Refresh marker cluster
      this.marker_clusterer.repaint();
    },

    /**
     * Toggles visibility of the heatmap layer
     * @param {Boolean} show - whether to show the heatmap layer
     */
    setHeatmapVisibility(show) {
      this.heatmap.setMap(show ? this.map : null);
    },

    /**
     * Upon clicking a marker
     * @param {string} id - the selected marker's ID
     */
    onMarkerClick(id) {
      this.$emit("propertySelected", id, true);
    },
  },
};
</script>
