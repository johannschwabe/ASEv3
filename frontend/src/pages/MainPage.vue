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
import airbnbs_data from "@/data/airbnb.json";
import properties_data from "@/data/properties.json";
import properties_locations from "@/data/properties_locations.json";
import Heatmap from "@/components/map/Heatmap.vue";
import PropertyCard from "@/components/PropertyCard.vue";
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
      airbnbs: airbnbs_data, // TODO get from backend
      properties: properties_data, // TODO
      properties_location_data: properties_locations, // TODO
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
          return this.airbnb_locations;
        case OPTIONS.MAP_TYPES.PROPERTY:
          return this.property_locations;
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
          id: property.id.toString(),
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
        if (property.lat && property.lng) {
          result.push({
            lat: property.lat,
            lng: property.lng,
            id: property.id,
          });
        }
      });

      return result;
    },
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
  },
};
</script>

<style scoped>
  .main-page{
    height: calc(100vh - 50px) !important;
  }
</style>
