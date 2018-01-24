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