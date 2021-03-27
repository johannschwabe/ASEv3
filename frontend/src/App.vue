<template>
  <q-layout view="lHh Lpr lFf">
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
