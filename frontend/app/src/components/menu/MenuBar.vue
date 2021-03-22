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
  props: {
    drawer_open: { type: Boolean, required: true},
    show_markers: { type: Boolean, required: true},
  },
  methods: {
    /**
     * TODO
     */
    toggleDrawer() {
      this.$emit('update:drawer_open', !this.drawer_open)
    },

    /**
     * TODO
     */
    toggleMarkers() {
      this.$emit('update:show_markers', !this.show_markers)
    }
  }
}
</script>

<style scoped>

</style>
