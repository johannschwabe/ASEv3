<template>
  <q-layout view="hhh lpr fff">
    <!-- Menu bar -->
    <MenuBar />

    <!-- Side menu drawer -->
    <MenuDrawer />

    <!-- Main page content -->
    <q-page-container>
      <!-- Page shown only once API is loaded -->
      <MainPage v-if="api_loaded" />
    </q-page-container>
  </q-layout>
</template>

<script>
/* eslint-disable import/no-unresolved */
import MenuBar from "@/components/menu/MenuBar.vue";
import MainPage from "@/pages/MainPage.vue";
import MenuDrawer from "@/components/menu/MenuDrawer.vue";
import properties from "@/data/properties.json";

export default {
  name: "LayoutDefault",

  components: {
    MenuBar,
    MainPage,
    MenuDrawer,
  },

  data() {
    return {
      api_loaded: false,
      properties: properties,
    };
  },

  created() {
    // Get Google maps API
    this.getMapsApi().then(() => this.getPropertyCoordinates());
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
      this.api_loaded = true;
    },

    /**
     * TODO
     * @returns {Promise<void>}
     */
    async getPropertyCoordinates() {
      // Use google geocoding API to get location for all properties TODO for all once working
      this.properties.slice(0, 10).forEach((property) => {
        // Determine full address string
        const address_string = `${property.ADDRESS}, ${property["ZIP CODE"]}, New York`;

        console.log("Query geocoding for property with address", property, address_string);

        // Make query to geocoding API
      });
    },
  },
};
</script>

<style>
</style>
