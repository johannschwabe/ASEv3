import Vue from 'vue'
import App from './App.vue'
import './quasar'

// Store
import Vuex from 'vuex'
import state from '@/store/state.js'
import * as getters from '@/store/getters.js'
import * as mutations from '@/store/mutations.js'
import * as actions from '@/store/actions.js'

Vue.use(Vuex)
const store = new Vuex.Store({
  state,
  mutations,
  getters,
  actions
})

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  store: store
}).$mount('#app')
