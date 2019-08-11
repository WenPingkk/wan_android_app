# 项目笔记
## 1.为什么在app下,没activity都可以跑user_center下的SplashActivity?
- 注意配置
```
    sourceSets {
        main {
            if (isRunAlone.toBoolean()) {
                manifest.srcFile 'src/main/debug/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/AndroidManifest.xml'
                java {
                    // 全部 module 一起编译时剔除 debug 目录
                    exclude '**/debug/**'
                }
            }
        }
    }
```
# 怎么会遇到 路由跳转失败,打log也看不出来的情况?
- 1.查看模块时候配置好了 阿里路由;2.查看路径是不是有误解.
