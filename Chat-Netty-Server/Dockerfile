FROM openjdk:15
EXPOSE 8080
ADD /target/Chat-Netty-Server-1.0-jar-with-dependencies.jar Chat-Netty-Server-1.0-jar-with-dependencies.jar
ENTRYPOINT ["java","-jar","Chat-Netty-Server-1.0-jar-with-dependencies.jar"]

# Сборка банки
# mvn clean compile assembly:single
# Сборка образа
# docker build -t chat-server .
# Узнать ip который использует docker
# ip addr show
# Запустить сервер
# sudo docker run -p <ip-шник докера>:8080:8080 chat-server