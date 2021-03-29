<template>
  <q-layout view="hhh lpr fff">
    <!-- Menu bar -->
    <MenuBar />

    <!-- Side menu drawer -->
    <MenuDrawer />

    <!-- Main page content -->
    <q-page-container>
      <MainPage />
    </q-page-container>
  </q-layout>
</template>

<script>
/* eslint-disable import/no-unresolved */
import MenuBar from "@/components/menu/MenuBar.vue";
import MainPage from "@/pages/MainPage.vue";
import MenuDrawer from "@/components/menu/MenuDrawer.vue";

export default {
  name: "LayoutDefault",

  components: {
    MenuBar,
    MainPage,
    MenuDrawer,
  },
};
</script>

<style>
</style>
