# QBC框架

## 内容概览

* [技术选型](#技术选型)
* [系统架构](#系统架构)
* [框架模块](#框架模块)
    * [注册中心](#注册中心)
    * [配置中心](#配置中心)
    * [认证中心](#认证中心)
    * [网关中心](#网关中心)
* [应用分层](#应用分层)

## <a name="技术选型"></a>技术选型

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
p3c|阿里巴巴Java开发手册和Eclipse插件|[https://github.com/alibaba/p3c](https://github.com/alibaba/p3c)

## <a name="系统架构"></a>系统架构

![系统架构](doc/qbc-cloud.png)

## <a name="框架模块"></a>框架模块

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

### <a name="注册中心"></a>注册中心

通过 Spring Cloud Netflix Eureka 实现注册中心。提供服务的注册于发现功能。生产环境中采用3个对等节点进行两两注册以实现高可用。

### <a name="配置中心"></a>配置中心

通过 Spring Cloud Config 实现配置中心。配置中心注册到注册中心。在生产环境中服务从配置中心读取文件，而配置中心从Git读取配置文件，将配置中心做成一个集群化微服务即可实现高可用，满足大量服务的需求。

### <a name="认证中心"></a>认证中心

通过 JWT 实现认证中心。提供 Token 的发行、验证和失效功能。在验证 Token 的时候，也对权限进行验证。

### <a name="网关中心"></a>网关中心

通过 Spring Cloud Gateway 实现网关中心。提供外部的统一访问入口。收到外部请求后，如果是 Token 的发行或失效，会直接分发到认证中心。如果是对其他服务的请求，会先到认证中心进行 Token 和权限的验证，如果通过验证，才会分发到对应的服务。

## <a name="应用分层"></a>应用分层

- **开放接口层(API层)**

    - 通过注解的形式，直接封装 Service 方法暴露成 RPC 接口；

    - 通过注解的形式，验证请求参数，统一处理异常，封装返回结果。

- **业务逻辑层(Service层)**

    - 通过调用Manager层或DAO层实现对业务逻辑和流程的控制。

- **通用处理层(Manager层)** 

    - 对第三方平台封装的层，预处理返回结果及转化异常信息；

    - 对 Service 层通用能力的下沉，如缓存方案、中间件通用处理；

    - 与 DAO 层交互，对多个 DAO 的组合复用。

- **数据访问层(DAO层)** 

    - 与底层数据库进行交互。


