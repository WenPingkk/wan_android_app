# 这是个组件化的项目
## 这个项目是怎样搭建起来的？
## 零 搭建好common_base。这是mvp的基础库.
- 这个库包含了很多依赖库;

## 一 base_library 
- 1.包含mvp的基础库以及许多的工具类.这个库被其他几个组件化的库使用到.

## 二 modulle_usercenter
- 1.这几个module里只有这个模块直接引导启动页的清单文件里设置了默认的启动的Activity,

其他的模块都只是声明了Activity的清单.
- 2.其他页面需要进行跳转用的是 阿里路由.

## 三 module_main 
- 1.这里面声明了这个app的绝大多数的Activity.
- 2.Activity之间跳转也有依赖阿里路由.

## 四 module_gank
- 1.这是项目的设置页面里用到的美图显示模块.

--- 

# 在gradle.properties 里配置了一个布尔值
- 标示各个业务组件是否以 application 单独运行，修改后需要同步才能生效
- isRunAlone=false;设置为false,在所有gradle中指引的清单文件只有userCenter模块里有启动页的配置;app会以application来运行.
- isRunAlone=true,则每个模块都有自己的启动页.就会以module来启动.直接引用的清单文件是在debug目录下的.
> 项目中的默认设置的是false的.
