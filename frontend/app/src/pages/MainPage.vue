<template>
  <q-page>
    <div
      class="window-height"
      style="padding-bottom: 50px"
    >
      <GmapMap
          :center="{lat:40.730610, lng:-73.935242}"
          :zoom="12"
          map-type-id="roadmap"
          style="width: 100%; height: 100%"
          :options="{
           zoomControl: true,
           mapTypeControl: false,
           scaleControl: false,
           streetViewControl: false,
           rotateControl: false,
           fullscreenControl: false,
           disableDefaultUI: false
         }"
      >
        <!--      <GmapMarker-->
        <!--          :key="index"-->
        <!--          v-for="(m, index) in markers"-->
        <!--          :position="m.position"-->
        <!--          :clickable="true"-->
        <!--          :draggable="true"-->
        <!--          @click="center=m.position"-->
        <!--      />-->
      </GmapMap>
    </div>
  </q-page>
</template>

<script>
export default {
  name: "MainPage"
}
</script>

<style scoped>

</style>
