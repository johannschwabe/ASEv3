<template>
  <q-header elevated>
    <q-toolbar>
      <img
        style="width: 100px; filter: brightness(0) invert(1);"
        alt="Airbnb logo"
        src="@/assets/logo.png"
      >

      <q-toolbar-title>
        Property Rating Tool
      </q-toolbar-title>

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
  methods: {
    /**
     * Toggles the drawer
     */
    toggleDrawer() {
      this.$store.commit("toggleDrawer");
    },
  },
};
</script>

<style scoped>

</style>
