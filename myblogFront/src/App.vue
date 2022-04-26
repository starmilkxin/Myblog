<template>
  <div id="app">
    <router-view/>
      <div class="snow-container"></div>
  </div>
</template>

<script>
// 下雪
import '@/../static/js/snowy.js'

export default {
  name: 'App'
}
</script>
<style type="text/css">
    .snow-container{position:fixed;top:0;left:0;width:100%;height:100%;pointer-events:none;z-index:100001;}
</style>
