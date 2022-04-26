<template>
<div>
  <!--头部导航栏-->
  <navigate :navigateFlag="navigateFlag" :father="this"></navigate>
<br/>
<br/>
<br/>
  <!-- 中间内容 -->
    <el-container>
        <el-aside width="180px">Aside</el-aside>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>消息</span>
            </div>
            <div v-for="(message, messageIndex) in messages" :key="messageIndex" class="text item">
                <el-card>
                <el-button size="mini" type="danger" v-if="message.status == 0" @click="messageUpdate(1, message.id)"> 未读 </el-button>
                <el-button size="mini" type="success" v-if="message.status == 1" @click="messageUpdate(0, message.id)"> 已读 </el-button>
                {{'[' + $dateFormat(message.createTime, 'yyyy-mm-dd HH:MM') + ']&nbsp;&nbsp;&nbsp;'}}
                <nobr v-text="message.fromNickname" style="color:purple"></nobr>&nbsp;&nbsp;&nbsp;
                <nobr v-text="((message.toNickname == null || message.toNickname == '') ? '评论了博客' : '回复了评论')" style="color:green"></nobr>&nbsp;&nbsp;&nbsp; : &nbsp;&nbsp;
                {{message.content.toString().substring(0, 8) + '&nbsp;&nbsp;&nbsp;......&nbsp;&nbsp;&nbsp;'}}
                <a class="text-primary" :href="'/#/blog?blogId=' + message.blogId" target="_blank" @click="checkAndUpdate(message.id)">点击查看</a>
                </el-card>
            </div>
            <!-- page -->
            <page :page="page" :father="this"></page>
        </el-card>
    </el-container>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
  <myfooter></myfooter>
</div>
</template>

<script>
import myfooter from '@/components/footer'
import navigate from '@/components/admin/navigate'
import page from '@/components/page'

export default {
  components: {myfooter, navigate, page},
  name: 'message',
  data () {
    return {
      navigateFlag: 4,
      page: {'current': 1},
      messages: []
    }
  },
  methods: {
    pagination (page) {
      this.$http({
        method: 'POST',
        url: this.$base_url + '/admin/message',
        data: page
      }).then(response => {
        this.messages = response.data.data.messages
        this.page = response.data.data.page
      })
    },
    messageUpdate (status, messageId) {
      this.$http({
        method: 'GET',
        url: this.$base_url + '/admin/messageUpdate?status=' + status + '&id=' + messageId
      }).then(response => {
        this.pagination(this.page)
      })
    },
    checkAndUpdate (messageId) {
      this.messageUpdate(1, messageId)
    }
  },
  created () {
    this.pagination(this.page)
  }
}
</script>
<style>
  .el-container {
    height: 100%;
  }

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 24px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 650px;
  }
</style>
