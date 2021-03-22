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
          style="width: 100%; height: 100%"
      />
<!--      <GmapMap-->
<!--          :center="center"-->
<!--          :zoom="12"-->
<!--          map-type-id="roadmap"-->
<!--          style="width: 100%; height: 100%"-->
<!--          :options="{-->
<!--           zoomControl: true,-->
<!--           mapTypeControl: false,-->
<!--           scaleControl: false,-->
<!--           streetViewControl: false,-->
<!--           rotateControl: false,-->
<!--           fullscreenControl: false,-->
<!--           disableDefaultUI: false-->
<!--         }"-->
<!--      >-->
<!--              <GmapMarker-->
<!--                  :key="index"-->
<!--                  v-for="(m, index) in markers"-->
<!--                  :position="m.position"-->
<!--                  :clickable="true"-->
<!--                  :draggable="false"-->
<!--                  @click="center=m.position"-->
<!--              />-->
<!--      </GmapMap>-->
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
