import Vue from 'vue'
import App from './App.vue'
import VueGoogleHeatmap from 'vue-google-heatmap';

import './quasar'

Vue.config.productionTip = false


Vue.use(VueGoogleHeatmap, {
  apiKey: "***REMOVED***" // TODO should not be part of repo if public
});

new Vue({
  render: h => h(App),
}).$mount('#app')
