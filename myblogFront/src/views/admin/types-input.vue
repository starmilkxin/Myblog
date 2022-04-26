<template>
<div>
  <!--头部导航栏-->
  <navigate :navigateFlag="navigateFlag" :father="this"></navigate>
  <!--中间内容-->
  <div  class="m-container-small m-padded-tb-big my-height">
    <div class="ui container m-opacity">
        <div class="field">
          <div class="ui right labeled input">
            <label class="ui teal basic label">名称</label>
            <input type="text" name="name" placeholder="分类名称" v-model="name">
            <button type="button" class="ui button" onclick="window.history.go(-1)" >返回</button>
            <button class="ui teal submit button" @click="typePost()">提交</button>
          </div>
        </div>
        <div class="ui error message" v-if="message!=null && message.length > 0" v-text="message">sth 错误</div>
    </div>
  </div>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>

  <myfooter></myfooter>
</div>
</template>
<script>
import myfooter from '@/components/footer'
import navigate from '@/components/admin/navigate'
import router from '@/router/index.js'

export default {
  components: {myfooter, navigate},
  name: 'adminTypesInput',
  data () {
    return {
      id: -1,
      name: '',
      page: {'current': 1},
      navigateFlag: 2,
      message: ''
    }
  },
  methods: {
    typePost () {
      this.$http({
        method: 'POST',
        url: this.$base_url + '/admin/types/input',
        async: false,
        data: {
          'id': this.id,
          'name': this.name
        }
      }).then(response => {
        if (response.data.code === 200) {
          router.push({path: '/admin/types?pageCurrent=' + this.page.current})
        } else {
          this.message = response.data.message
        }
      })
    }
  },
  created () {
    var pageCurrent = (this.$route.query.pageCurrent === undefined || this.$route.query.pageCurrent == null) ? 1 : this.$route.query.pageCurrent
    this.page = {'current': parseInt(pageCurrent)}
    var id = (this.$route.query.id === undefined || this.$route.query.id == null) ? -1 : this.$route.query.id
    this.id = parseInt(id)
    var name = (this.$route.query.name === undefined || this.$route.query.name == null) ? '' : this.$route.query.name
    this.name = name
  }
}
</script>
