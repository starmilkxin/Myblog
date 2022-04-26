<template>
<div>
  <br>
  <br>
  <!--音乐盒-->
  <aplayer :music="audio[0]" :shuffle="true" :list="audio"  :showLrc="false" :listFolded="true" :autoplay="false" :volume=0.5 :mutex=true></aplayer>
  <footer class="ui inverted vertical segment m-padded-tb-small m-opacity" >
  <div class="ui center aligned container">
    <div class="ui inverted divided stackable grid">
      <div class="three wide column">
        <div class="ui inverted link list">
          <div class="item">
            <img src="@/../static/images/wechat.jpg" th:src="@{/images/wechat.jpg}"  class="ui rounded image" alt="" style="width: 110px">
          </div>
        </div>
      </div>
      <div class="three wide column">
        <h4 class="ui inverted header m-text-thin m-text-spaced ">联系我</h4>
        <div class="ui inverted link list">
          <a class="item m-text-thin">Email：jingxin_1@163.com</a>
          <a class="item m-text-thin">QQ：1016231041</a>
        </div>
      </div>
      <div class="seven wide column">
        <h4 class="ui inverted header m-text-thin m-text-spaced ">XIN's Blog</h4>
        <p class="m-text-thin m-text-spaced m-opacity-mini">这是我的个人博客、会分享关于编程、写作、思考相关的任何内容，希望可以给来到这儿的人有所帮助...</p>
      </div>
    </div>
    <div class="ui inverted section divider"></div>
    <div style="color: #F08047;margin-top: -18px" class="ui inverted m-text-thin m-text-spaced">我的小屋已营业：<span id="htmer_time" class="item m-text-thin"></span> (✿◕‿◕✿)</div>
    <a href="https://beian.miit.gov.cn" target="_blank">沪ICP备2021033460号-1</a>
  </div>
</footer>
</div>

</template>

<script>
import aplayer from 'vue-aplayer'
// 时间
import '@/../static/js/myclock.js'

export default {
  name: 'myfooter',
  data () {
    return {
      // 音频列表
      audio: [
        {
          title: 'The First Snowflakes',
          artist: 'Richard Clayderman',
          src: '@/../static/music/Richard Clayderman-初雪（The First Snowflakes）.mp3',
          pic: '@/../static/music/cover/music_cover7.jpg',
          theme: 'rgba(206,253,243,0.47)'
        },
        {
          title: 'Snowdream',
          artist: 'Bandari',
          src: '@/../static/music/Snowdream.flac',
          pic: '@/../static/music/cover/music_cover4.jpg',
          theme: '#8df5e4'
        },
        {
          title: 'Nature\'s Path',
          artist: 'Dan Gibson\'s Solitudes',
          src: '@/../static/music/Dan Gibson\'s Solitudes - Nature\'s Path.mp3',
          pic: '@/../static/music/cover/music_cover1.jpg',
          theme: '#ef8a25'
        },
        {
          title: 'Days - Nuit Silencieuse.mp3',
          artist: 'Days',
          src: '@/../static/music/Days - Nuit Silencieuse.mp3',
          pic: '@/../static/music/cover/music_cover13.png',
          theme: '#ca6afd'
        },
        {
          title: 'Moonglow',
          artist: 'Bandari',
          src: '@/../static/music/Moonglow.flac',
          pic: '@/../static/music/cover/music_cover5.jpg',
          theme: '#1cecca'
        },
        {
          title: 'Endlesshorizon',
          artist: 'Bandari',
          src: '@/../static/music/Endlesshorizon.mp3',
          pic: '@/../static/music/cover/music_cover8.jpg',
          theme: '#8df5e4'
        },
        {
          title: 'One Day In Spring',
          artist: 'Bandari',
          src: '@/../static/music/One Day In Spring.flac',
          pic: '@/../static/music/cover/music_cover11.jpg',
          theme: '#34f63c'
        },
        {
          title: 'Liberstraum',
          artist: 'Richard Clayderman',
          src: '@/../static/music/Richard Clayderman - Liberstraum.mp3',
          pic: '@/../static/music/cover/music_cover6.png',
          theme: '#a05bf3'
        },
        {
          title: '思乡曲',
          artist: 'Richard Clayderman',
          src: '@/../static/music/Richard Clayderman - 思乡曲.flac',
          pic: '@/../static/music/cover/music_cover2.jpg',
          theme: '#d6b2f6'
        },
        {
          title: '水边的阿狄丽娜',
          artist: 'Richard Clayderman',
          src: '@/../static/music/Richard Clayderman - 水边的阿狄丽娜.flac',
          pic: '@/../static/music/cover/music_cover9.jpg',
          theme: 'rgba(164,79,236,0.85)'
        },
        {
          title: '爱的纪念',
          artist: 'Richard Clayderman',
          src: '@/../static/music/Richard Clayderman - 爱的纪念.flac',
          pic: '@/../static/music/cover/music_cover10.jpg',
          theme: '#ee599e'
        },
        {
          title: '雨的印记',
          artist: 'Yiruma',
          src: '@/../static/music/Yiruma - 雨的印记.mp3',
          pic: '@/../static/music/cover/music_cover12.jpg',
          theme: '#46bef8'
        },
        {
          title: '月明かり',
          artist: '本田宗,tororo',
          src: '@/../static/music/本田宗,tororo - 月明かり.mp3',
          pic: '@/../static/music/cover/music_cover3.jpg',
          theme: '#ee78df'
        }
      ]
    }
  },
  components: { aplayer }
}
</script>
