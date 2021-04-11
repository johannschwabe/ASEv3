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
        v-if="selected_property"
        :property="selected_property"
      />
    </div>
  </q-page>
</template>

<script>
/* eslint-disable import/no-unresolved */
import properties from "@/data/properties.json";
import Heatmap from "@/components/map/Heatmap.vue";
import PropertyCard from "@/components/PropertyCard.vue";

export default {
  name: "MainPage",
  components: {
    Heatmap,
    PropertyCard,
  },
  props: {
    show_markers: { type: Boolean, default: true },
  },
  data() {
    return {
      center: { lat: 40.730610, lng: -73.935242 },
      properties: properties, // TODO get from backend
      selected_property: null, // The property that was clicked
    };
  },
  computed: {
    /**
     * Geographical Property distribution
     * @returns {Array}
     */
    property_locations() {
      const result = [];
      this.properties.forEach((property) => {
        result.push({
          lat: property.latitude,
          lng: property.longitude,
          id: property.id,
        });
      });

      return result;
    },
  },
  methods: {
    onMarkerClick(id) {
      // If this property was selected already, toggle
      if (this.selected_property && this.selected_property.id === id) {
        this.selected_property = null;
      } else {
        // Get property by ID
        this.selected_property = this.properties.find((prop) => prop.id === id);
      }
    },
  },
};
</script>

<style scoped>

</style>
