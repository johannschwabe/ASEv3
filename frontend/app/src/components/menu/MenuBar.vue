<template>
  <q-header elevated>
    <q-toolbar>
      <q-btn
          flat
          dense
          round
          aria-label="Menu"
          icon="menu"
      />

      <q-toolbar-title>
        Quasar App
      </q-toolbar-title>

      <div>Quasar v{{ $q.version }}</div>
    </q-toolbar>
  </q-header>
</template>

<script>
export default {
  name: "MenuBar"
}
</script>

<style scoped>

</style>
