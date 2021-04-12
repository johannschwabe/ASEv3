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
      <div v-if="selected_property">
        <PropertyCard
          v-if="map_type === 'PROPERTY'"
          :property="selected_property"
          @hide="onCardHide"
        />
        <AirbnbCard
          v-else-if="map_type === 'AIRBNB'"
          :property="selected_property"
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
     * The property/airbnb that is selected
     */
    selected_property() {
      return this.$store.getters.selectedProperty;
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
    this.fetchAirbnbs();
    this.fetchProperties();
  },

  methods: {
    /**
     * Upon manually closing card, discard selected property
     */
    onCardHide() {
      this.$store.commit("setSelectedProperty", { selected_property: null });
    },

    /**
     * Upon clicking a marker, mark it as selected
     * @param {Object} marker - the marker that was clicked, having id, lat and lng
     */
    onMarkerClick(marker) {
      // Close drawer if open
      if (this.$store.getters.drawerOpen) {
        this.$store.commit("toggleDrawer");
      }

      // If this property was selected already, toggle
      if (this.selected_property && this.selected_property.id.toString() === marker.id) {
        this.$store.commit("setSelectedProperty", { selected_property: null });
      } else {
        // Get property/airbnb/rating by ID
        let _property;
        switch (this.map_type) {
          case OPTIONS.MAP_TYPES.AIRBNB:
            _property = this.airbnbs.find((prop) => prop.id.toString() === marker.id);
            break;
          case OPTIONS.MAP_TYPES.PROPERTY:
            // TODO cleanup once we have proper IDs
            _property = this.properties.find((prop) => `${prop[""]}_${prop.BOROUGH}` === marker.id);
            break;
          default:
            break;
        }

        // Add coordinates and ID to property (if any)
        if (_property) {
          _property.id = marker.id;
          _property.lat = marker.lat;
          _property.lng = marker.lng;
        }

        // Commit selection change
        this.$store.commit("setSelectedProperty", { selected_property: _property });
      }
    },

    /**
     * Fetches location data for all Airbnbs
     */
    fetchAirbnbs() {
      axios({
        url: "http://localhost:8282/graphql",
        method: "post",
        data: {
          query: `
            {
              allAirbnbProperties {
                id
                latitude
                longitude
              }
            }
          `,
        },
      }).then((result) => {
        result.data.data.allAirbnbProperties.forEach((property) => {
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
    fetchProperties() {
      axios({
        url: "http://localhost:8282/graphql",
        method: "post",
        data: {
          query: `
            {
              allSalesProperties {
                id
                latitude
                longitude
              }
            }
          `,
        },
      }).then((result) => {
        console.log("got properties:", result.data.data);
        result.data.data.allSalesProperties.forEach((property) => {
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
