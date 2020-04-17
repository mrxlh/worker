# worker
基于消息补偿的最终一致性 - 方案之一

本地消息表的方案最初由ebay工程师提出.

核心思想:是将分布式事务拆分成本地事务进行处理,通过消息日志的方式来异步执行,本地消息表中是一种业务耦合的设计,消息生产方需要额外建一个事务消息表

并记录消息发送状态,消息消费方需要处理这个消息,并完成自己的业务逻辑,另外会有一个异步机制来定期扫描未完成的消息,确保最终一致性.

eg: 如下举例

![worker](https://img-blog.csdnimg.cn/20200417180115954.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3h1OTkwMTI4NjM4,size_16,color_FFFFFF,t_70)

