<template>
  <q-drawer
    v-model="is_open"
    side="right"
    bordered
    content-class="bg-grey-2"
  >
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
            A marker on each property - how pretty!
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
    </q-list>
  </q-drawer>
</template>

<script>
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
  },
};
</script>

<style scoped>

</style>
