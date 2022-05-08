<template>
<div>
<br>
<br>
<br>
 <div class="m-container-small m-padded-tb-massive" style="max-width: 30em !important;">
   <div class="ur container m-opacity">
     <div class="ui middle aligned center aligned grid">
       <div class="column">
         <h2 class="ui teal image header">
           <div class="content">
             管理后台登录
           </div>
         </h2>
         <div class="ui large form">
           <div class="ui  segment">
             <div class="field">
               <div class="ui left icon input">
                 <i class="user icon"></i>
                 <input v-model="username" placeholder="用户名">
               </div>
             </div>
             <div class="field">
               <div class="ui left icon input">
                 <i class="lock icon"></i>
                 <input v-model="password" type="password" placeholder="密码" autocomplete>
               </div>
             </div>
             <button class="ui fluid large teal button" @click="login()" type="button">登   录</button>
           </div>

           <div class="ui error mini message"></div>
           <div class="ui mini negative message" v-if="message!=null && message.length > 0" v-text="message">用户名和密码错误</div>

         </div>

       </div>
     </div>
   </div>
 </div>
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
import router from '@/router/index.js'

export default {
  components: {myfooter},
  name: 'login',
  data () {
    return {
      username: '',
      password: '',
      message: ''
    }
  },
  methods: {
    login () {
      this.$http({
        method: 'POST',
        url: this.$base_url + '/admin/login',
        async: false,
        data: {
          'username': this.username,
          'password': this.password
        }
      }).then(response => {
        this.message = (response.data.code === 200 ? null : response.data.data.ErrorResponse.message)
        if (response.data.data.token !== undefined && response.data.data.token !== null) {
          var token = response.data.data.token
          // 将数据以JSON形式的字符串存入
          localStorage.setItem('Authorization', JSON.stringify(token))
          router.push({name: 'adminIndex'})
        }
      })
    }
  }
}
</script>
