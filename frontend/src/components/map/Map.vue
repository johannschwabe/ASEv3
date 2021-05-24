<template>
  <div ref="map" />
</template>

<script>
import MarkerClusterer from "@googlemaps/markerclustererplus";
import { setHeatmapRadius } from "../../data/helpers.js";

export default {
  name: "Heatmap",
  props: {
    center: {type: Object, default: () => { return {lat: 40.730610, lng: -73.935242}; }},
    initial_zoom: {type: Number, default: () => 13},
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
      return this.$store.getters.heatmapRadius;
    },

    /**
     * The points to construct the heatmap from
     */
    heatmap_points() {
      return this.points.map(
        (point) => new this.maps_api.LatLng(point.lat.toFixed(6), point.lng.toFixed(6)),
      );
    },
  },

  watch: {
    heatmap_points() {
      this.fillMap();
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
     * When points change, update heatmap
     */
    points() {
      this.heatmap.setData(this.heatmap_points);
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
      zoom: this.initial_zoom,
      center: this.center,
      mapTypeId: "roadmap",
      mapTypeControl: false,
      streetViewControl: false,
      fullscreenControl: false,
    };

    // Create map
    this.map = new this.maps_api.Map(map_element, map_options);

    this.fillMap();
  },
  methods: {
    setHeatmapRadius,

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
        radius: this.heatmap_radius * 3,
      };

      // Add heatmap to map and store locally
      this.heatmap = new this.maps_api.visualization.HeatmapLayer(heatmap_options);
      // Adjust initial radius
      this.setHeatmapRadius(this.heatmap_radius);

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
        new_marker.addListener("click", () => this.onMarkerClick({id: new_marker.id, lat: marker.lat, lng: marker.lng}));

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
     * @param {Object} marker
     */
    onMarkerClick(marker) {
      this.$emit("markerClick", marker);
    },
  },
};
</script>
