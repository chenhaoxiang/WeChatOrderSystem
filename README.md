# WeChatOrderSystem  
sell - 微信点餐系统-SpringBoot开发   

在这里使用了微信公众平台的账号和微信开放平台的账号。  
需要自己去申请一些权限。  
目前用到的权限有:  
微信公众号的登录支付权限，消息推送权限。登录和消息推送可以在开发文档中使用测试账号。  
至于支付权限，则需要你自己去找朋友借借账号了。  
我是学习的廖师兄的视频进行的开发,需要有支付权限测试的，可以看这篇文档:  
https://github.com/Pay-Group/best-pay-sdk/blob/master/doc/borrowAccount.md  
还有微信开放平台的登录权限，这个也需要自己去认证或者找朋友借下了。  

在这里微信公众号接入开发和微信开放平台接入开发就没有重复造轮子了。    
分别使用了两个开源的SDK。  
链接如下  
https://github.com/Wechat-Group/weixin-java-tools 这个非常全，你看了就知道了  
https://github.com/Pay-Group/best-pay-sdk 这个就是廖师兄开发的SDK，支付使用的就是该SDK  


# linux服务器启动jar包项目

centos7提倡的用法  

cd /ets/systemd/system  
到这个目录下，新建一个 AAA.service，可以把AAA设置为项目名的  

vim AAA.service  
文件内容Start:  
```
[Unit]  
Description=AAA #描述  
After=syslog.target network.target  #依赖  

[Service]  
Type=simple  

ExecStart=/usr/bin/java -jar /opt/javaapps/AAA.jar  
#前面是java命令的绝对路径  后面是jar包的绝对路径  
ExecStop=/bin/kill -15 $MAINPID   

User=root  
Group=root   

[Install]  
WantedBy=multi-user.target  
```
文件结束END  

使用  
systemctl start AAA或者  
systemctl start AAA.service  
如果被改变了:  
先运行systemctl daemon-reload再运行systemctl start sell.service  

停止服务:  
systemctl stop AAA或者  
systemctl stop AAA.service  

开机自启动:   
systemctl enable AAA或者   
systemctl enable AAA.service   

不想开机启动:  
systemctl disable AAA或者  
systemctl  disable AAA.service  


# 项目总结   
 
## 微信特性  
模板消息，授权，支付和退款    

## Token认证   
在卖家端登录管理系统用到  
我在aop中已经屏蔽了，因为我没有微信开放平台的认证账号，无法登录    
可以自行去cn.chenhaoxiang.aspect.SellerAuthorizeAspect将类上的注解放开    

## WebSocket消息  
在买家下订单后，对买家端有消息提示并播放音乐   

## Redis缓存+分布式锁  
Redis的缓存的话，注意增删改更新缓存，否则会出现无法预知的后果  
在这里，如果有商品的抢购活动，就可以使用到Redis的分布式锁了  



我觉得该项目还有一些需要完善的地方  
比如卖家端没有权限控制  
比如应用没有独立，项目里面的商品，订单   
比如哪天修改了商品的代码，会影响到订单的部分  
应该把商品和订单拆分开来，作为两个独立的应用  

在这个项目中学到了很多。  
学到的最重要的不是一些知识点的学习，而是项目架构方面的学习，比如DTO，比如工具类，比如From,前端表单数据提交的实体类，比如应用独立，前后端分离，分布式和集群等等。  

在此感谢廖师兄分享的视频教程。  
