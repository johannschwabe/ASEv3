<template>
  <div ref="map"/>
</template>

<script>

export default {
  name: 'Heatmap',
  props: {
    center: {type: Object, default: () => { return {lat: 40.730610, lng: -73.935242} },},
    initial_zoom: {type: Number, default: () => 13},
    points: {type: Array, required: false},
    markers: {type: Array, required: false},
  },
  data(){
    return {
      maps_api: null, // Google Maps API object
      map: null, // Google Map object
      heatmap: null, // Heatmap layer
      map_markers: [], // Map marker objects
    }
  },
  computed: {
    /**
     * Whether to show the markers
     */
    show_markers(){
      return this.$store.getters.showMarkers
    },

    /**
     * Whether to show the heatmap
     */
    show_heatmap(){
      return this.$store.getters.showHeatmap
    },

    /**
     * The points to construct the heatmap from
     */
    heatmapPoints() {
      return this.points.map(
        point => new this.maps_api.LatLng(point.lat, point.lng)
      );
    },
  },

  watch: {
    /**
     * When show_markers property changes, update all markers
     */
    show_markers(show) {
      this.setMarkerVisibility(show)
    },

    /**
     * When show_heatmap property changes, update heatmap visibility
     */
    show_heatmap(show) {
      console.log("gugus")
      this.setHeatmapVisibility(show)
    },
  },

  created() {
    const loadGoogleMapsApi = require('load-google-maps-api')
    const apiKey = "***REMOVED***"
    const options = {
      key: apiKey,
      libraries: ['visualization']
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
      }

      // Create map
      this.map = new googleMaps.Map(map_element, map_options);

      // Add heatmap to map
      const heatmap = new this.maps_api.visualization.HeatmapLayer({
        data: this.heatmapPoints,
        map: this.$mapObject
      });

      // Store locally
      this.heatmap = heatmap;

      this.heatmap.setMap(this.map);

      //TODO makes sense here?
      this.placeMarkers(this.markers);
    }).catch((e) => {
      console.error(e)
    })
  },

  methods: {
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
          map: this.map,
          visible: this.show_markers,
          title: "test" // TODO do we want a label?
        };

        // Place marker
        const new_marker = new this.maps_api.Marker(options)

        // Add click listener
        new_marker.addListener("click", () => this.onMarkerClick(new_marker));

        // Add to local array
        this.map_markers.push(new_marker);
      })
    },

    /**
     * Toggles visibility for all markers
     * @param {Boolean} show - whether to show the markers
     */
    setMarkerVisibility(show){
      this.map_markers.forEach((marker) => {
        marker.setVisible(show)
      })
    },

    /**
     * Toggles visibility of the heatmap layer
     * @param {Boolean} show - whether to show the heatmap layer
     */
    setHeatmapVisibility(show){
      console.log("Setting heatmap vis...")
      this.heatmap.setMap(show? this.map : null);
    },

    /**
     * Upon clicking a marker
     * @param {Object} marker
     */
    onMarkerClick(marker){
      this.$emit('markerClick', marker)
      // this.map.setZoom(15);
      // this.map.setCenter(marker.getPosition());
    }
  }
};
</script>



