<template>
  <q-page>
    <div
      class="window-height"
      style="padding-bottom: 50px"
    >
      <Heatmap
        :points="property_locations"
        :markers="property_locations"
        :center="center"
        :initial_zoom="12"
        :show_markers="show_markers"
        style="height: 100%; width: 100%"
        @markerClick="onMarkerClick"
      />

      <!-- Info window if property selected -->
      <PropertyCard
          v-if="selectedProperty"
          :property="selectedProperty"
      />

    </div>
  </q-page>
</template>

<script>
import properties from "@/data/properties.json"
import Heatmap from "@/components/map/Heatmap.vue";
import PropertyCard from "@/components/PropertyCard.vue";

export default {
  name: "MainPage",
  components: {
    Heatmap,
    PropertyCard
  },
  props: {
    show_markers: { type: Boolean, default: true }
  },
  data() {
    return {
      center: { lat: 40.730610, lng: -73.935242 },
      properties: properties, // TODO get from backend
      selectedProperty: null, // The property that was clicked
    }
  },
  computed: {
    /**
     * Geographical Property distribution
     * @returns {Array}
     */
    property_locations(){
      const result = [];
      this.properties.forEach((property) => {
        result.push({lat: property.latitude, lng: property.longitude});
      })

      return result;
    }
  },
  methods: {
    onMarkerClick(marker){
      // TODO use actual property
      this.selectedProperty = this.selectedProperty === marker ? null : marker;
    }
  }
}
</script>

<style scoped>

</style>
