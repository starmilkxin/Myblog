<template>
<div>
  <!--头部导航栏-->
  <navigate :navigateFlag="navigateFlag" :father="this"></navigate>
  <!--中间内容-->
  <div class="m-container-small m-padded-tb-big my-height">
    <div class="ui container m-opacity">
      <!--header-->
      <div class="ui top attached padded segment">
        <div class="ui middle aligned two column grid">
          <div class="column">
            <h3 class="ui teal header">时间轴</h3>
          </div>
          <div class="right aligned column">
            共 <h2 class="ui orange header m-inline-block m-text-thin" v-text="blogCount"> 114 </h2> 篇博客
          </div>
        </div>
      </div>

      <div v-for="(item, itemIndex) in archiveMap" :key="itemIndex">
      <h3 class="ui center aligned header m-text-thick" v-text="item.year" style="padding: 17px 14px 0 4px;color: rgb(152 41 241)">2017</h3>
      <div class="ui fluid vertical menu">
        <a :href="'/#/blog?blogId=' + blog.id" target="_blank" class="item" v-for="(blog, blogIndex) in item.blogs" :key="blogIndex">
          <span >
            <i class="mini teal circle icon"></i><span v-text="blog.title"></span>
            <div class="ui teal basic left pointing label m-padded-mini " text="">{{$dateFormat(blog.createTime, 'mm-dd')}}</div>
          </span>
          <div class="ui orange basic left pointing label m-padded-mini " v-text="blog.flag">原创</div>
        </a>
      </div>
      </div>

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

  <!-- 尾部联系方式、音乐盒-->
  <myfooter></myfooter>
</div>
</template>
<script>
import navigate from '@/components/navigate'
import myfooter from '@/components/footer'

export default {
  components: {navigate, myfooter},
  name: 'archives',
  data () {
    return {
      blogs: [],
      archiveMap: [{'': []}],
      blogCount: 0,
      navigateFlag: 4,
      loading: true
    }
  },
  methods: {
    archives () {
      this.loading = true
      this.$http({
        method: 'GET',
        url: this.$base_url + '/x/archives'
      }).then(response => {
        this.archiveMap = response.data.data.archiveMap
        this.blogCount = response.data.data.blogCount
        this.loading = false
      })
    }
  },
  created () {
    this.archives()
  }
}
</script>
<style scoped>
  @import '../../static/css/style.css';
</style>
