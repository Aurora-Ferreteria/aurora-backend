# Etapa de construcción (Build)
FROM maven:3.9.6-eclipse-temurin-26 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Compila el proyecto y salta los tests para que sea más rápido
RUN mvn clean package -DskipTests

# Etapa de ejecución (Run)
FROM eclipse-temurin:26-jdk
WORKDIR /app
# Copia el archivo .jar compilado de la etapa anterior
COPY --from=build /app/target/*.jar app.jar
# Render expone el puerto a través de una variable de entorno, Spring Boot la lee automáticamente
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]