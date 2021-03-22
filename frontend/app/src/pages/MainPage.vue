<template>
  <q-page>
    <div
      class="window-height"
      style="padding-bottom: 50px"
    >
      <vue-google-heatmap
        :points="points"
        :lat="center.lat"
        :lng="center.lng"
        width="100%"
        height="100%"
        :initial-zoom="13"
      />
    </div>
  </q-page>
</template>

<script>
import properties from "@/data/properties.json"

export default {
  name: "MainPage",
  data() {
    return {
      center: {lat:40.730610, lng:-73.935242},
      properties: properties
    }
  },
  computed: {

    /**
     * Geographical Property distribution
     * @returns {Array}
     */
    points(){
      const result = [];
      this.properties.forEach((property) => {
        result.push({lat: property.latitude, lng: property.longitude})
      })

      return result;
    }
  }
}
</script>

<style scoped>

</style>
