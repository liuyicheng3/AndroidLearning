# AndroidTest
## 1  每个feature 讲解  放入一个新的包名下 eg:com.lyc.study.go***; 每个测试demo的入口使用Go****Activity命名activity（中间的跳转activity不要使用的Go开头）， layout使用  act、view、dialog方式命名


## 自定义Plugin 

需要先去PluginA把数据部署到本地maven库

运行pluginA 和 pluginB的工程部署到本地  

演示了plugin的两种用处：
1. 在plugin里面定义一些属性，给app的build.gradle使用
2. 在app的build.gradle配置一些属性，然后给plugin的task用


