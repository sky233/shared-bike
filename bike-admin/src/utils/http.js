import axios from 'axios'
import store from '../store/store'
import * as types from '../store/types'
import router from '../router'
import api from '../constant/api'

axios.defaults.timeout = 5000;
axios.defaults.baseURL = process.env.API_DOMAIN;
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';

// http request 拦截器
axios.interceptors.request.use(
  config => {
    if (store.state.token) {
      config.headers.Authorization = `Token ${store.state.token}`;
    } else if (localStorage.token) {
      config.headers.Authorization = `Token ${localStorage.token}`;
    }
    return config;
  },
  err => {
    return Promise.reject(err);
  });

axios.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          if (error.response.data.errorCode === 11) {
            //todo redirect login.
          }
          store.commit(types.LOGOUT);
          router.replace({
            path: 'Login',
            query: {redirect: router.currentRoute.fullPath}
          })
      }
    }
    return Promise.reject(error)
  });

export default axios;
