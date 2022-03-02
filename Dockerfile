#基础镜像  docker build -t job/e_job:v1 .
FROM chart.devops.com/devops/openjdk:8u201-jdk-alpine
#镜像制作者
MAINTAINER xx xx@ewell.cc
#同步Docker的时区
RUN echo "Asia/Shanghai" > /etc/timezone;
#添加程序的Spring Boot Jar包
ADD project-example-nacos-provider/target/project-example-nacos-provider-0.0.1-SNAPSHOT.jar /dubbo.jar
#声明需要暴露的接口
EXPOSE 20880
#配置容器启动后执行的命令
CMD ["java","-jar","-XX:InitialRAMPercentage=50.0","-XX:MinRAMPercentage=50.0","-XX:MaxRAMPercentage=90.0","-XshowSettings:vm","/dubbo.jar"]