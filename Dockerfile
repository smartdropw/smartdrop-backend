# ==============================================================================
# SMARTDROP BACKEND - MULTI-STAGE DOCKERFILE FOR RENDER / CLOUD
# Base Image: Eclipse Temurin OpenJDK 21 (LTS)
# ==============================================================================

# STAGE 1: Build & Package
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copiar archivos de Maven para aprovechar la caché de dependencias
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente y compilar artefacto omitiendo pruebas unitarias en build final
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# STAGE 2: Runtime Production Image
FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

# Crear usuario de sistema sin privilegios de root por seguridad
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Copiar el archivo ejecutable JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Render asigna dinámicamente la variable de entorno $PORT (por defecto 8080)
ENV PORT=8080
ENV SPRING_PROFILES_ACTIVE=dev
EXPOSE 8080

# Healthcheck para orquestadores
HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
  CMD curl -f http://localhost:${PORT}/api/v1/health || exit 1

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
