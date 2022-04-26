<template>
     <!--page-->
    <div class="ui bottom attached segment" v-if="page.total>1">
        <div class="ui middle aligned five column grid">
            <div class="column">
            <a v-on:click="paging(1)" class="ui mini teal basic button">首页</a>
            </div>
            <div class="column">
            <a v-on:click="paging(page.current - 1)" class="ui mini teal basic button" v-if="page.current -1 > 0">上一页</a>
            <a class="ui mini grey basic button" v-else>上一页</a>
            </div>
            <div class="center aligned column">
            <a class="ui mini teal basic button">第{{page.current}}页</a>
            </div>
            <div class="right aligned column">
            <a v-on:click="paging(page.current + 1)" class="ui mini teal basic button" v-if="page.total > page.current">下一页</a>
            <a class="ui mini grey basic button" v-else>下一页</a>
            </div>
            <div class="right aligned column">
            <a v-on:click="paging(page.total)" class="ui mini teal basic button" >尾页</a>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  name: 'page',
  props: ['page', 'father'], // 接收父组件传来的值
  methods: {
    paging (n) {
      this.page.current = n
      this.father.pagination(this.page)
    }
  }
}
</script>
