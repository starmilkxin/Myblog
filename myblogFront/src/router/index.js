import Vue from 'vue'
import Router from 'vue-router'
import index from '@/views/index'
import search from '@/views/search'
import types from '@/views/types'
import tags from '@/views/tags'
import archives from '@/views/archives'
import about from '@/views/about'
import blog from '@/views/blog'
import login from '@/views/admin/login'
import adminIndex from '@/views/admin/index'
import adminBlogs from '@/views/admin/blogs'
import adminBlogsInput from '@/views/admin/blogs-input'
import adminTypes from '@/views/admin/types'
import adminTypesInput from '@/views/admin/types-input'
import adminTags from '@/views/admin/tags'
import adminTagsInput from '@/views/admin/tags-input'
import message from '@/views/admin/message'
import error from '@/views/error/error'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: index,
      meta: {
        title: "XIN's BLOG"
      }
    },
    {
      path: '/index',
      name: 'index',
      component: index,
      meta: {
        title: "XIN's BLOG"
      }
    },
    {
      path: '/search',
      name: 'search',
      component: search,
      meta: {
        title: '搜索结果'
      }
    },
    {
      path: '/types',
      name: 'types',
      component: types,
      meta: {
        title: '分类'
      }
    },
    {
      path: '/tags',
      name: 'tags',
      component: tags,
      meta: {
        title: '标签'
      }
    },
    {
      path: '/archives',
      name: 'archives',
      component: archives,
      meta: {
        title: '时间轴'
      }
    },
    {
      path: '/about',
      name: 'about',
      component: about,
      meta: {
        title: '关于我'
      }
    },
    {
      path: '/blog',
      name: 'blog',
      component: blog,
      meta: {
        title: '博客详情'
      }
    },
    {
      path: '/admin',
      name: 'login',
      component: login,
      meta: {
        title: '博客管理登录'
      }
    },
    {
      path: '/admin/index',
      name: 'adminIndex',
      component: adminIndex,
      meta: {
        title: '登录成功'
      }
    },
    {
      path: '/admin/blogs',
      name: 'adminBlogs',
      component: adminBlogs,
      meta: {
        title: '博客管理'
      }
    },
    {
      path: '/admin/blogs/input',
      name: 'adminBlogsInput',
      component: adminBlogsInput,
      meta: {
        title: '博客发布'
      }
    },
    {
      path: '/admin/types',
      name: 'adminTypes',
      component: adminTypes,
      meta: {
        title: '分类管理'
      }
    },
    {
      path: '/admin/types/input',
      name: 'adminTypesInput',
      component: adminTypesInput,
      meta: {
        title: '分类修改'
      }
    },
    {
      path: '/admin/tags',
      name: 'adminTags',
      component: adminTags,
      meta: {
        title: '标签管理'
      }
    },
    {
      path: '/admin/tags/input',
      name: 'adminTagsInput',
      component: adminTagsInput,
      meta: {
        title: '标签修改'
      }
    },
    {
      path: '/admin/message',
      name: 'message',
      component: message,
      meta: {
        title: '消息'
      }
    },
    {
      path: '*',
      name: 'error',
      component: error,
      meta: {
        title: '页面走丢了'
      }
    }
  ]
})
