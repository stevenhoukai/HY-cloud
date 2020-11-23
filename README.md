# HY-cloud
项目为标准springcloud项目架构
官网的设计理念，如果有服务间调用，建议使用feign+hystrix进行资源隔离与降低熔断处理！

整体架构说明：

项目需要整合以下技术栈:

后端:springboot +springcloud全家桶 + quartz + security + JPA + redis + mysql

前端:HY-react（antd+axios+redux+react脚手架）

[点这里查看]: https://github.com/stevenhoukai/HY-cloud



项目整体搭建思路:

1、提取公用组件bapi模块，项目依赖文件由父工程HY-cloud统一管理，父工程统一管理打包

2、新建具体微服务继承公用组件，只专注业务逻辑开发

3、集成springcloud全套桶(ribbon、feign、eureka、hystrix、zuul、config)

4、开发模式前后端分离，前端react前台展现，后端springboot业务处理，json通讯，springcloud整合


项目启动说明:

本机hosts需要做如下配置:

如果需要实现eureka集群以及zuul需要配置下面全部
在本机host文件添加即可

127.0.0.1 eureka8001.com

127.0.0.1 myzuul.com

eureka启动成功之后 启动provider去注册服务；
zuul作为微服务内部网关接收所有请求并转发

**相关服务类项目启动前提:
mysql
redis的好处：
1、可做缓存,全内存操作、单线程、底层使用epoll以及独特的数据结构，效率非常高效
2、分布式id生成方便
3、处理高并发利器
4、分布式锁实现方便
rabbitmq也是建议加入，如果需要config-bus动态修改配置文件，此组件必须引入**


eureka启动不依赖第三方软件

provider,quartzprovider启动前必须启动mysql,否则会启动失败







