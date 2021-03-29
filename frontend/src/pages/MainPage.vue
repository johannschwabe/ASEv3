<template>
  <q-page>
    <div
      class="main-page"
    >
      <Heatmap
        :points="heatmap_points"
        :markers="airbnb_locations"
        :center="center"
        :initial_zoom="12"
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
import airbnbs_data from "@/data/airbnb.json";
import properties_data from "@/data/properties.json";
import properties_locations from "@/data/properties_locations.json";
import Heatmap from "@/components/map/Heatmap.vue";
import PropertyCard from "@/components/PropertyCard.vue";
import * as OPTIONS from "@/data/OPTIONS.js";

export default {
  name: "MainPage",
  components: {
    Heatmap,
    PropertyCard,
  },
  data() {
    return {
      center: { lat: 40.730610, lng: -73.935242 },
      airbnbs: airbnbs_data, // TODO get from backend
      properties: properties_data, // TODO
      properties_location_data: properties_locations, // TODO
      selected_property: null, // The property that was clicked
    };
  },
  computed: {
    /**
     * Type of heatmap to show
     */
    heatmap_type() {
      return this.$store.getters.heatmapType;
    },

    /**
     * The points to show on the heatmap, depending on the chosen type
     */
    heatmap_points() {
      switch (this.heatmap_type) {
        case OPTIONS.HEATMAP_TYPES.AIRBNB:
          return this.airbnb_locations;
        case OPTIONS.HEATMAP_TYPES.PROPERTY:
          return this.property_locations;
        case OPTIONS.HEATMAP_TYPES.RATING:
          return []; // TODO
        default:
          return [];
      }
    },

    /**
     * Google Maps API instance
     */
    maps_api() {
      return this.$store.getters.mapsApi;
    },

    /**
     * Geographical Airbnb distribution
     * @returns {Array}
     */
    airbnb_locations() {
      const result = [];
      this.airbnbs.forEach((property) => {
        result.push({
          lat: property.latitude,
          lng: property.longitude,
          id: property.id,
        });
      });

      return result;
    },

    /**
     * Geographical Airbnb distribution
     * @returns {Array}
     */
    property_locations() {
      const result = [];
      this.properties_location_data.forEach((property) => {
        result.push({
          lat: property.lat,
          lng: property.lng,
          id: property.id,
        });
      });

      return result;
    },
  },

  methods: {
    /**
     * Upon clicking a marker, mark it as selected
     * @param id
     */
    onMarkerClick(id) {
      // If this property was selected already, toggle
      if (this.selected_property && this.selected_property.id === id) {
        this.selected_property = null;
      } else {
        // Get property by ID TODO also for properties
        this.selected_property = this.airbnbs.find((prop) => prop.id === id);
      }
    },
  },
};
</script>

<style scoped>
  .main-page{
    height: calc(100vh - 50px) !important;
  }
</style>
