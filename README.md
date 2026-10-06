# 💧 SmartDrop Backend — IoT Liquid Monitoring & Quality Management

Backend robusto, modular y desacoplado desarrollado en **Java 21 LTS** con **Spring Boot 4.x**, diseñado bajo los principios de **Domain-Driven Design (DDD)**, **Clean Architecture** y el modelo **C4**. Este sistema atiende el monitoreo volumétrico en cisternas residenciales de agua potable y el control estricto de temperatura/calidad en fermentadores de microcervecerías artesanales.

---

## 📋 Tabla de Contenidos
1. [Características y Arquitectura](#-características-y-arquitectura)
2. [Patrones de Diseño Implementados](#-patrones-de-diseño-implementados)
3. [Modo 1: Ejecución Local Inmediata (Cero Fricción con H2)](#-modo-1-ejecución-local-inmediata-cero-fricción-con-h2)
4. [Modo 2: Despliegue de Base de Datos en la Nube (TiDB Cloud / Aiven)](#-modo-2-despliegue-de-base-de-datos-en-la-nube-tidb-cloud--aiven)
5. [Modo 3: Despliegue del Backend en Render (Render.com)](#-modo-3-despliegue-del-backend-en-render-rendercom)
6. [Modo 4: Despliegue Local con Docker Compose](#-modo-4-despliegue-local-con-docker-compose)
7. [Modo 5: Integración con Frontend en Vercel](#-modo-5-integración-con-frontend-en-vercel)
8. [Suite de Pruebas Automatizadas](#-suite-de-pruebas-automatizadas)
9. [Catálogo de Endpoints y Swagger UI](#-catálogo-de-endpoints-y-swagger-ui)

---

## 🏛 Características y Arquitectura

* **Stack Técnico:** Java 21 (LTS), Spring Boot 4.0.6, Spring Data JPA, Spring Security, Hibernate 7, JUnit 5, Mockito, AssertJ.
* **Organización C4 por Bounded Contexts:**
  * `iam`: Autenticación JWT, gestión de identidades, 2FA y roles (`ROLE_USER`, `ROLE_ADMIN`, `ROLE_BREWER`).
  * `inventory`: Gestión de tanques residenciales/industriales, cubicación, consumos y dispositivos IoT.
  * `analytics`: Motor de analítica de calidad, patrones de consumo y reportes.
  * `support`: Alertas críticas, notificaciones multicanal y mesa de tickets.
  * `finance`: Planes de suscripción SaaS, facturas y billetera digital.
  * `profiles`: Perfiles de usuario y preferencias de notificación.
  * `shared`: Shared Kernel transversal, seguridad, CORS, Health Check y Outbox Relay.

---

## 🎯 Patrones de Diseño Implementados

1. **Strategy (GoF) — Evaluación Polimórfica de Alertas (`analytics.domain.model.strategies`):**
   * `NightLeakDetectionStrategy`: Detecta caídas volumétricas anormales en agua residencial (>5 cm/h) en horas nocturnas (01:00 AM - 05:00 AM, US05).
   * `ThermalExcursionStrategy`: Detecta excursiones de temperatura (>20.5°C) que arriesgan la fermentación cervecera (US07).
   * `AlertStrategyResolver`: Resuelve en runtime la estrategia según el tipo de líquido (`WATER` vs `BEER_WORT`).
2. **Proxy / Cache-Aside (GoF) — Optimización de Consultas (`inventory.infrastructure.persistence.proxy`):**
   * `CachedTankReadServiceProxy`: Intercepta consultas a tanques; ante *Cache-Hit* responde desde memoria en <1ms; ante *Cache-Miss*, delega a JPA e invalida proactivamente (`evictCache`).
3. **Factory Method (GoF) — Normalización de Sensores IoT (`inventory.domain.model.factories`):**
   * `SensorNormalizerFactory`: Normaliza payloads crudos de sensores ultrasónicos (HC-SR04 a porcentaje) y térmicos (PT100 a grados Celsius canónicos).
4. **Transactional Outbox (EIP) — Publicación Confiable de Eventos (`shared.infrastructure.outbox`):**
   * `OutboxMessage` y `OutboxPublisherService`: Registra eventos de dominio en estado `PENDING` en la misma transacción ACID de negocio y los despacha de forma asíncrona a `PUBLISHED`.

---

## 🚀 Modo 1: Ejecución Local Inmediata (Cero Fricción con H2)

Permite clonar y arrancar el backend en menos de 3 segundos sin instalar MySQL ni configurar credenciales externas.

### Requisitos Previos
* **Java 21 LTS** instalado (`java -version`).
* **Git**.

### Comandos de Ejecución
```bash
# 1. Clonar el repositorio
git clone https://github.com/upc-pre-202620-1asi0657-sw62-smartdrop/backend-smardrop-funda.git
cd backend-smardrop-funda

# 2. Ejecutar con el Maven Wrapper incluido
# En Windows (PowerShell / CMD):
.\mvnw.cmd spring-boot:run

# En Linux / macOS:
chmod +x ./mvnw
./mvnw spring-boot:run
```

### ¿Qué ocurre al arrancar?
1. Se activa automáticamente el perfil `dev` configurado con **H2 Database en memoria** (modo compatibilidad MySQL).
2. El componente `DataInitializer` puebla de inmediato los siguientes datos de prueba:
   * **3 Usuarios:**
     * `admin@smartdrop.io` (Admin)
     * `demo@smartdrop.io` (Usuario Residencial)
     * `brewer@bohorquez.pe` (Cerveza Bohórquez)
   * **3 Tanques:** Cisterna Principal (1000L), Fermentador Cónico Ale #1 (500L), Tanque Agua Helada (1200L).
   * **2 Sensores:** Ultrasónico HC-SR04 y Sonda Térmica PT100.
   * **2 Alertas Demostrativas:** Fuga Nocturna y Excursión Térmica.
3. **Swagger UI interactivo disponible en:**
   👉 `http://localhost:8080/swagger-ui/index.html`
4. **Consola H2 Web disponible en:**
   👉 `http://localhost:8080/h2-console`
   * **JDBC URL:** `jdbc:h2:mem:drop_db`
   * **User Name:** `sa`
   * **Password:** *(dejar vacío)*

---

## ☁️ Modo 2: Despliegue de Base de Datos en la Nube (TiDB Cloud / Aiven)

Para desplegar el backend en producción necesitas un servidor MySQL accesible por internet. Recomendamos **TiDB Cloud** (Free Tier permanente de 5 GB, 100% compatible con MySQL 8.0 y no se apaga).

### Paso a Paso en TiDB Cloud:
1. Regístrate gratis en [TiDB Cloud](https://tidbcloud.com) con tu cuenta de GitHub o Google.
2. Haz clic en **Create Cluster** y selecciona el plan **Serverless** (gratuito).
3. Selecciona la región (ejemplo: `AWS / us-east-1` o la más cercana).
4. Asigna un nombre al cluster (ej. `smartdrop-db`) y haz clic en **Create**.
5. En la ventana de conexión, copia las credenciales generadas:
   * **Host:** `gateway01.us-east-1.prod.aws.tidbcloud.com` (ejemplo)
   * **Port:** `4000`
   * **User:** `xxxx.root`
   * **Password:** `TuPasswordGenerado`
   * **Database:** `drop_db`
6. En la pestaña **Security Settings**, asegúrate de que el acceso esté permitido desde cualquier IP (`0.0.0.0/0`) para que Render pueda conectarse.

*(Alternativa: También puedes usar [Aiven for MySQL](https://aiven.io) o [Clever Cloud MySQL](https://www.clever-cloud.com) siguiendo un procedimiento idéntico).*

---

## 🌐 Modo 3: Despliegue del Backend en Render (Render.com)

El repositorio incluye la configuración de infraestructura como código [`render.yaml`](./render.yaml) y el [`Dockerfile`](./Dockerfile) multi-stage listo para Render.

### Opción A: Despliegue con 1-Clic vía Blueprint (Recomendado)
1. Regístrate o inicia sesión en [Render](https://render.com) vinculando tu cuenta de GitHub.
2. En el panel principal, haz clic en **New +** y selecciona **Blueprint**.
3. Selecciona el repositorio `backend-smardrop-funda`.
4. Render detectará automáticamente el archivo `render.yaml`.
5. En el formulario, ingresa las variables de base de datos que obtuviste en el Paso 2:
   * `DATABASE_URL`: Host de tu base de datos cloud (ej. `gateway01.us-east-1.prod.aws.tidbcloud.com`)
   * `DATABASE_PORT`: Puerto (ej. `4000` o `3306`)
   * `DATABASE_NAME`: `drop_db`
   * `DATABASE_USER`: Usuario de BD
   * `DATABASE_PASSWORD`: Contraseña de BD
   * `SPRING_PROFILES_ACTIVE`: `mysql`
6. Haz clic en **Apply**. Render compilará el contenedor Docker y publicará tu API.

### Opción B: Despliegue Manual como Web Service
1. En Render, haz clic en **New +** -> **Web Service**.
2. Conecta el repositorio `backend-smardrop-funda`.
3. Configura:
   * **Name:** `smartdrop-backend`
   * **Region:** Oregon (US West) o Ohio (US East)
   * **Runtime:** `Docker`
   * **Instance Type:** `Free`
4. En **Advanced** -> **Health Check Path**, coloca:
   `/api/v1/health`
5. En **Environment Variables**, añade:
   | Variable | Valor |
   | :--- | :--- |
   | `PORT` | `8080` |
   | `SPRING_PROFILES_ACTIVE` | `mysql` |
   | `DATABASE_URL` | *Tu host de TiDB / Aiven* |
   | `DATABASE_PORT` | `4000` *(o `3306`)* |
   | `DATABASE_NAME` | `drop_db` |
   | `DATABASE_USER` | *Tu usuario de BD* |
   | `DATABASE_PASSWORD` | *Tu contraseña de BD* |
   | `JPA_DDL_AUTO` | `update` |
6. Haz clic en **Create Web Service**.
7. Una vez finalizado el build, tu backend estará disponible en:
   `https://smartdrop-backend.onrender.com/swagger-ui/index.html`

---

## 🐳 Modo 4: Despliegue Local con Docker Compose

Si deseas simular el entorno de producción en tu propia máquina corriendo tanto el backend como un contenedor de MySQL 8.0:

```bash
# Iniciar MySQL y el Backend
docker compose up -d --build

# Ver logs del contenedor backend
docker compose logs -f smartdrop-backend

# Detener los contenedores
docker compose down
```

* El contenedor MySQL expone el puerto `3306` localmente.
* El contenedor Backend expone el puerto `8080`.

---

## ⚡ Modo 5: Integración con Frontend en Vercel

Si tu equipo despliega la interfaz web en **Vercel** o **Netlify**:
1. En el proyecto frontend de Vercel, configura la variable de entorno:
   ```env
   VITE_API_BASE_URL=https://smartdrop-backend.onrender.com
   # O en Next.js:
   NEXT_PUBLIC_API_URL=https://smartdrop-backend.onrender.com
   ```
2. **CORS:** El backend en `WebConfig.java` ya tiene configurado:
   ```java
   registry.addMapping("/**")
           .allowedOriginPatterns("https://*.vercel.app", "https://*.netlify.app", "http://localhost:*")
           .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
           .allowedHeaders("*")
           .allowCredentials(true);
   ```
   Cualquier subdominio de Vercel podrá consumir la API sin bloqueos de seguridad.

---

## 🧪 Suite de Pruebas Automatizadas

El backend incluye una suite de pruebas con **JUnit 5**, **AssertJ** y **Mockito**:

```bash
# Ejecutar todas las pruebas unitarias y de integración
./mvnw test
```

### Resultados de la Suite (17 Tests en Verde):
```text
[INFO] Running com.smart.drop.BackendSmartDropApplicationTests ................ [OK] (14.61s)
[INFO] Running SensorNormalizerFactoryTest .................................... [OK] (0.030s)
[INFO] Running CachedTankReadServiceProxyTest ................................. [OK] (0.563s)
[INFO] Running OutboxPublisherServiceTest ..................................... [OK] (0.382s)
[INFO] Running NightLeakDetectionStrategyTest ................................. [OK] (0.015s)
[INFO] Running ThermalExcursionStrategyTest ................................... [OK] (0.012s)
[INFO] Running AlertStrategyResolverTest ...................................... [OK] (0.018s)
[INFO] 
[INFO] Results:
[INFO] Tests run: 17, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## 📡 Catálogo de Endpoints y Swagger UI

Cuando el backend esté activo (local o en Render), accede a la documentación interactiva:
👉 **URL:** `http://localhost:8080/swagger-ui/index.html` (o tu URL en Render).

### Principales Rutas REST por Bounded Context:

| Módulo | Método | Endpoint | Descripción |
| :--- | :---: | :--- | :--- |
| **Health** | `GET` | `/api/v1/health` | Sonda de salud y liveness probe para la nube. |
| **IAM** | `POST` | `/api/v1/iam/auth/register` | Registro de usuarios con contraseña hasheada (BCrypt). |
| **IAM** | `POST` | `/api/v1/iam/auth/login` | Autenticación y generación de token JWT. |
| **IAM** | `GET` | `/api/v1/iam/roles` | Catálogo de roles de seguridad. |
| **Inventory** | `GET` | `/api/v1/inventory/tanks` | Lista tanques (optimizado vía Proxy de Caché). |
| **Inventory** | `POST` | `/api/v1/inventory/tanks` | Crea tanque con validación de capacidad. |
| **Inventory** | `GET` | `/api/v1/inventory/devices` | Lista dispositivos sensores IoT vinculados. |
| **Inventory** | `GET` | `/api/v1/inventory/consumptions` | Registros históricos de consumo volumétrico. |
| **Analytics** | `GET` | `/api/v1/analytics/dashboard` | KPIs consolidados de nivel y calidad. |
| **Analytics** | `GET` | `/api/v1/analytics/reports` | Reportes de estabilidad y auditoría. |
| **Support** | `GET` | `/api/v1/support/alerts` | Consulta de alertas activas y resueltas. |
| **Support** | `POST` | `/api/v1/support/alerts` | Registro de alertas disparadas por el motor. |
| **Finance** | `GET` | `/api/v1/finance/subscriptions/plans` | Planes SaaS (Residencial, Cervecero Pro). |
| **Finance** | `POST` | `/api/v1/finance/subscriptions` | Contratación de suscripciones multi-tanque. |
| **Profiles** | `GET` | `/api/v1/profiles` | Perfiles de usuario. |
| **Profiles** | `PUT` | `/api/v1/profiles/preferences` | Configuración de idioma y alertas. |

---

## 👥 Equipo de Desarrollo (Startup SmartDrop — UPC)

* Pariona Chacca, Angel Jose (U202314734)
* Celis Berrospi, Eslander (U201911249)
* Pillaca Gonzales, Andy Saúl (U202418823)
* Huaco Oliva, Luis Alonso (U202417743)
* Arizabal Condori, Jean Niels (U201919096)

*Curso:* 1ASI0657 — Fundamentos de Arquitectura de Software (Ciclo 2026-20)
