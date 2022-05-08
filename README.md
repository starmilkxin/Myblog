# XINBlog
一款简单的自用博客。

myblog为旧版的，前后端不分离。

之后重构为前后端分离，myblogFront为前端，myblogBack为后端。

技术栈：
+ 前端： Vue + JS + axios + Semantic UI + Element UI
+ 后端： Spring Boot + Mybatis + MySQL + Redis + Kafka

功能：
+ 基本的博客首页展示、分类/标签博客展示、博客评论(脏词过滤)、时间轴展示
+ 管理员登录、编辑分类/标签、编辑/发送博客、评论消息的观看与跳转
+ 通过枚举自定义异常，并增加了全局异常处理，前端相应地展示错误信息