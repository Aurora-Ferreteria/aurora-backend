# ==========================================
# Etapa 1: Construcción (Build)
# ==========================================
FROM eclipse-temurin:26-jdk AS build
WORKDIR /app

# Copiamos el wrapper de Maven y el archivo de dependencias
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Le damos permisos de ejecución al wrapper (súper importante al pasar de Windows a Linux)
RUN chmod +x ./mvnw

# Copiamos el código fuente
COPY src src

# Construimos el proyecto ignorando los tests para que sea más rápido
RUN ./mvnw clean package -DskipTests

# ==========================================
# Etapa 2: Ejecución (Run)
# ==========================================
FROM eclipse-temurin:26-jdk
WORKDIR /app

# Copiamos SOLO el archivo .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Copiamos la carpeta local 'newrelic' al contenedor para que el agente exista en la ejecución
# COPY newrelic/ newrelic/

# Exponemos el puerto que usa Spring Boot
EXPOSE 8080

# Un solo ENTRYPOINT optimizado con límite de memoria (300MB) y el agente APM
# ENTRYPOINT ["java", "-Xmx300m", "-javaagent:newrelic/newrelic.jar", "-jar", "app.jar"]
ENTRYPOINT ["java", "-Xmx300m", "-jar", "app.jar"]