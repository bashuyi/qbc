# QBC框架

## 内容概览

* [框架模块](##框架模块)
* [技术选型](##技术选型)
* [应用分层](##应用分层)

## 框架模块

``` lua
qbc
├── qbc-cloud -- 微服务框架
|    ├──qbc-cloud-auth -- 认证中心
|    ├──qbc-cloud-config -- 配置中心
|    ├──qbc-cloud-gateway -- 网关中心
|    ├──qbc-cloud-registry -- 注册中心
|    ├──qbc-cloud-starter -- 微服务脚手架
├── qbc-starter -- 普通服务脚手架
├── qbc-starter-data -- 数据访问服务脚手架
```

## 技术选型

技术|名称|URL
--|--|--
Spring Framework|容器|[https://spring.io/projects/spring-framework](https://spring.io/projects/spring-framework)
Spring Boot|脚手架|[https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
Spring Data JPA|JPA|[https://spring.io/projects/spring-data-jpa](https://spring.io/projects/spring-data-jpa)
dynamic-datasource-spring-boot-starter|动态多数据源|[https://github.com/baomidou/dynamic-datasource-spring-boot-starter](https://github.com/baomidou/dynamic-datasource-spring-boot-starter)
spring-data-jpa-extra|JPA扩展|[https://github.com/slyak/spring-data-jpa-extra](https://github.com/slyak/spring-data-jpa-extra)
Spring Data Redis|Redis|[https://spring.io/projects/spring-data-redis](https://spring.io/projects/spring-data-redis)
Java JWT|JWT|[https://github.com/auth0/java-jwt](https://github.com/auth0/java-jwt)
Spring Cloud|微服务|[https://spring.io/projects/spring-cloud](https://spring.io/projects/spring-cloud)

## 应用分层

- **开放接口层(API层)**

    - 通过注解的形式，直接封装 Service 方法暴露成 RPC 接口；

    - 通过注解的形式，验证请求参数，统一处理异常，封装返回结果。

- **业务逻辑层(Service层)**

    - 通过调用Manager层或DAO层实现对业务逻辑和流程的控制。

- **通用处理层(Manager层)** 

    - 对第三方平台封装的层，预处理返回结果及转化异常信息；

    - 对 Service 层通用能力的下沉，如缓存方案、中间件通用处理；

    - 与 DAO 层交互，对多个 DAO 的组合复用。

- **数据访问(DAO层)** 

    - 与底层数据库进行交互。


