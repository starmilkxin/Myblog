<template>
<div>
    <!--头部导航栏-->
    <navigate :navigateFlag="navigateFlag" :father="this"></navigate>

    <!--中间内容-->
    <div id="waypoint" class="m-container-small m-padded-tb-big animated fadeIn">
        <div class="ui container m-opacity">
        <div class="ui top attached segment">
            <div class="ui horizontal link list">
            <div class="item">
                <img :src="blog.userAvatar" alt="&nbsp" class="ui avatar image">
                <div class="content"><a href="#" class="header" v-text="blog.userNickname">XIN</a></div>
            </div>
            <div class="item">
                <i class="calendar icon"></i><span text="">{{$dateFormat(blog.createTime, 'yyyy-mm-dd')}}&nbsp;&nbsp;&nbsp;&nbsp;</span>
            </div>
    <!--          <div class="item">-->
    <!--            <i class="eye icon" th:text="${blog.views}">2342</i>-->
    <!--          </div>-->
            </div>
        </div>
        <div class="ui attached segment">
            <!--图片区域-->
            <img :src="blog.firstPicture" alt="" class="ui fluid rounded image">
        </div>
        <div class="ui  attached padded segment" style="padding: 0px 0px 0px 0px">
            <!--内容-->
            <div class="ui right aligned basic segment">
            <div class="ui orange basic label" v-text="blog.flag">原创</div>
            </div>

            <h2 class="ui center aligned header" v-text="blog.title">关于刻意练习的清单</h2>
            <br>

            <!-- <div id="content" class="typo  typo-selection js-toc-content m-padded-lr-responsive m-padded-tb-large" v-html="blog.content">
            </div> -->
            <mavon-editor
            :value="blog.content"
            defaultOpen="preview"
            :boxShadow="false"
            :editable="false"
            :subfield="false"
            :toolbarsFlag="false"
            :aligncenter="true"
            codeStyle="monokai-sublime"
            ></mavon-editor>

            <div class="m-padded-lr-responsive">
            <br/>
            <!-- 分类-->
            <div class="ui teal basic label" v-text="blog.typeName">认知升级</div>
            <!--标签-->
            <div class="ui pink basic left pointing label" v-for="(tag, tagIndex) in blog.tags" :key="tagIndex" v-text="tag.name">方法论</div>
            </div>
            <br/>
            <!--赞赏-->
            <div class="ui center aligned basic segment" v-if="blog.appreciation == true">
            <button id="payButton" class="ui orange basic circular button">赞赏</button>
            </div>
            <div class="ui payQR flowing popup transition hidden">
            <div class="ui orange basic label">
                <div class="ui images" style="font-size: inherit !important;">
                <div class="image">
                    <img src="@/../static/images/wechat.jpg" alt="" class="ui rounded bordered image" style="width: 120px">
                    <div>微信</div>
                </div>
                </div>
            </div>
            </div>

        </div>
        <div class="ui attached positive message">
            <!--博客信息-->
            <div class="ui middle aligned grid">
            <div class="eleven wide column">
                <ui class="list">
                <li>作者：<span v-text="blog.userNickname"></span> </li>
                <li>发表时间：<span v-text="$dateFormat(blog.createTime, 'yyyy-mm-dd')"> </span></li>
                <li>更新时间：<span v-text="$dateFormat(blog.updateTime, 'yyyy-MM-dd')"> </span></li>
                <li>版权声明：自由转载-非商用-非衍生-保持署名</li>
                </ui>
            </div>
            <div class="five wide column">
                <img :src="blog.userAvatar" alt="" class="ui floated rounded bordered image" style="width: 180px">
            </div>
            </div>
        </div>
        <div class="ui bottom attached segment">
            <!--留言区域列表-->
            <div id="comment-container" class="ui teal segment">
                <div class="ui threaded comments" style="max-width: 100%;">
                <h3 class="ui dividing header">评论</h3>
                <!-- 所有评论 -->
                <div class="comment" v-for="(comment, commentIndex) in comments" :key="commentIndex">
                    <a class="avatar">
                    <img :src="comment.avatar">
                    </a>
                    <div class="content">
                    <a class="author" v-text="comment.nickname">Matt</a>
                    <div class="ui mini basic teal left pointing label m-padded-mini" v-if="comment.adminComment == 1">博主</div>
                    <div class="metadata">
                        <span class="date" v-text="$dateFormat(comment.createTime, 'yyyy-mm-dd')">Today at 5:42PM</span>
                    </div>
                    <div class="text" v-text="comment.content">
                        How artistic!
                    </div>
                    <div class="actions">
                        <a class="reply" v-on:click="replyComment(comment.id, comment.nickname)">回复</a>
                    </div>
                    </div>
                    <div class="comments" :if="comment.replyComments.length>0">
                    <!-- 评论中的评论 -->
                    <div class="comment" v-for="(reply, replyIndex) in comment.replyComments" :key="replyIndex">
                        <a class="avatar">
                        <img :src="reply.avatar">
                        </a>
                        <div class="content">
                        <a class="author" >
                            <span v-text="reply.nickname">小红</span>
                            <div class="ui mini basic teal left pointing label m-padded-mini" v-if="reply.adminComment == 1">博主</div>
                            &nbsp;<span v-text="'@ ' + reply.parentCommentNickname" class="m-teal">@ 小白</span>
                        </a>
                        <div class="metadata">
                            <span class="date" v-text="$dateFormat(reply.createTime, 'yyyy-mm-dd')">Today at 5:42PM</span>
                        </div>
                        <div class="text" v-text="reply.content">
                            How artistic!
                        </div>
                        <div class="actions">
                            <a class="reply" v-on:click="replyComment(reply.id, reply.nickname)">回复</a>
                        </div>
                        </div>
                    </div>
                    </div>
                </div>
                </div>
            </div>
            <!-- page -->
            <page :page="page" :father="this"></page>

            <div id="comment-form" class="ui form" v-if="blog.commentabled == true">
            <div class="field">
                <textarea v-model='content' :placeholder="myplaceholder"></textarea>
            </div>
            <div class="fields">
                <div class="field m-mobile-wide m-margin-bottom-small">
                <div class="ui left icon input">
                    <i class="user icon"></i>
                    <input v-model="nickname" type="text" placeholder="昵称"/>
                </div>
                </div>
                <div class="field  m-margin-bottom-small m-mobile-wide">
                <button id="commentpost-btn" class="ui teal button m-mobile-wide" @click="postComment(blogId, parentCommentId, nickname, content)"><i class="edit icon"></i>发布</button>
                </div>
            </div>
            </div>
        </div>
        </div>
    </div>

    <!-- 尾部联系方式、音乐盒-->
    <myfooter></myfooter>
