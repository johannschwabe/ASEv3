<script src="store/mutations.js"></script>
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

import MenuBar from "@/components/menu/MenuBar.vue";
import MainPage from "@/pages/MainPage.vue";
import MenuDrawer from "@/components/menu/MenuDrawer.vue";
import { API_KEY } from "@/data/API.js";

export default {
  name: "LayoutDefault",

  components: {
    MenuBar,
    MainPage,
    MenuDrawer,
  },

  data() {
    return {
      api_loaded: false, // API loading status for enabling the main page
    };
  },
  computed: {
    /**
     * Google Maps API instance
     */
    maps_api() {
      return this.$store.getters.mapsApi;
    },
  },

  created() {
    // Get Google maps API
    this.getMapsApi();
  },

  methods: {
    /**
     * Loads the Google Maps API and stores its instance locally
     */
    async getMapsApi() {
      // eslint-disable-next-line global-require
      const loadGoogleMapsApi = require("load-google-maps-api");
      const options = {
        key: API_KEY,
        libraries: ["visualization"],
      };

      const maps_api = await loadGoogleMapsApi(options);
      this.$store.commit("setMapsApi", {maps_api: maps_api});
      this.api_loaded = true;
    },
  },
};
</script>

<style>
</style>
