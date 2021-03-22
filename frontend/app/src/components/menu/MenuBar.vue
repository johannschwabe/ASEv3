<template>
  <q-header elevated>
    <q-toolbar>
      <q-avatar>
        <img
          alt="Quasar logo"
          src="@/assets/logo.png"
        >
      </q-avatar>

      <q-toolbar-title>
        Airbnb Property Rating
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
  props: {
    drawer_open: { type: Boolean, required: true},
  },
  methods: {
    toggleDrawer() {
      this.$emit("update:drawerOpen", !this.drawer_open);
    },
  },
};
</script>

<style scoped>

</style>
