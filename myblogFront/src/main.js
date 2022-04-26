// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
// eslint-disable-next-line no-unused-vars
import axios from 'axios'
import semantic from 'semantic'
import dateFormat from 'dateformat'
import '../node_modules/semantic-ui-css/semantic.min.css'
import '../static/css/animate.css'
import '../static/css/APlayer.min.css'
import '../static/css/me.css'
import '../static/css/typo.css'
import elementui from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import 'jquery'

Vue.use(mavonEditor)
Vue.use(semantic)
Vue.use(elementui)

Vue.prototype.$dateFormat = dateFormat
Vue.prototype.$base_url = 'https://starmilk.top:443'
// Vue.prototype.$base_url = 'http://localhost:81'
Vue.config.productionTip = false

// 创建axios实例
const service = axios.create({
  baseURL: Vue.prototype.$base_url, // api的base_url
  timeout: 13000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(config => {
  // Do something before request is sent
  if (localStorage.getItem('Authorization')) {
    config.headers['Authorization'] = JSON.parse(localStorage.getItem('Authorization'))
  }
  return config
}, error => {
  console.log(error) // for debug
  Promise.reject(error)
})

// respone拦截器
service.interceptors.response.use(
  response => {
    var code = response.data.code
    if (code === 400) {
      return Promise.reject(new Error(response.data.message)).catch(err => { console.log(err) })
    } else {
      if (response.data.message) {
        console.log(response.data.message)
      }
    }
    return response
  },
  error => {
    console.log('err' + error)// for debug
    return Promise.reject(error)
  }
)

Vue.prototype.$http = service
Vue.prototype.$https = service

// 简易权限控制
const blackList = ['/admin/index', '/admin/blogs', '/admin/blogs/input', '/admin/types', '/admin/types/input', '/admin/tags', '/admin/tags/input']

router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title
  }
  if (blackList.indexOf(to.path) !== -1) { // 代表需要权限
    service({
      method: 'GET',
      async: false,
      url: Vue.prototype.$base_url + '/admin/user'
    }).then(response => {
      const user = response.data.data.user
      if (user === undefined || user == null) {
        next({ path: '/admin' })
      } else {
        next()
      }
    })
  } else {
    next()
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
