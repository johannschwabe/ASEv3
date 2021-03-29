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
     * The points to show on the heatmap, depending on the chosen type
     */
    heatmap_points() {
      switch (this.heatmap_type) {
        case "airbnbs:":
          return this.airbnb_locations;
        case "properties":
          return this.property_locations;
        default:
          return [];
      }
    },

    /**
     * Google Maps API instance
     */
    heatmap_type() {
      return this.$store.getters.heatmapType;
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
     * Geographical property distribution
     * @returns {Array}
     */
    property_locations() {
      const result = [];
      this.properties.forEach((property) => {
        // Get location
        const coords = this.getCoordinatesForProperty(property);

        // TODO why don't we have all...
        if (coords) {
          result.push({
            lat: coords.lat,
            lng: coords.lng,
            id: coords.id,
          });
        }
      });

      return result;
    },
  },

  methods: {

    /**
     * Gets the coordinates for a given property
     * @async
     * @param {Object} property - the property object
     * @returns {null | {id: string, lat: string, lng: string}}
     */
    getCoordinatesForProperty(property) {
      const id_sale = `${property[""]}_${property.BOROUGH}`;
      const coord_object = this.properties_location_data.find((prop) => prop.id_sale === id_sale);
      if (!coord_object) { return null; }
      return {id: id_sale, lat: coord_object.lat, lng: coord_object.long};
    },

    /**
     * TODO
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
