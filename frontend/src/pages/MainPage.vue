<template>
  <q-page>
    <div
      class="main-page"
    >
      <!-- Table Dialog -->
      <TableDialog
        @propertySelected="onPropertySelected"
      />

      <Map
        :points="map_points"
        :markers="map_points"
        :center="center"
        :initial_zoom="12"
        style="height: 100%; width: 100%"
        @propertySelected="onPropertySelected"
      />

      <!-- Info window if property/airbnb/rating is selected -->
      <div v-if="selected_id">
        <PropertyCard
          v-if="map_type === 'PROPERTY'"
          :id="selected_id"
          :from_map="from_map"
          @hide="onCardHide"
        />
        <AirbnbCard
          v-else-if="map_type === 'AIRBNB'"
          :id="selected_id"
          @hide="onCardHide"
        />
      </div>
    </div>
  </q-page>
</template>

<script>

import axios from "axios";
import Map from "../components/view/Map.vue";
import PropertyCard from "../components/property/PropertyCard.vue";
import AirbnbCard from "../components/airbnb/AirbnbCard.vue";
import TableDialog from "../components/view/TableDialog.vue";
import {BACKEND_URL} from "../constants/API.js";
import * as OPTIONS from "../constants/OPTIONS.js";

export default {
  name: "MainPage",
  components: {
    Map,
    PropertyCard,
    AirbnbCard,
    TableDialog,
  },
  data() {
    return {
      center: { lat: 40.730610, lng: -73.935242 },
      properties: [],
      airbnbs: [],
      from_map: false, // Whether the currently selected property was picked from the map
    };
  },
  computed: {
    /**
     * The id of the coordinates that is selected
     */
    selected_id() {
      return this.$store.getters.selectedId;
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
      this.$store.commit("setSelectedId", { selected_id: null });
    },

    /**
     * Upon selecting a property (by marker click or table select), mark it as selected
     * @param {string} id - the selected property's ID
     * @param {boolean} from_map - whether the given ID is a coordinates ID
     */
    async onPropertySelected(id, from_map) {
      // Close drawer if open
      if (this.$store.getters.drawerOpen) {
        this.$store.commit("toggleDrawer");
      }

      // If this property was selected already, toggle
      if (this.selected_id && this.selected_id === id) {
        this.$store.commit("setSelectedId", { selected_id: null });
      } else {
        // Commit selection change
        this.$store.commit("setSelectedId", { selected_id: id });

        // Update whether property was picked from map in order to tell PropertyCard how to fetch
        this.from_map = from_map;
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
