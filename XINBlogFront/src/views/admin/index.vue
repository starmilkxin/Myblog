<template>
<div>
    <!--中间内容-->
    <div  class="m-container-small m-padded-tb-big my-height">
        <div class="ui container">
        <div class="ui success large  message">
            <h3>Hi,</h3>
            <p>阿鑫，欢迎登录！系统会在 <span id="seconds" class="text-danger" v-text="seconds">3</span> 秒后自动跳转,您也可以点此 <a id="target" href="/#/admin/blogs" class="text-primary">链接</a>, 手动跳转!</p>
        </div>

        <img src="https://cdn.jsdelivr.net/gh/starmilkxin/picturebed/img/探脸.jpg" alt="" class="ui rounded bordered fluid image">
        </div>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>

    <myfooter></myfooter>
    </div>
</div>
</template>
<script>
import myfooter from '@/components/footer'
import router from '@/router/index.js'

export default {
  components: {myfooter},
  name: 'adminIndex',
  data () {
    return {
      seconds: 3
    }
  },
  methods: {
    time () {
      this.seconds--
      if (this.seconds === 0) {
        router.push({path: '/admin/blogs'})
      }
    }
  },
  created () {
    setInterval(() => { this.time() }, 1000)
  }
}
</script>
