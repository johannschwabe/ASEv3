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
            :color="map_type === 'AIRBNB' ? 'primary' : null"
            :text-color="map_type === 'AIRBNB' ? 'white' : 'black'"
            label="Airbnbs"
            icon="hotel"
            no-caps
            @click="setMapType('AIRBNB')"
          />
          <q-btn
            style="width: 50%"
            :color="map_type === 'PROPERTY' ? 'primary' : null"
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
            :value="heatmap_radius"
            :min="1"
            :max="200"
            @input="val => setHeatmapRadius(val)"
          />
        </div>

        <q-separator />

        <!--        &lt;!&ndash; TODO remove altogether? &ndash;&gt;-->
        <!--        <q-item-label header>-->
        <!--          Filters-->
        <!--        </q-item-label>-->

        <!--        <AirbnbFilters v-if="map_type === 'AIRBNB'" />-->
      </q-list>
    </q-scroll-area>
  </q-drawer>
</template>

<script>
// import AirbnbFilters from "./AirbnbFilters.vue";

export default {
  name: "MenuDrawer",
  components: {
    // AirbnbFilters,
  },
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
    },

    /**
     * Sets the heatmap point radius
     * @param {Number} radius - the radius in px
     */
    setHeatmapRadius(radius) {
      this.$store.commit("setHeatmapRadius", {heatmap_radius: radius});
    },
  },
};
</script>

<style scoped>

</style>
