<template>
  <q-header elevated>
    <q-toolbar>
      <q-avatar>
        <img alt="Quasar logo" src="@/assets/logo.png">
      </q-avatar>

      <q-toolbar-title>
        Airbnb Property Rating
      </q-toolbar-title>

      <q-btn
          flat
          dense
          round
          aria-label="Marker"
          :icon="show_markers ? 'location_on' : 'location_off'"
          @click="toggleMarkers"
      />

      <q-btn
          flat
          dense
          round
          aria-label="Menu"
          icon="menu"
          @click="toggleDrawer"
      />
    </q-toolbar>
  </q-header>
</template>

<script>
export default {
  name: "MenuBar",
  computed: {
    /**
     * TODO
     */
    show_markers(){
      return this.$store.getters.showMarkers
    },
  },
  methods: {
    /**
     * TODO
     */
    toggleDrawer() {
      this.$store.commit('toggleDrawer')
    },

    /**
     * TODO
     */
    toggleMarkers() {
      this.$store.commit('toggleMarkers')
    }
  }
}
</script>

<style scoped>

</style>
