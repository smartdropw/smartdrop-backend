# ==============================================================================
# SMARTDROP MICROSERVICES - MULTI-MODULE DOCKERFILE
# Permite compilar y empaquetar cualquiera de los microservicios mediante
# el argumento de compilación: SERVICE_NAME
# ==============================================================================
ARG SERVICE_NAME=smartdrop-iam-service

# STAGE 1: Build
FROM eclipse-temurin:21-jdk-jammy AS build
ARG SERVICE_NAME
WORKDIR /app

# Copiar configuración Maven y proyectos
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY smartdrop-shared/ smartdrop-shared/
COPY smartdrop-iam-service/ smartdrop-iam-service/
COPY smartdrop-inventory-service/ smartdrop-inventory-service/
COPY smartdrop-analytics-service/ smartdrop-analytics-service/
COPY smartdrop-support-service/ smartdrop-support-service/
COPY smartdrop-finance-service/ smartdrop-finance-service/

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests -pl ${SERVICE_NAME} -am -B

# STAGE 2: Runtime Production Image
FROM eclipse-temurin:21-jre-jammy AS runtime
ARG SERVICE_NAME
WORKDIR /app

# Crear usuario de sistema sin privilegios de root por seguridad
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Copiar el ejecutable JAR del microservicio seleccionado
COPY --from=build /app/${SERVICE_NAME}/target/*.jar app.jar

ENV PORT=8080
ENV SPRING_PROFILES_ACTIVE=dev
EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
  CMD curl -f http://localhost:${PORT}/api/v1/health || exit 1

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
