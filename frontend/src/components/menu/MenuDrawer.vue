<template>
  <q-drawer
    v-model="is_open"
    side="right"
    bordered
    content-class="bg-grey-2"
  >
    <q-scroll-area style="height: 100%">
      <q-list>
        <q-item-label header>
          Settings
        </q-item-label>

        <!-- Marker toggle -->
        <q-item
          clickable
          @click="toggleMarkers"
        >
          <q-item-section avatar>
            <q-icon
              :name="show_markers ? 'location_on' : 'location_off'"
            />
          </q-item-section>
          <q-item-section>
            <q-item-label>Show markers</q-item-label>
            <q-item-label caption>
              A marker on every property
            </q-item-label>
          </q-item-section>
          <q-item-section
            side
            top
          >
            <q-toggle
              color="secondary"
              :value="show_markers"
              @input="toggleMarkers"
            />
          </q-item-section>
        </q-item>

        <q-separator />

        <!-- Heatmap toggle -->
        <q-item
          clickable
          @click="toggleHeatmap"
        >
          <q-item-section avatar>
            <q-icon
              :name="show_heatmap ? 'explore_on' : 'explore_off'"
            />
          </q-item-section>
          <q-item-section>
            <q-item-label>Show heatmap</q-item-label>
            <q-item-label caption>
              Toggle heatmap visibility
            </q-item-label>
          </q-item-section>
          <q-item-section
            side
            top
          >
            <q-toggle
              color="secondary"
              :value="show_heatmap"
              @input="toggleHeatmap"
            />
          </q-item-section>
        </q-item>

        <q-separator />

        <!-- Neighbourhood toggle -->
        <q-item
          clickable
          @click="toggleNeighbourhoods"
        >
          <q-item-section avatar>
            <q-icon
              :name="show_neighbourhoods ? 'mdi-grid' : 'mdi-grid-off'"
            />
          </q-item-section>
          <q-item-section>
            <q-item-label>Show neighbourhoods</q-item-label>
            <q-item-label caption>
              Toggle neighbourhood borders visibility
            </q-item-label>
          </q-item-section>
          <q-item-section
            side
            top
          >
            <q-toggle
              color="secondary"
              :value="show_neighbourhoods"
              @input="toggleNeighbourhoods"
            />
          </q-item-section>
        </q-item>

        <q-separator />
        <!-- Map data type -->
        <q-item>
          <q-item-section>
            <q-item-label>Type</q-item-label>
            <q-item-label caption>
              Choose what data to visualize
            </q-item-label>
          </q-item-section>
        </q-item>
        <q-btn-group
          flat
          class="full-width"
          style="padding: 4px"
        >
          <q-btn
            style="width: 50%"
            :color="map_type === 'AIRBNB' ? 'secondary' : null"
            :text-color="map_type === 'AIRBNB' ? 'white' : 'black'"
            label="Airbnbs"
            icon="hotel"
            no-caps
            @click="setMapType('AIRBNB')"
          />
          <q-btn
            style="width: 50%"
            :color="map_type === 'PROPERTY' ? 'secondary' : null"
            :text-color="map_type === 'PROPERTY' ? 'white' : 'black'"
            label="Properties"
            icon="home"
            no-caps
            @click="setMapType('PROPERTY')"
          />
        </q-btn-group>
        <q-separator />

        <q-item-label header>
          Heatmap
        </q-item-label>

        <!-- Heatmap point radius -->
        <q-item>
          <q-item-section avatar>
            <q-icon name="mdi-arrow-expand" />
          </q-item-section>
          <q-item-section>
            <q-item-label>Point Radius</q-item-label>
            <q-item-label caption>
              Determine the visualization radius of data points
            </q-item-label>
          </q-item-section>
        </q-item>
        <div style="padding: 0 15px">
          <q-slider
            color="secondary"
            :value="heatmap_radius"
            :min="1"
            :max="200"
            @input="val => setHeatmapRadius(map_type, val)"
          />
        </div>

        <q-separator />

        <q-item-label header>
          About
        </q-item-label>

        <q-item
          class="column"
          style="text-align: justify"
        >
          <p>
            This is a tool for analysing properties for sale with the goal of renting them out using Airbnb, as well as analysing existing Airbnbs.
          </p>
          <br>
          <p style="text-align: center; font-size: smaller">
            Made for the Advanced Software Engineering course 2021 at the University of Zurich
          </p>
        </q-item>
      </q-list>

      <p
        class="fixed-bottom full-width"
        style="font-size: x-small; text-align: center"
      >
        © {{ new Date().getFullYear() }} Johann Schwabe, Thomas Mannhart & David Wyss
      </p>
    </q-scroll-area>
  </q-drawer>
</template>

<script>
import { setHeatmapRadius } from "../../data/helpers.js";

export default {
  name: "MenuDrawer",
  computed: {
    /**
     * Whether the drawer is open
     */
    is_open() {
      return this.$store.getters.drawerOpen;
    },

    /**
     * Whether to show map markers
     */
    show_markers() {
      return this.$store.getters.showMarkers;
    },

    /**
     * Whether to show the heatmap
     */
    show_heatmap() {
      return this.$store.getters.showHeatmap;
    },

    /**
     * Whether to show neighbourhood borders
     */
    show_neighbourhoods() {
      return this.$store.getters.showNeighbourhoods;
    },

    /**
     * The heatmap's type
     */
    map_type() {
      return this.$store.getters.mapType;
    },

    /**
     * Pixel radius of heatmap points
     */
    heatmap_radius() {
      return this.$store.getters.heatmapRadius;
    },
  },
  methods: {
    setHeatmapRadius,

    /**
     * Toggles marker visibility
     */
    toggleMarkers() {
      this.$store.commit("toggleMarkers");
    },

    /**
     * Toggles heatmap visibility
     */
    toggleHeatmap() {
      this.$store.commit("toggleHeatmap");
    },

    /**
     * Toggles neighbourhoods visibility
     */
    toggleNeighbourhoods() {
      this.$store.commit("toggleNeighbourhoods");
    },

    /**
     * Sets the map type
     * @param {String} type - the chosen type, e.g. 'AIRBNB'
     */
    setMapType(type) {
      this.$store.dispatch("setMapType", {map_type: type});

      // Set heatmap radius again to adjust for type change
      this.setHeatmapRadius(type, this.heatmap_radius);
    },
  },
};
</script>

<style scoped>

</style>
