FROM openjdk:15
EXPOSE 8080
ADD /target/Chat-Netty-Client-1.0-jar-with-dependencies.jar Chat-Netty-Client-1.0-jar-with-dependencies.jar
ENTRYPOINT ["java","-jar","Chat-Netty-Client-1.0-jar-with-dependencies.jar"]

# Сборка банки
# mvn clean compile assembly:single
# Сборка образа
# sudo docker build -t chat-client .
# Узнать ip который использует docker
# ip addr show
# Запустить клиент
# sudo docker run -ti chat-client <ip-шник докера> 8080