<template>
    <!-- <head th:fragment="head(title)">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title th:replace="${title}">博客测试</title>
    </head> -->
    <nav class="ui inverted attached segment m-padded-tb-mini m-shadow-small m-opacity-mini">
      <div class="ui container">
        <div class="ui inverted secondary stackable menu">
          <h2 class="ui teal header item">XIN's BLOG</h2>
          <a href="/#/" class="m-item item m-mobile-hide " v-bind:class="{'active': (navigateFlag == 1)}"><i class="home icon"></i>首页</a>
          <a href="/#/types" class="m-item item m-mobile-hide" :class="{'active' : (navigateFlag==2)}"><i class="idea icon"></i>分类</a>
          <a href="/#/tags" class="m-item item m-mobile-hide" :class="{'active' : (navigateFlag==3)}"><i class="tags icon"></i>标签</a>
          <a href="/#/archives" class="m-item item m-mobile-hide" :class="{'active' : (navigateFlag==4)}"><i class="hourglass start icon"></i>时间轴</a>
          <a href="/#/about" class="m-item item m-mobile-hide" :class="{'active' : (navigateFlag==5)}"><i class="info icon"></i>关于我</a>
          <div class="right m-item item m-mobile-hide">
            <div name="search" >
              <div class="ui icon inverted transparent input m-margin-tb-tiny" @keyup.enter="gotoSearch(kw)">
                <input input="text" name="query" placeholder="Search...." v-model='kw'>
                <i @click="gotoSearch(kw)" class="search link icon"></i>
              </div>
            </div>
            <div class="right m-item m-mobile-hide menu" v-if="user != null"  @click="whetheronhover = !whetheronhover">
              <div class="ui dropdown item">
                <div class="text">
                  <img class="ui avatar image"  :src="user.avatar"/>
                  <span v-text="user.nickname">XIN</span>
                </div>
                <i class="dropdown icon"></i>
                <div class="menu transition" :class="{'visible':whetheronhover}">
                  <a class="item" @click="logout()">注销</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>
</template>

<script>
import router from '@/router/index.js'

export default {
  name: 'navigate',
  props: ['navigateFlag', 'father'],
  data () {
    return {
      user: null,
      kw: '',
      whetheronhover: false
    }
  },
  methods: {
    gotoSearch (kw) {
      router.push({name: 'search', query: {'name': kw}})
      router.go(0)
    },
    logout () {
      localStorage.removeItem('Authorization')
      this.user = null
    },
    getUserInfo () {
      this.$http({
        method: 'GET',
        url: this.$base_url + '/admin/user'
      }).then(response => {
        this.user = response.data.data.user
        return this.user
      })
    }
  },
  created () {
    this.getUserInfo()
  }
}
</script>
