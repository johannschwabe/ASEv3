<template>
  <q-page>
    <div
      class="main-page"
    >
      <Heatmap
        :points="airbnb_locations"
        :markers="airbnb_locations"
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
import airbnbs from "@/data/airbnb.json";
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
      airbnbs: airbnbs, // TODO get from backend
      properties: properties,
      selected_property: null, // The property that was clicked
    };
  },
  computed: {
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
        result.push({
          lat: property.latitude,
          lng: property.longitude,
          id: property.id,
        });
      });

      return result;
    },
  },

  created() {
    // Get Google maps API
    this.getMapsApi();

    // Use google geocoding API to get location for all properties
    this.properties.slice(0, 10).forEach((property) => {
      // Determine full address string
      const address_string = `${property.ADDRESS}, ${property["ZIP CODE"]}, New York`;

      console.log("Query geocoding for property with address", property, address_string);

      // Make query to geocoding API
    });
  },

  methods: {
    /**
     * TODO
     */
    async getMapsApi() {
      // eslint-disable-next-line global-require
      const loadGoogleMapsApi = require("load-google-maps-api");
      const apiKey = "***REMOVED***";
      const options = {
        key: apiKey,
        libraries: ["visualization"],
      };

      const maps_api = await loadGoogleMapsApi(options);
      console.log("Setting API!");
      this.$store.commit("setMapsApi", {maps_api: maps_api});
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
