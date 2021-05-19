<template>
  <q-page>
    <div
      class="main-page"
    >
      <Heatmap
        :points="map_points"
        :markers="map_points"
        :center="center"
        :initial_zoom="12"
        style="height: 100%; width: 100%"
        @markerClick="onMarkerClick"
      />

      <!-- Info window if property/airbnb/rating is selected -->
      <div v-if="selected_coordinates">
        <PropertyCard
          v-if="map_type === 'PROPERTY'"
          :coordinates="selected_coordinates"
          @hide="onCardHide"
        />
        <AirbnbCard
          v-else-if="map_type === 'AIRBNB'"
          :coordinates="selected_coordinates"
          @hide="onCardHide"
        />
      </div>
    </div>
  </q-page>
</template>

<script>

/* eslint-disable import/no-unresolved */
import Heatmap from "@/components/map/Heatmap.vue";
import PropertyCard from "@/components/PropertyCard.vue";
import {BACKEND_URL} from "@/constants/API.js";
import axios from "axios";
import AirbnbCard from "@/components/AirbnbCard.vue";
import * as OPTIONS from "@/constants/OPTIONS.js";

export default {
  name: "MainPage",
  components: {
    Heatmap,
    PropertyCard,
    AirbnbCard,
  },
  data() {
    return {
      center: { lat: 40.730610, lng: -73.935242 },
      properties: [],
      airbnbs: [],
    };
  },
  computed: {
    /**
     * The id of the coordinates that is selected
     */
    selected_coordinates() {
      return this.$store.getters.selectedCoordinates;
    },

    /**
     * Type of heatmap to show
     */
    map_type() {
      return this.$store.getters.mapType;
    },

    /**
     * The points to show on the map, either as heatmap, markers, or both
     */
    map_points() {
      switch (this.map_type) {
        case OPTIONS.MAP_TYPES.AIRBNB:
          return this.airbnbs;
        case OPTIONS.MAP_TYPES.PROPERTY:
          return this.properties;
        case OPTIONS.MAP_TYPES.RATING:
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
  },

  mounted() {
    this.fetchAirbnbCoordinates();
    this.fetchPropertyCoordinates();
  },

  methods: {
    /**
     * Upon manually closing card, discard selected property
     */
    onCardHide() {
      this.$store.commit("setSelectedCoordinates", { selected_coordinates: null });
    },

    /**
     * Upon clicking a marker, mark it as selected
     * @param {Object} marker - the marker that was clicked, having id, lat and lng
     */
    async onMarkerClick(marker) {
      // Close drawer if open
      if (this.$store.getters.drawerOpen) {
        this.$store.commit("toggleDrawer");
      }

      // If this property was selected already, toggle
      if (this.selected_coordinates && this.selected_coordinates === marker.id) {
        this.$store.commit("setSelectedCoordinates", { selected_coordinates: null });
      } else {
        // Commit selection change
        this.$store.commit("setSelectedCoordinates", { selected_coordinates: marker.id });
      }
    },

    /**
     * Fetches location data for all Airbnbs
     */
    fetchAirbnbCoordinates() {
      axios({
        url: BACKEND_URL,
        method: "post",
        data: {
          query: `
            {
              allAirbnbCoordinates {
                id
                latitude
                longitude
              }
            }
          `,
        },
      }).then((result) => {
        result.data.data.allAirbnbCoordinates.forEach((property) => {
          this.airbnbs.push({
            id: property.id,
            lat: property.latitude,
            lng: property.longitude,
          });
        });
      });
    },

    /**
     * Fetches location data for all properties
     */
    fetchPropertyCoordinates() {
      axios({
        url: BACKEND_URL,
        method: "post",
        data: {
          query: `
            {
              allSalesCoordinates {
                id
                latitude
                longitude
              }
            }
          `,
        },
      }).then((result) => {
        result.data.data.allSalesCoordinates.forEach((property) => {
          this.properties.push({
            id: property.id,
            lat: property.latitude,
            lng: property.longitude,
          });
        });
      });
    },
  },
};
</script>

<style scoped>
  .main-page{
    height: calc(100vh - 50px) !important;
  }
</style>
