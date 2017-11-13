// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.css'

import router from './router'
import axios from './utils/http'
import store from './store/store'
import * as models from './models';

import App from '@/App'
import DemoPage from '@/views/components/DemoPage'

import 'vue-toast/dist/vue-toast.min.css'
import VueToast from 'vue-toast'
import vueLogger from 'vue-logger'
import BaiduMap from 'vue-baidu-map'

Vue.component('vue-toast', VueToast);
Vue.component('demo-page', DemoPage);
Vue.use(VueMaterial);
Vue.use(BaiduMap, {
  ak: 'G8MfElBszl16ZhmHsVvAYcZOdvvIoVpx'
});
Vue.use(vueLogger, {
  prefix: new Date(),
  dev: true,
  shortname: true,
  levels: ['log', 'warn', 'debug', 'error', 'dir', 'info']
});

Vue.material.registerTheme({
  default: {
    primary: 'blue',
    accent: 'red'
  }
});

Vue.prototype.axios = axios;
Vue.config.productionTip = false;
Vue.config.debug = process.env.DEBUG_MODE;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  axios,
  router,
  store,
  models,
  template: '<App/>',
  components: {App}
});



