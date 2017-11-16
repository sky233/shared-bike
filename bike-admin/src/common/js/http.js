import axios from 'axios'
import {Message} from 'element-ui';

axios.defaults.timeout = 5000;
axios.defaults.baseURL = process.env.API_URL;
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';

// http request 拦截器
/*
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
 */

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
          break;
        case 400:
          Message.error({
            message: error.response.data.message
          });
          break;
        /*                    router.replace({
         path: 'login',
         query: {redirect: router.currentRoute.fullPath}
         })*/
      }
    }
    return Promise.reject(error)
  });

export default axios;
