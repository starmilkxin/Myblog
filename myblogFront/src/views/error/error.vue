<template>
<div>
  <!--头部导航栏-->
  <navigate :navigateFlag="navigateFlag" :father="this"></navigate>
  <br>
  <br>
  <br>
  <div class="m-container-small m-padded-tb-massive">
    <div class="ui error message m-padded-tb-huge" >
      <div class="ui contianer">
        <h2>404</h2>
        <p>对不起，你访问的资源不存在</p>
      </div>
    </div>
  </div>
  <br>
  <br>
  <br>
  <br>
    <!-- 尾部联系方式、音乐盒-->
    <myfooter></myfooter>
</div>
</template>
<script>
import navigate from '@/components/navigate'
import myfooter from '@/components/footer'

export default {
  components: {navigate, myfooter},
  name: 'error',
  data () {
    return {
      navigateFlag: -1
    }
  }
}
</script>
