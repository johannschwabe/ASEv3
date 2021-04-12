<template>
  <div ref="map" />
</template>

<script>
import MarkerClusterer from "@googlemaps/markerclustererplus";

export default {
  name: "Heatmap",
  props: {
    center: {type: Object, default: () => { return {lat: 40.730610, lng: -73.935242}; }},
    initial_zoom: {type: Number, default: () => 13},
    points: {type: Array, default: () => []},
    markers: {type: Array, default: () => []},
  },
  data() {
    return {
      maps_api: null, // Google Maps API instance
      map: null, // Google Map object
      heatmap: null, // Heatmap layer
      map_markers: [], // Map marker objects
      marker_clusterer: null, // Marker clusterer instance
    };
  },
  computed: {
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
     * The points to construct the heatmap from
     */
    heatmapPoints() {
      return this.points.map(
        (point) => new this.maps_api.LatLng(point.lat, point.lng),
      );
    },
  },

  watch: {
    heatmapPoints() {
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
  },

  created() {
    // eslint-disable-next-line global-require
    const loadGoogleMapsApi = require("load-google-maps-api");
    const api_key = process.env.VUE_APP_API_KEY;
    const options = {
      key: api_key,
      libraries: ["visualization"],
    };

    loadGoogleMapsApi(options).then((googleMaps) => {
      this.maps_api = googleMaps;
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
      this.map = new googleMaps.Map(map_element, map_options);
    }).catch((e) => {
      // eslint-disable-next-line no-console
      console.error(e);
    });
  },

  methods: {
    fillMap() {
      // Options for heatmap overlay
      const heatmap_options = {
        data: this.heatmapPoints,
        map: this.$mapObject,
      };

      // Add heatmap to map
      // Store locally
      this.heatmap = new this.maps_api.visualization.HeatmapLayer(heatmap_options);

      this.heatmap.setMap(this.map);

      // TODO makes sense here?
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
          // map: this.map,
          visible: this.show_markers,
          // label: "something" // TODO remove?
        };

        // Create marker and add ID from data
        const new_marker = new this.maps_api.Marker(options);
        new_marker.id = marker.id;

        // Add click listener
        new_marker.addListener("click", () => this.onMarkerClick(new_marker.id));

        // Add to local array
        this.map_markers.push(new_marker);
      });

      // Add a marker clusterer to manage the markers.
      this.marker_clusterer = new MarkerClusterer(this.map, this.map_markers, {
        ignoreHidden: true, // Ignore hidden markers, so clusters aren't shown if markers are hidden
        imagePath: "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m",
      });
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
      // this.map.setZoom(15);
      // this.map.setCenter(marker.getPosition());
    },
  },
};
</script>
