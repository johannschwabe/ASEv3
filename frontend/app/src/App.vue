<template>
  <q-layout view="lHh Lpr lFf">
    <!-- Menu bar -->
    <MenuBar
        :drawer_open.sync="drawer_open"
        :show_markers.sync="show_markers"
    />

    <!-- Side menu drawer -->
    <MenuDrawer :is-open="drawer_open"/>

    <!-- Main page content -->
    <q-page-container>
      <MainPage
          :show_markers="show_markers"
      />
    </q-page-container>
  </q-layout>
</template>

<script>
import MenuBar from "@/components/menu/MenuBar.vue";
import MainPage from "@/pages/MainPage.vue";
import MenuDrawer from "@/components/menu/MenuDrawer.vue";

export default {
  name: 'LayoutDefault',

  components: {
    MenuBar,
    MainPage,
    MenuDrawer
  },

  data () {
    return {
      drawer_open: false,
      show_markers: true,
    }
  }
}
</script>

<style>
</style>
