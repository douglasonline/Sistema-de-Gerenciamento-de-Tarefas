# Fase de build para o Eureka Server
FROM eclipse-temurin:17-jdk-alpine AS eureka-build

# Definir diretório de trabalho para o Eureka Server
WORKDIR /app/eureka

# Copiar o JAR da aplicação Eureka Server para o contêiner
COPY eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar /app/eureka/eureka-server-0.0.1-SNAPSHOT.jar

# Fase de build para o Config Server
FROM eclipse-temurin:17-jdk-alpine AS config-build

# Definir diretório de trabalho para o Config Server
WORKDIR /app/config

# Copiar o JAR da aplicação Config Server para o contêiner
COPY config-server/target/config-server-0.0.1-SNAPSHOT.jar /app/config/config-server-0.0.1-SNAPSHOT.jar

# Fase de build para o API Gateway
FROM eclipse-temurin:17-jdk-alpine AS gateway-build

# Definir diretório de trabalho para o API Gateway
WORKDIR /app/gateway

# Copiar o JAR da aplicação API Gateway para o contêiner
COPY gateway/target/gateway-0.0.1-SNAPSHOT.jar /app/gateway/gateway-0.0.1-SNAPSHOT.jar

# Fase de build para o UserService
FROM eclipse-temurin:17-jdk-alpine AS user-service-build

# Definir diretório de trabalho para o UserService
WORKDIR /app/user

# Copiar o JAR da aplicação UserService para o contêiner
COPY UserService/target/UserService-0.0.1-SNAPSHOT.jar /app/user/UserService-0.0.1-SNAPSHOT.jar

# Fase de build para o ProjectService
FROM eclipse-temurin:17-jdk-alpine AS project-service-build

# Definir diretório de trabalho para o ProjectService
WORKDIR /app/project

# Copiar o JAR da aplicação ProjectService para o contêiner
COPY ProjectService/target/ProjectService-0.0.1-SNAPSHOT.jar /app/project/ProjectService-0.0.1-SNAPSHOT.jar

# Fase de build para o TaskService
FROM eclipse-temurin:17-jdk-alpine AS task-service-build

# Definir diretório de trabalho para o TaskService
WORKDIR /app/task

# Copiar o JAR da aplicação TaskService para o contêiner
COPY TaskService/target/TaskService-0.0.1-SNAPSHOT.jar /app/task/TaskService-0.0.1-SNAPSHOT.jar

# Fase de build para o Notification Service
FROM eclipse-temurin:17-jdk-alpine AS notification-service-build

# Definir diretório de trabalho para o Notification Service
WORKDIR /app/notification

# Copiar o JAR da aplicação Notification Service para o contêiner
COPY Notification/target/Notification-0.0.1-SNAPSHOT.jar /app/notification/Notification-0.0.1-SNAPSHOT.jar

# Imagem final: combinar os servidores Eureka, Config, API Gateway, UserService, ProjectService, TaskService e NotificationService
FROM eclipse-temurin:17-jdk-alpine

# Definir diretórios de trabalho para cada serviço no contêiner final
WORKDIR /app

# Copiar os serviços da fase de build
COPY --from=eureka-build /app/eureka/eureka-server-0.0.1-SNAPSHOT.jar /app/eureka-server-0.0.1-SNAPSHOT.jar
COPY --from=config-build /app/config/config-server-0.0.1-SNAPSHOT.jar /app/config-server-0.0.1-SNAPSHOT.jar
COPY --from=gateway-build /app/gateway/gateway-0.0.1-SNAPSHOT.jar /app/gateway-0.0.1-SNAPSHOT.jar
COPY --from=user-service-build /app/user/UserService-0.0.1-SNAPSHOT.jar /app/UserService-0.0.1-SNAPSHOT.jar
COPY --from=project-service-build /app/project/ProjectService-0.0.1-SNAPSHOT.jar /app/ProjectService-0.0.1-SNAPSHOT.jar
COPY --from=task-service-build /app/task/TaskService-0.0.1-SNAPSHOT.jar /app/TaskService-0.0.1-SNAPSHOT.jar
COPY --from=notification-service-build /app/notification/Notification-0.0.1-SNAPSHOT.jar /app/Notification-0.0.1-SNAPSHOT.jar

# Expor as portas dos sete serviços. Apenas o Eureka Server e API Gateway têm portas fixas.
EXPOSE 8761 9999

# Definir variáveis de ambiente para o Config Server, API Gateway, UserService, ProjectService, TaskService e NotificationService
ENV SPRING_PROFILES_ACTIVE=default

# Comando para iniciar os sete serviços
CMD ["sh", "-c", "java -jar /app/eureka-server-0.0.1-SNAPSHOT.jar & \
                  java -Dserver.port=${PORT:0} -jar /app/config-server-0.0.1-SNAPSHOT.jar & \
                  java -jar /app/gateway-0.0.1-SNAPSHOT.jar & \
                  java -Dserver.port=${PORT:0} -jar /app/UserService-0.0.1-SNAPSHOT.jar & \
                  java -Dserver.port=${PORT:0} -jar /app/ProjectService-0.0.1-SNAPSHOT.jar & \
                  java -Dserver.port=${PORT:0} -jar /app/TaskService-0.0.1-SNAPSHOT.jar & \
                  java -Dserver.port=${PORT:0} -jar /app/Notification-0.0.1-SNAPSHOT.jar"]
