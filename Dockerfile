# Primera etapa: imagen Base (Construcción del jar)
FROM maven:3.9.9-openjdk-17 AS builder

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivo pom.xml para descargar las deoendencias
COPY pom.xml /
RUN mvn dependency:go-offline # Comando para descargar las dependecnias del proyecto

# Pasar el codigo completo del proyecto
COPY src ./src
RUN mvn clean package -DskipTests # Compila el codigo y crea el archivo .jar

RUN ls -la /app/target # Verifica que el archivo .jar se haya generado correctamente

# Segunda etapa: Construcción imagen final (es una distribución de linux para poder ejecutar la app)
FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/birdmanagementapi-0.0.1-SNAPSHOT.jar /app/birdmanagementapi.jar

# Puerto que escuchara la app
EXPOSE 9093

# Ejecuta el jar, por ende toda la aplicación
ENTRYPOINT ["java", "-jar", "/app/birdmanagementapi.jar"]
