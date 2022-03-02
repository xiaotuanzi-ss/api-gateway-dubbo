
**每个后端微服务一般包含xxx-api和xxx-provider两个子项目，xxx-api是微服务对外的接口定义，xxx-provider是微服务接口的实现，包括业务逻辑和持久层写入。**

```
微服务对外的接口定义对应名为xxx-api的子项目
|-- src/main/java

|-- |-- cc.ewell.xxx.xxx  xxx.xxx为[项目].[模块]，例如cc.ewell.emr.moe                              

|-- |-- |-- service |-- dubbo    dubbo暴露外部接口，命名规则为**DubboService，入参统一为MessageHeader类型或PageHeader（分页查询），返回值统一为MessageResult类型

|-- |-- |-- dto    数据传输对象

|-- |-- |-- dto |-- req    前端入参实体对象，命名规则为**Req

|-- |-- |-- dto |-- res    前端返回值实体对象，命名规则为**Res。简单的对象，可以不重复定义，用**Req对象做为返回值实体。

微服务的对外接口实现以及业务逻辑，定义名为xxx--provider的子项目

|-- src/main/java

|-- |-- cc.ewell.xxx.xxx  xxx.xxx为[项目].[模块]，例如cc.ewell.emr.moe

|-- |-- |-- common    微服务内部公用类

|-- |-- |-- mapper    该目录下为daomapper，可以按照功能命名子包，规范为**Mapper.java

|-- |-- |-- mapper |-- domain    实体领域对象

|-- |-- |-- service    该目录下为业务逻辑接口与实现

|-- |-- |-- service |-- business    该目录下为服务内部业务逻辑接口类

|-- |-- |-- service |-- business |-- impl   该目录下为服务内部业务逻辑实现类

|-- |-- |-- service |-- consumer    该目录下为需调用微服务间调用的接口类

|-- |-- |-- service |-- consumer |-- impl   该目录下为需调用微服务间调用的接口的实现类

|-- |-- |-- service |-- dubbo |-- impl   该目录下为对外dubbo接口的实现类

|-- |-- |-- ***Start.java    该目录下为项目启动类,可以按照功能命名 规范为**Start.java

|-- src/main/resources 

|-- db |-- migration    flyway数据库脚本目录，如V1_1_1__add_columns.sql

|-- |-- mapper    该目录下为mybatis sql配置,可不配置，但目录不能为空,可以按照功能命名 规范为**Mapper.xml

|-- |-- application.properties    项目配置文件，只用于切换配置文件

|-- |-- application-apollo.properties    项目配置文件，用于容器环境

|-- |-- application-local.properties    项目配置文件，用于本地开发

|-- |-- generatorConfig.xml    mybatis生成插件配置文件

|-- |-- logback.xml    日志格式文件，如果不配置则启用框架内置配置

|-- |-- message.yml    错误码文件
```