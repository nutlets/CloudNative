# 基于java镜像创建新镜像

FROM java:8

# 作者

MAINTAINER Giesen

# 将jar包添加到容器中并更名为app.jar

ADD  target/demo-0.0.1-SNAPSHOT.jar ./app/app.jar

# 运行jar包

ENTRYPOINT ["nohup","java","-jar","./app/app.jar","&"]

# docker build -t demo .
# docker run -d -p 8080:8080 --name my_demo_03 sbdemo:latest
