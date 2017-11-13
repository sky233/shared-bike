import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/Login'
import Yard from '@/views/Yard'

Vue.use(Router);

const lazyLoading = (name, index = false) => () => System.import(`@/views/${name}${index ? '/index' : ''}.vue`)

export default new Router({
  mode: 'hash',
  linkActiveClass: 'is-active',
  scrollBehavior: () => ({
    y: 0
  }),
  routes: [{
    name: 'login',
    path: '/login',
    component: Login
  }, {
    path: '/',
    component: Yard,
    children: [
      {
        path: 'home',
        component: lazyLoading('Home')
      },
      {
        path: 'users',
        component: lazyLoading('users/User')
      },
      {
        path: 'bikes',
        component: lazyLoading('bikes/Bike')
      },
      {
        path: 'create_bikes',
        component: lazyLoading('bikes/BikeCreate')
      }
    ]
  }]
})
