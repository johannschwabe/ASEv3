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
/* eslint-disable import/no-unresolved */
import MenuBar from "@/components/menu/MenuBar.vue";
import MainPage from "@/pages/MainPage.vue";
import MenuDrawer from "@/components/menu/MenuDrawer.vue";

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
  },
};
</script>

<style>
</style>
