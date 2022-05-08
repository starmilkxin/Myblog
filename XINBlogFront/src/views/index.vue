/* eslint-disable no-return-assign */
<template>
  <div>
    <!--头部导航栏-->
    <navigate :navigateFlag="navigateFlag" :father="this"></navigate>
    <!--中间内容-->
    <div class="m-container m-padded-tb-big animated fadeIn">
      <div class="ui container m-opacity">
        <div class="ui stackable grid">

          <!--左边博客列表-->
          <div class="eleven wide column blogDiv animated fadeIn">
            <!--header-->
            <div class="ui top attached segment">
              <div class="ui middle aligned two column grid">
                <div class="column">
                  <h3 class="ui teal header">博客</h3>
                </div>
                <div class="right aligned column">
                  共 <h2 class="ui orange header m-inline-block m-text-thin" v-text="page.rows"> 100 </h2> 篇
                </div>
              </div>
            </div>
              <!--content-->
              <div class="ui attached  segment"  >
                <div class="ui padded vertical segment m-padded-tb-large" v-for="(blog,blogIndex) in blogs" v-bind:key="blogIndex">
                  <div class="ui middle aligned mobile reversed stackable grid" >
                    <div class="eleven wide column">
                      <h3 class="ui header"><a :href="'/#/blog?blogId=' + blog.blogId" target="_blank" class="m-black" v-text="blog.title">你真的理解什么是财富自由吗？</a></h3>
                      <p class="m-text" v-text="blog.description">正确做好任何一件事情的前提是清晰、正确的理解目标。而事实是，我们很多人很多时候根本没有对目标正确的定义，甚至根本从来就没有想过，只是大家都那么做而已…...</p>
                      <div class="ui grid">
                        <div class="eight wide column" style="padding: 14px 14px 4px 14px;">
                          <div class="ui mini horizontal link list">
                            <div class="item">
                              <img v-bind:src='blog.userAvatar' alt="&nbsp" class="ui avatar image">
                              <div class="content"><a href="#" class="header" v-text="blog.userNickname">XIN</a></div>
                            </div>
                            <div class="item">
                              <i class="calendar icon"></i><span text="">{{blog.createTime}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            </div>
                            <div class="middle item">
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

              <!-- 等待loading -->
              <br/>
              <br/>
              <br/>
              <br/>
              <br/>
              <el-container v-if="loading==true" style="height: 400px;">
                <el-aside width="300px"></el-aside>
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

          </div>

          <!--右边的top-->
          <div class="five wide column">
            <!--分类-->
            <div class="ui segments">
              <div class="ui secondary segment">
                <div class="ui two column grid">
                  <div class="column">
                    <i class="idea icon"></i>分类
                  </div>
                  <div class="right aligned column">
                    <a href="/#/types">more <i class="angle double right icon"></i></a>
                  </div>
                </div>
              </div>
              <div class="ui teal segment">
                <div class="ui fluid vertical menu ">
                  <a :href="'/#/types?activeTypeId='+type.id" class="item" v-for="(type,typeIndex) in types" :key="typeIndex">
                    <span v-text="type.name">学习日志</span>
                    <div class="ui teal basic left pointing label" v-text="type.number">13</div>
                  </a>
                </div>
              </div>
            </div>
            <!--标签-->
            <div class="ui segments m-margin-top-large">
              <div class="ui secondary segment">
                <div class="ui two column grid">
                  <div class="column">
                    <i class="tags icon"></i>标签
                  </div>
                  <div class="right aligned column">
                    <a href="/#/tags">more <i class="angle double right icon"></i></a>
                  </div>
                </div>
              </div>
              <div class="ui teal segment">
                <a :href="'/#/tags?activeTagId='+tag.id" class="ui teal basic left pointing label m-margin-tb-tiny" v-for="(tag,tagIndex) in tags" :key="tagIndex">
                  <span v-text="tag.name">学习日志</span>
                  <div class="detail" v-text="tag.number">23</div>
                </a>
              </div>
            </div>
            <!--最新推荐-->
            <div class="ui segments m-margin-top-large">
              <div class="ui secondary segment ">
                <i class="bookmark icon"></i>最新推荐
              </div>
              <div class="ui segment" v-for="(blog,blogIndex) in recommendBlogs" :key="blogIndex">
                <a :href="'/#/blog?blogId='+blog.id" target="_blank" class="m-black m-text-thin" v-text="blog.title">用户故事（User Story）</a>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
    <!-- 尾部联系方式、音乐盒 -->
    <myfooter></myfooter>
  </div>
</template>

<script>
import navigate from '@/components/navigate'
import myfooter from '@/components/footer'
import page from '@/components/page'

export default {
  components: {navigate, myfooter, page},
  name: 'index',
  data () {
    return {
      blogs: [],
      types: [],
      tags: [],
      recommendBlogs: [],
      page: [],
      navigateFlag: 1,
      loading: true
    }
  },
  methods: {
    pagination (page) {
      this.$http({
        method: 'POST',
        url: this.$base_url + '/x/index',
        data: page
      }).then(response => {
        this.blogs = response.data.data.blogs
        this.page = response.data.data.page
        this.loading = false
      })
    },
    indexRight () {
      this.$http({
        method: 'POST',
        url: this.$base_url + '/x/indexRight'
      }).then(response => {
        this.types = response.data.data.types
        this.tags = response.data.data.tags
        this.recommendBlogs = response.data.data.recommendBlogs
      })
    }
  },
  created () {
    this.loading = true
    this.pagination(page)
    this.indexRight()
  }
}

</script>
<style scoped>
  @import '../../static/css/style.css';
</style>
