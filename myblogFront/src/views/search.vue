<template>
    <div>
    <!--头部导航栏-->
    <navigate :navigateFlag="navigateFlag" :father="this"></navigate>
    <!--中间内容-->
    <div  class="m-container-small m-padded-tb-big animated fadeIn my-height">
        <div class="ui container m-opacity">
        <!--header-->
        <div class="ui top attached segment">
            <div class="ui middle aligned two column grid">
            <div class="column">
                <h3 class="ui teal header">搜索结果</h3>
            </div>
            <div class="right aligned column">
                共 <h2 class="ui orange header m-inline-block m-text-thin" v-text="page.rows"> 14 </h2> 个
            </div>
            </div>
        </div>
        <div class="ui top attached teal segment">
            <div class="ui padded vertical segment m-padded-tb-large" v-for="(blog,blogIndex) in blogs" :key="blogIndex">
                <div class="ui middle aligned mobile reversed stackable grid">
                <div class="eleven wide column">
                    <h3 class="ui header"><a :href="'/#/blog?blogId='+blog.blogId" target="_blank" class="m-black" v-text="blog.title">你真的理解什么是财富自由吗？</a></h3>
                    <p class="m-text" v-text="blog.description">正确做好任何一件事情的前提是清晰、正确的理解目标。而事实是，我们很多人很多时候根本没有对目标正确的定义，甚至根本从来就没有想过，只是大家都那么做而已…...</p>
                    <div class="ui grid">
                    <div class="eleven wide column" style="padding: 14px 14px 0 14px">
                        <div class="ui mini horizontal link list">
                        <div class="item">
                            <img :src="blog.userAvatar" alt="&nbsp" class="ui avatar image">
                            <div class="content"><a href="#" class="header" v-text="blog.userNickname">XIN</a></div>
                        </div>
                        <div class="item">
                            <i class="calendar icon"></i><span text="">{{blog.createTime}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        </div>
                        <!--                      <div class="item">-->
                        <!--                        <i class="eye icon" th:text="${blog.views}"></i>-->
                        <!--                      </div>-->
                        <div class="item">
                            <div class="ui orange basic label m-padded-tiny" v-text="blog.flag">原创</div>
                        </div>
                        </div>
                    </div>
                    <div class="middle left aligned"  style="padding: 17px 14px 0 4px">
                    <a :href="'/#/types?activeTypeId='+blog.typeId" class="ui teal basic label m-padded-tiny" v-text="blog.typeName">认知升级</a>
                    </div>
                    <div class="middle left aligned"  style="padding: 17px 4px 0 4px" v-for="(tag, tagIndex) in blog.tags" :key="tagIndex">
                    <a :href="'/#/tags?activeTagId='+tag.id" class="ui pink basic left pointing label m-padded-tiny" v-text="tag.name">认知升级</a>
                    </div>
                    </div>
                </div>

                <div class="five wide column">
                    <a :href="'/#/blog?blogId=' + blog.blogId" target="_blank">
                    <img :src="blog.firstPicture" alt="" class="ui rounded image">
                    </a>
                </div>
                </div>
            </div>
        </div>
        <!-- page -->
        <page :page="page" :father="this"></page>
        </div>
    </div>

  <br/>
  <br/>
  <br/>
  <!-- 等待loading -->
  <el-container v-if="loading==true" style="height: 400px;">
    <el-aside width="600px"></el-aside>
    <div>
      <ul>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
      </ul>
    </div>
  </el-container>

    <!-- 尾部联系方式、音乐盒、下雪 -->
    <myfooter></myfooter>
    </div>
</template>

<script>
import navigate from '@/components/navigate'
import myfooter from '@/components/footer'
import page from '@/components/page'

export default {
  components: {navigate, myfooter, page},
  name: 'search',
  data () {
    return {
      blogs: [],
      page: [],
      navigateFlag: 0,
      kw: this.$route.query.name,
      loading: true
    }
  },
  methods: {
    pagination (page) {
      if (typeof (this.kw) === 'undefined') {
        this.kw = ''
      }
      this.$http({
        method: 'POST',
        url: this.$base_url + '/x/search' + '?name=' + this.kw,
        data: page
      }).then(response => {
        this.blogs = response.data.data.blogs
        this.page = response.data.data.page
        this.loading = false
      })
    }
  },
  created () {
    this.loading = true
    this.pagination(page)
  }
}
</script>
<style scoped>
  @import '../../static/css/style.css';
</style>
