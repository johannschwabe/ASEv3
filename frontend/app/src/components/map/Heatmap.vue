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
      mapsAPI: null, // Google Maps API object
      map: null,
    }
  },
  computed: {
    heatmapPoints() {
      return this.points.map(
        point => new this.mapsAPI.LatLng(point.lat, point.lng)
      );
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
      this.mapsAPI = googleMaps;
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
      const heatmap = new this.mapsAPI.visualization.HeatmapLayer({
        data: this.heatmapPoints,
        map: this.$mapObject
      });

      heatmap.setMap(this.map);

      //TODO
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
          position: new this.mapsAPI.LatLng(marker.lat, marker.lng),
          map: this.map,
          visible: true,
          title: "test" // TODO do we want a label?
        };

        // Place marker
        const new_marker = new this.mapsAPI.Marker(options)

        new_marker.addListener("click", () => this.onMarkerClick(new_marker));
      })
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



