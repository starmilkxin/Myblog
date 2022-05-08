<template>
<div>
  <!--头部导航栏-->
  <navigate :navigateFlag="navigateFlag" :father="this"></navigate>
  <!--中间内容-->
  <div  class="m-container m-padded-tb-big">
    <div class="ui container m-opacity m-opacity">
      <form id="blog-form" class="ui form">
        <div class="required field">
          <div class="ui left labeled input">
            <el-dropdown>
              <el-button type="success" v-text="flag">
                类型<i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="flag='原创'">原创</el-dropdown-item>
                <el-dropdown-item @click.native="flag='转载'">转载</el-dropdown-item>
                <el-dropdown-item @click.native="flag='翻译'">翻译</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <input type="text" name="title" placeholder="标题" v-model="title">
          </div>
        </div>

        <!-- <div class="required field"> -->
            <mavon-editor style="height:22em"
            placeholder="博客内容"
            codeStyle="monokai-sublime"
            v-model = "content"
            ></mavon-editor>
        <!-- </div> -->

        <div class="two fields">
            <label class="ui teal basic label" style="line-height:normal; margin:0px 0px 0px 6px">分类</label>
              <el-select v-model="typeId" filterable placeholder="分类" size="medium" style="width:500px;">
                <el-option
                  v-for="(type, typeIndex) in types" :key="typeIndex"
                  :label="type.name"
                  :value="type.id">
                </el-option>
              </el-select>
            <label class="ui teal basic label" style="line-height:normal; margin:0px 0px 0px 20px">标签</label>
            <el-select v-model="tagsId" placeholder="标签" multiple filterable size="medium" style="width:500px;">
              <el-option
                v-for="(tag, tagIndex) in tags" :key="tagIndex"
                :label="tag.name"
                :value="tag.id">
              </el-option>
            </el-select>

        </div>

        <div class="required field">
          <div class="ui left labeled input">
            <label class="ui teal basic label">首图</label>
            <input type="text" name="firstPicture" v-model="firstPicture" placeholder="首图引用地址">
          </div>
        </div>

        <div class="required field">
          <textarea name="description" v-model="description" placeholder="博客描述..." maxlength="150"></textarea>
        </div>

        <div class="inline fields">
          <div class="field">
            <div class="ui checkbox">
              <input type="checkbox" id="recommend" name="recommend" v-model="recommend" class="hidden">
              <label for="recommend" style="color: white;">推荐</label>
            </div>
          </div>
          <div class="field">
            <div class="ui checkbox">
              <input type="checkbox" id="shareStatement" name="shareStatement" v-model="shareStatement" class="hidden">
              <label for="shareStatement" style="color: white;">转载声明</label>
            </div>
          </div>
          <div class="field">
            <div class="ui checkbox">
              <input type="checkbox" id="appreciation" name="appreciation" v-model="appreciation" class="hidden">
              <label for="appreciation" style="color: white;">赞赏</label>
            </div>
          </div>
          <div class="field">
            <div class="ui checkbox">
              <input type="checkbox" id="commentabled" name="commentabled" v-model="commentabled" class="hidden">
              <label for="commentabled" style="color: white;">评论</label>
            </div>
          </div>
        </div>

        <div class="ui error message"></div>

        <div class="ui right aligned container">
          <button type="button" class="ui button" onclick="window.history.go(-1)" >返回</button>
          <button type="button" class="ui secondary button" @click="published=false;blogsPost()">保存</button>
          <button type="button" class="ui teal button" @click="published=true;blogsPost()">发布</button>
        </div>

      </form>
    </div>
  </div>
  <myfooter></myfooter>
</div>
</template>
<script>
import myfooter from '@/components/footer'
import navigate from '@/components/admin/navigate'
import router from '@/router/index.js'

export default {
  components: {myfooter, navigate},
  name: 'adminBlogsInput',
  data () {
    return {
      navigateFlag: 1,
      blogId: -1,
      flag: '原创',
      title: '',
      content: '',
      typeId: '',
      tagsId: [],
      firstPicture: '',
      description: '',
      appreciation: false,
      shareStatement: false,
      commentabled: false,
      published: false,
      recommend: false,
      types: [],
      tags: [],
      uuid: '',
      page: {}
    }
  },
  methods: {
    blogsInput (blogId) {
      this.$http({
        method: 'GET',
        url: this.$base_url + '/admin/blogs/input?blogId=' + blogId
      }).then(response => {
        var blog = response.data.data.blog
        if (blog !== undefined && blog != null) {
          this.flag = blog.flag
          this.title = blog.title
          this.content = blog.content
          this.typeId = blog.typeId
          if (blog.tagsId != null && blog.tagsId.length !== 0) {
            var strList = blog.tagsId.split(',')
            var tmp = null
            for (tmp in strList) {
              this.tagsId.push(parseInt(strList[tmp]))
            }
          }
          this.firstPicture = blog.firstPicture
          this.description = blog.description
          this.appreciation = blog.appreciation
          this.shareStatement = blog.shareStatement
          this.commentabled = blog.commentabled
          this.published = blog.published
          this.recommend = blog.recommend
        }
        this.uuid = response.data.data.uuid
        this.types = response.data.data.types
        this.tags = response.data.data.tags
      })
    },
    blogsPost () {
      this.$http({
        method: 'POST',
        url: this.$base_url + '/admin/blogsInput',
        data: {
          'id': this.blogId,
          'flag': this.flag,
          'title': this.title,
          'content': this.content,
          'typeId': this.typeId,
          'tagsId': this.tagsId,
          'firstPicture': this.firstPicture,
          'description': this.description,
          'appreciation': this.appreciation,
          'shareStatement': this.shareStatement,
          'commentabled': this.commentabled,
          'published': this.published,
          'recommend': this.recommend,
          'uuid': this.uuid
        }
      }).then(response => {
        if (response.data.code === 200) {
          router.push({path: '/admin/blogs?pageCurrent=' + this.page.current})
        } else {
          console.log(response.data.message)
        }
      })
    }
  },
  created () {
    this.blogId = parseInt(this.$route.query.blogId)
    if (this.blogId === undefined || this.blogId == null) {
      this.blogId = -1
    }
    var pageCurrent = (this.$route.query.pageCurrent === undefined || this.$route.query.pageCurrent == null) ? 1 : this.$route.query.pageCurrent
    this.page = {'current': parseInt(pageCurrent)}
    this.blogsInput(this.blogId)
  }
}
</script>
<style>
  .ui.form textarea{
      height: 17em;
  }
</style>
