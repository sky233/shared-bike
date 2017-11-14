import Vue from 'vue'
import Resource from 'vue-resource'
import NProgress from 'vue-nprogress'
import {sync} from 'vuex-router-sync'
import App from './App.vue'
import router from './router'
import store from './store'
import * as filters from './filters'
import {TOGGLE_SIDEBAR} from 'vuex-store/mutation-types'

import 'vue-toast/dist/vue-toast.min.css'
import VueToast from 'vue-toast'
import vueLogger from 'vue-logger'
import BaiduMap from 'vue-baidu-map'
import axios from './utils/http'

Vue.use(Resource)
Vue.use(NProgress)

Vue.prototype.axios = axios;
Vue.component('vue-toast', VueToast);
Vue.use(BaiduMap, {
    ak: 'G8MfElBszl16ZhmHsVvAYcZOdvvIoVpx'
});
Vue.use(vueLogger, {
    prefix: new Date(),
    dev: true,
    shortname: true,
    levels: ['log', 'warn', 'debug', 'error', 'dir', 'info']
});

// Enable devtools
Vue.config.devtools = true

sync(store, router)

const nprogress = new NProgress({parent: '.nprogress-container'})

const {state} = store

router.beforeEach((route, redirect, next) => {
    if (state.app.device.isMobile && state.app.sidebar.opened) {
        store.commit(TOGGLE_SIDEBAR, false)
    }
    next()
})

Object.keys(filters).forEach(key => {
    Vue.filter(key, filters[key])
})

const app = new Vue({
    router,
    store,
    axios,
    nprogress,
    ...App
})

export {app, router, store, axios}
