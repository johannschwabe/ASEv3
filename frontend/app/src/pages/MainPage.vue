<template>
  <q-page>
    <div
      class="window-height"
      style="padding-bottom: 50px"
    >
      <Heatmap
        :points="points"
        :markers="points.slice(0,100)"
        :center="center"
        :initial_zoom="12"
        style="height: 100%; width: 100%"
      />
    </div>
  </q-page>
</template>

<script>
import properties from "@/data/properties.json"
import Heatmap from "@/components/map/Heatmap.vue";

export default {
  name: "MainPage",
  components: {
    Heatmap
  },
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
