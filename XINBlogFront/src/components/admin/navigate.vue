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
          <a href="/#/admin/blogs" class="m-item item m-mobile-hide " v-bind:class="{'active': (navigateFlag == 1)}"><i class="home icon"></i>博客</a>
          <a href="/#/admin/types" class="m-item item m-mobile-hide" :class="{'active' : (navigateFlag==2)}"><i class="idea icon"></i>分类</a>
          <a href="/#/admin/tags" class="m-item item m-mobile-hide" :class="{'active' : (navigateFlag==3)}"><i class="tags icon"></i>标签</a>
          <a href="/#/admin/message" class="m-item item m-mobile-hide" :class="{'active' : (navigateFlag==4)}"><i class="user icon" :class="{'red':unreadMessageN>0}"></i>
          <el-badge :value="(unreadMessageN>0)?unreadMessageN:''" :max="99" class="item">
            <nobr size="small">评论</nobr>
          </el-badge>
          </a>
          <a href="/#/" target="_blank" class="m-item item m-mobile-hide" :class="{'active' : (navigateFlag==5)}"><i class="clone icon"></i>主页</a>
          <div class="right m-item item m-mobile-hide">
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
export default {
  name: 'navigate',
  props: ['navigateFlag', 'father'],
  data () {
    return {
      user: null,
      kw: '',
      whetheronhover: false,
      unreadMessageN: 0
    }
  },
  methods: {
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
    },
    whetherUnreadMessage () {
      this.$http({
        method: 'GET',
        url: this.$base_url + '/admin/whetherUnreadMessage'
      }).then(response => {
        this.unreadMessageN = response.data.data.n
      })
    }
  },
  created () {
    this.getUserInfo()
    this.whetherUnreadMessage()
  }
}
</script>
