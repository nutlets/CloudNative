# 基于java镜像创建新镜像

FROM openjdk:8-jdk-alpine

# 作者

MAINTAINER Giesen

# 将jar包添加到容器中并更名为app.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone

ADD  target/demo-0.0.1-SNAPSHOT.jar /app.jar

# 运行jar包

ENTRYPOINT ["nohup","java","-jar","./app.jar","&"]

EXPOSE 8080
# docker build -t demo .
# docker run -d -p 8080:8080 --name my_demo_03 sbdemo:latest
