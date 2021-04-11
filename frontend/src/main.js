/* eslint-disable import/no-unresolved */

import Vue from "vue";
import "./quasar.js";
// Store

import Vuex from "vuex";
import state from "@/store/state.js";
import * as getters from "@/store/getters.js";
import * as mutations from "@/store/mutations.js";
import * as actions from "@/store/actions.js";
import App from "./App.vue";

Vue.use(Vuex);
const store = new Vuex.Store({
  state,
  mutations,
  getters,
  actions,
});

Vue.config.productionTip = false;

new Vue({
  render: (h) => h(App),
  store: store,
}).$mount("#app");