</div>
</template>

<script>
import navigate from '@/components/navigate'
import myfooter from '@/components/footer'
import page from '@/components/page'

export default {
  components: {navigate, myfooter, page},
  name: 'blog',
  data () {
    return {
      navigateFlag: 0,
      blogId: -1,
      blog: {},
      comments: [],
      content: '',
      myplaceholder: '请输入评论信息...',
      parentCommentId: -1,
      nickname: '',
      toNickname: '',
      page: {'current': 1}
    }
  },
  methods: {
    getBlog (blogId) {
      this.$http({
        method: 'GET',
        url: this.$base_url + '/x/blogContent?blogId=' + blogId
      }).then(response => {
        this.blog = response.data.data.blog
      })
    },
    pagination (page) {
      this.$http({
        method: 'POST',
        url: this.$base_url + '/x/comments?blogId=' + this.blogId,
        data: page
      }).then(response => {
        this.comments = response.data.data.comments
        this.page = response.data.data.page
      })
    },
    postComment (blogId, parentCommentId, nickname, content) {
      this.$http({
        method: 'POST',
        url: this.$base_url + '/x/commentsInput?blogId=' + blogId,
        data: {'blogId': blogId,
          'parentCommentId': parentCommentId,
          'nickname': nickname,
          'content': content,
          'parentCommentNickname': this.toNickname}
      }).then(response => {
        this.pagination(this.page)
        this.content = ''
        this.myplaceholder = '请输入评论信息...'
        this.parentCommentId = -1
      })
    },
    replyComment (parentCommendId, nickname) {
      this.parentCommentId = parentCommendId
      this.content = ''
      this.myplaceholder = '@' + nickname
      this.toNickname = nickname
      let container = this.$el.querySelector('#comment-form')
      container.scrollIntoView(false) // false:滚到底部 true:滚到顶部
    }
  },
  created () {
    this.blogId = (this.$route.query.blogId === undefined ? this.blogId : this.$route.query.blogId)
    var user = JSON.parse(localStorage.getItem('User'))
    if (user !== null) {
      this.nickname = user.nickname
    }
    this.getBlog(this.blogId)
    this.pagination(this.page)
  }
}
</script>
