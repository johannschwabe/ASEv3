<template>
  <div ref="map"/>
</template>

<script>

export default {
  name: 'Heatmap',
  props: {
    lat: {type: Number, default: () => 37.775},
    lng: {type: Number, default: () => -122.434},
    initialZoom: {type: Number, default: () => 13},
    mapType: {type: String, default: () => 'roadmap'},
    points: {type: Array, required: false},
    markers: {type: Array, required: false},
  },
  data(){
    return {
      google: null,
      map: null
    }
  },
  computed: {
    heatmapPoints() {
      return this.points.map(
        point => new this.google.LatLng(point.lat, point.lng)
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
      this.google = googleMaps;
      const map_element = this.$refs.map;
      this.map = new googleMaps.Map(map_element, {
        zoom: this.initialZoom,
        center: { lat: this.lat, lng: this.lng },
        mapTypeId: this.mapType

      });
      const heatmap = new this.google.visualization.HeatmapLayer({
        data: this.heatmapPoints,
        map: this.$mapObject
      });

      heatmap.setMap(this.map);

      //TODO
      this.placeMarkers();
    }).catch((e) => {
      console.error(e)
    })
  },

  methods: {
    placeMarkers() {
      this.markers.forEach((marker) => {
        const options = {
          position: new this.google.LatLng(marker.lat, marker.lng),
          map: this.map,
          visible: true,
          title: "test"
        };

        // Place marker
        new this.google.Marker(options)
      })
    }
  }
};
</script>



