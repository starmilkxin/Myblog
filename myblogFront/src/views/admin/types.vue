<template>
<div>
  <el-dialog
    title="提示"
    :visible.sync="dialogVisible"
    width="30%"
    >
    <span>确定要删除吗</span>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="dialogVisible = false;deleteType(deleteTypeId)">确 定</el-button>
    </span>
  </el-dialog>

  <!--头部导航栏-->
  <navigate :navigateFlag="navigateFlag" :father="this"></navigate>
  <!--中间内容-->
  <div  class="m-container-small m-padded-tb-big my-height">
    <div class="ui container m-opacity">
      <table class="ui compact teal table">
        <thead>
          <tr>
            <th></th>
            <th>名称</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(type,typeIndex) in types" :key="typeIndex">
            <td v-text="typeIndex+(page.current-1)*page.limit+1">1</td>
            <td v-text="type.name">刻意练习清单</td>
            <td>
              <a :href="'/#/admin/types/input?id='+type.id+'&name='+type.name+'&pageCurrent='+page.current" class="ui mini teal basic button">编辑</a>
              <a :href="'/#/admin/types/input?pageCurrent='+page.current" class="ui mini yellow basic button">新增</a>
              <a @click="deleteTypeId=type.id;dialogVisible = true" class="ui mini red basic button">删除</a>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- page -->
    <page :page="page" :father="this"></page>
  </div>
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
import page from '@/components/page'

export default {
  components: {myfooter, navigate, page},
  name: 'adminTypes',
  data () {
    return {
      types: [],
      page: {'current': 1},
      navigateFlag: 2,
      dialogVisible: false,
      deleteTypeId: -1
    }
  },
  methods: {
    pagination (page) {
      this.$http({
        method: 'POST',
        url: this.$base_url + '/admin/types',
        data: page
      }).then(response => {
        this.types = response.data.data.types
        this.page = response.data.data.page
      })
    },
    deleteType (id) {
      this.$http({
        method: 'GET',
        async: false,
        url: this.$base_url + '/admin/types/delete?id=' + id + '&pageCurrent=' + this.page.current
      }).then(response => {
        this.page = response.data.data.page
        this.pagination(this.page)
      })
    }
  },
  created () {
    var pageCurrent = (this.$route.query.pageCurrent === undefined || this.$route.query.pageCurrent == null) ? 1 : this.$route.query.pageCurrent
    this.page = {'current': parseInt(pageCurrent)}
    this.pagination(this.page)
  }
}
</script>
