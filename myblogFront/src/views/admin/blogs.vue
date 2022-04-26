<template>
<div>
  <el-dialog
    title="提示"
    :visible.sync="dialogVisible"
    width="30%"
    >
    <span>确定要删除吗</span>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="dialogVisible = false;deleteBlog(deleteBlogId)">确 定</el-button>
    </span>
  </el-dialog>

  <!--头部导航栏-->
  <navigate :navigateFlag="navigateFlag" :father="this"></navigate>
  <!--中间内容-->
  <div  class="m-container-small m-padded-tb-big my-height">
    <div class="ui container m-opacity">
      <form class="ui secondary segment form">
        <div class="inline fields">
          <div class="field">
            <input type="text" name="title" placeholder="标题" v-model="title">
          </div>
          <div class="field">
            <div class="ui selection dropdown">
              <i class="dropdown icon" @click="whetheronhover = !whetheronhover"></i>
              <div class="text" v-text="typeName">分类</div>
              <div class="menu transition" :class="{'visible':whetheronhover}">
                <div v-for="(type, typeIndex) in types" :key="typeIndex" class="item" @click="whetheronhover = !whetheronhover;typeName=type.name;typeId=type.id">{{type.name}}</div>
              </div>
            </div>
            <button @click="clearAll();" id="clear-btn" class="ui compact button">清空</button>
          </div>
          <div class="field">
            <div class="ui checkbox">
              <input type="checkbox" id="recommend" name="recommend" v-model="whetherrecommend">
              <label for="recommend">推荐</label>
            </div>
          </div>
          <div class="field">
            <button type="button" id="search-btn" class="ui mini pink basic button" @click="page.current=1;pagination(page)"><i class="search icon"></i>搜索</button>
          </div>
          <div class="right menu" >
            <a :href="'/#/admin/blogs/input?blogId=-1'" class="ui mini yellow teal basic button">发布</a>
            <a @click="clearAll();page.current=1;pagination(page)" class="ui mini green basic button">列表</a>
          </div>
        </div>
      </form>
      <div id="table-container">
        <table class="ui compact teal table">
          <thead>
          <tr>
            <th></th>
            <th>标题</th>
            <th>类型</th>
            <th>推荐</th>
            <th>发布</th>
            <th>发布时间</th>
            <th>更新时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(blog, blogIndex) in blogs" :key="blogIndex">
            <td v-text="blogIndex + (page.current-1) * page.limit + 1">1</td>
            <td v-text="blog.title">刻意练习清单</td>
            <td v-text="blog.type ? blog.type.name : ''">认知升级</td>
            <td v-text="blog.recommend ? '是' : '否'">是</td>
            <td v-text="blog.published ? '发布' : '草稿'">发布</td>
            <td v-text="$dateFormat(blog.createTime, 'yyyy-mm-dd HH:mm')">2017-10-02 09:45</td>
            <td v-text="$dateFormat(blog.updateTime, 'yyyy-mm-dd HH:mm')">2017-10-02 09:45</td>
            <td>
              <a :href="'/#/admin/blogs/input?blogId='+blog.id+'&pageCurrent='+page.current" class="ui mini teal basic button">编辑</a>
              <a @click="deleteBlogId=blog.id;dialogVisible = true" class="ui mini red basic button">删除</a>
            </td>
          </tr>
          </tbody>
        </table>
        <!-- page -->
        <page :page="page" :father="this"></page>
      </div>
    </div>
  </div>
  <myfooter></myfooter>
</div>
</template>
<script>
import myfooter from '@/components/footer'
import navigate from '@/components/admin/navigate'
import page from '@/components/page'

export default {
  components: {myfooter, navigate, page},
  name: 'adminBlogs',
  data () {
    return {
      blogs: [],
      types: [],
      page: {'current': 1},
      navigateFlag: 1,
      whetheronhover: false,
      title: '',
      typeId: -1,
      typeName: '分类',
      whetherrecommend: false,
      dialogVisible: false,
      deleteBlogId: -1
    }
  },
  methods: {
    pagination (page) {
      this.$http({
        method: 'POST',
        url: this.$base_url + '/admin/blogs',
        data: {
          'page': page,
          'title': this.title,
          'typeId': this.typeId,
          'whetherrecommend': this.whetherrecommend
        }
      }).then(response => {
        this.blogs = response.data.data.blogs
        this.types = response.data.data.types
        this.page = response.data.data.page
      })
    },
    clearAll () {
      this.title = ''
      this.typeId = -1
      this.typeName = '分类'
      this.whetherrecommend = false
    },
    deleteBlog (id) {
      this.$http({
        method: 'GET',
        url: this.$base_url + '/admin/blogs/delete?id=' + id + '&pageCurrent=' + this.page.current
      }).then(response => {
        this.page = response.data.data.page
        this.pagination(this.page)
      })
    }
  },
  created () {
    var pageCurrent = (this.$route.query.pageCurrent === undefined || this.$route.query.pageCurrent == null) ? 1 : this.$route.query.pageCurrent
    this.page = {'current': parseInt(pageCurrent)}
    this.pagination(this.page)
  }
}
</script>
