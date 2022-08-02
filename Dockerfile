# 基于java镜像创建新镜像

FROM java:8

# 作者

MAINTAINER Giesen

WORKDIR /app

# 将jar包添加到容器中并更名为cloud-native-demo.jar

ADD  target/demo-0.0.1-SNAPSHOT.jar ./cloud-native-demo.jar

# 运行jar包

ENTRYPOINT ["java","-jar","./cloud-native-demo.jar"]

EXPOSE 8080

# docker build -t demo .
# docker run -d -p 8080:8080 --name my_demo_03 demo:latest
