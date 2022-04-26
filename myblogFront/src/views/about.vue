<template>
<div>
    <!--头部导航栏-->
    <navigate :navigateFlag="navigateFlag" :father="this"></navigate>
    <!--中间内容-->
    <div  class="m-container m-padded-tb-big">
    <div class="ui container m-opacity">

    <div class="ui stackable grid">
        <div class="eleven wide column">
        <div class="ui segment">
            <img src="@/../static/images/about_avatar.jpg" alt="" class="ui rounded image">
        </div>
        </div>
        <div class="five wide column">
        <div class="ui top attached segment">
            <div class="ui header">关于我</div>
        </div>
        <div class="ui attached segment">
            <p class="m-text">阿鑫，一个独立开发者，折腾在 0 和 1 世界的弱鸡；一个终身学习者，誓将学习无限循环. 希望结识可以共同成长的小伙伴.</p>
            <p class="m-text">热爱编程，喜欢折腾，正在探索高效学习编程技术的方法...</p>
        </div>
        <div class="ui attached segment">
            <div class="ui orange basic left pointing label">编程</div>
            <div class="ui orange basic left pointing label">游戏</div>
            <div class="ui orange basic left pointing label">运动</div>
        </div>
<!--          <div class="ui attached segment">-->
<!--            <div class="ui teal basic left pointing label m-margin-tb-tiny">Java</div>-->
<!--            <div class="ui teal basic left pointing label m-margin-tb-tiny">JavaScript</div>-->
<!--            <div class="ui teal basic left pointing label m-margin-tb-tiny">Node</div>-->
<!--            <div class="ui teal basic left pointing label m-margin-tb-tiny">React</div>-->
<!--            <div class="ui teal basic left pointing label m-margin-tb-tiny">RN</div>-->
<!--            <div class="ui teal basic left pointing label m-margin-tb-tiny">MYSQL</div>-->
<!--            <div class="ui teal basic left pointing label m-margin-tb-tiny">...</div>-->
<!--          </div>-->
        <div class="ui bottom attached segment">
            <a class="ui qq circular icon button" data-content="1016231041" data-position="bottom center"><i class="qq icon"></i></a>
            <a class="ui wechat circular icon button" ><i class="weixin icon"></i></a>
            <a class="ui github circular icon button" href="@{https://github.com/starmilkxin}" target="_blank"><i class="github icon"></i></a>
        </div>

        <div class="ui wechat-qr flowing popup transition hidden">
            <img src="@/../static/images/wechat.jpg" alt="" class="ui rounded image" style="width: 110px">
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

export default {
  components: {navigate, myfooter},
  name: 'about',
  data () {
    return {
      navigateFlag: 5
    }
  }
}
</script>
