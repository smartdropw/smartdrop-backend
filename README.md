#  SmartDrop Backend — Arquitectura de Microservicios Modular (Multi-Module)

Backend empresarial modular y desacoplado desarrollado en **Java 21 LTS** con **Spring Boot 4.x**, diseñado bajo los principios de **Domain-Driven Design (DDD)**, **Arquitectura de Microservicios**, **Clean Architecture** y el modelo **C4**. Atiende el monitoreo volumétrico en cisternas residenciales de agua potable y el control estricto de temperatura/calidad en fermentadores de microcervecerías artesanales.

> [!NOTE]
> Este proyecto está estructurado como un **Maven Multi-Module Monorepo** con aislamiento de datos (*Database-per-Service*). Cada microservicio puede ejecutarse de manera completamente autónoma en puertos independientes o de forma concurrente en múltiples *Run Configurations* de **Eclipse IDE** e **IntelliJ IDEA**.

---

## Tabla de Contenidos
1. [Estructura Multi-Módulo de Microservicios](#-estructura-multi-módulo-de-microservicios)
2. [Matriz de Puertos y Bases de Datos Autónomas](#-matriz-de-puertos-y-bases-de-datos-autónomas)
3. [Patrones de Diseño Implementados](#-patrones-de-diseño-implementados)
4. [Ejecución en Eclipse IDE (Múltiples Runs Concurrentes)](#-ejecución-en-eclipse-ide-múltiples-runs-concurrentes)
5. [Ejecución en IntelliJ IDEA (Run Dashboard)](#-ejecución-en-intellij-idea-run-dashboard)
6. [Ejecución por Consola con Maven Wrapper](#-ejecución-por-consola-con-maven-wrapper)
7. [Despliegue Multi-Contenedor con Docker Compose](#-despliegue-multi-contenedor-con-docker-compose)
8. [Suite de Pruebas Automatizadas](#-suite-de-pruebas-automatizadas)

---

##  Estructura Multi-Módulo de Microservicios

```text
backend-smardrop-funda/
├── pom.xml                               # POM Padre (Packaging POM, reactor aggregator)
├── mvnw / mvnw.cmd                       # Maven Wrapper multi-plataforma
├── docker-compose.yml                    # Orquestación de los 5 microservicios
├── Dockerfile                            # Multi-stage Dockerfile con build-arg SERVICE_NAME
├── eclipse-launchers/                    # Lanzadores listos para Eclipse IDE (.launch)
│   ├── SmartDrop-IAM-Service.launch
│   ├── SmartDrop-Inventory-Service.launch
│   ├── SmartDrop-Analytics-Service.launch
│   ├── SmartDrop-Support-Service.launch
│   └── SmartDrop-Finance-Service.launch
├── .run/                                 # Run Configurations para IntelliJ IDEA
│   ├── IamServiceApplication.run.xml
│   ├── InventoryServiceApplication.run.xml
│   ├── AnalyticsServiceApplication.run.xml
│   ├── SupportServiceApplication.run.xml
│   └── FinanceServiceApplication.run.xml
│
├── smartdrop-shared/                     # [LIBRERÍA JAR COMÚN]
│   └── Kernel compartido: Auditoría JPA, Outbox Pattern, CORS, OpenAPI, Exception Handler
│
├── smartdrop-iam-service/                # [MICROSERVICIO 1 - Puerto 8081]
│   └── Bounded Contexts: IAM (Auth, JWT, Roles) + Profiles (Perfiles y Preferencias)
│
├── smartdrop-inventory-service/          # [MICROSERVICIO 2 - Puerto 8082]
│   └── Bounded Contexts: Inventory (Tanques, Sensores, Consumo) + Administration
│
├── smartdrop-analytics-service/          # [MICROSERVICIO 3 - Puerto 8083]
│   └── Bounded Context: Analytics (Detección de Fugas, Excursión Térmica, Reportes)
│
├── smartdrop-support-service/            # [MICROSERVICIO 4 - Puerto 8084]
│   └── Bounded Context: Support (Alertas de Emergencia, Notificaciones, Tickets)
│
└── smartdrop-finance-service/            # [MICROSERVICIO 5 - Puerto 8085]
    └── Bounded Contexts: Finance (Pagos, Billetera, Facturas) + Planning (Rutas)
```

---

## Matriz de Puertos y Bases de Datos Autónomas

Cada microservicio cuenta con su propia base de datos H2 en memoria aislada (*Database-per-Service*) y su propia documentación Swagger UI interactiva:

| Microservicio | Puerto | Base de Datos H2 Local | Swagger UI URL |
| :--- | :---: | :--- | :--- |
| **`smartdrop-iam-service`** | `8081` | `jdbc:h2:mem:drop_iam_db` | `http://localhost:8081/swagger-ui/index.html` |
| **`smartdrop-inventory-service`** | `8082` | `jdbc:h2:mem:drop_inventory_db` | `http://localhost:8082/swagger-ui/index.html` |
| **`smartdrop-analytics-service`** | `8083` | `jdbc:h2:mem:drop_analytics_db` | `http://localhost:8083/swagger-ui/index.html` |
| **`smartdrop-support-service`** | `8084` | `jdbc:h2:mem:drop_support_db` | `http://localhost:8084/swagger-ui/index.html` |
| **`smartdrop-finance-service`** | `8085` | `jdbc:h2:mem:drop_finance_db` | `http://localhost:8085/swagger-ui/index.html` |

* Consolas H2 Web disponibles en `/h2-console` en el puerto de cada servicio (Usuario: `sa`, Contraseña: vacía).

---

## Conexion y Configuracion con MySQL Workbench

Si deseas persistencia real en lugar de H2 en memoria, tu base de datos local de **MySQL 8.0** está completamente integrada:

### 1. Parámetros de Conexión en MySQL Workbench
Al abrir MySQL Workbench y crear/editar una conexión (botón `+` en *MySQL Connections*), configura los campos exactamente así:

| Campo en MySQL Workbench | Valor a Colocar | Explicación |
| :--- | :--- | :--- |
| **Connection Name** | `SmartDrop Local` | Nombre visual descriptivo para identificar tu conexión. |
| **Connection Method** | `Standard (TCP/IP)` | Protocolo estándar de conexión. |
| **Hostname** | `localhost` *(o `127.0.0.1`)* | Indica que el servidor MySQL corre en tu misma máquina. |
| **Port** | `3306` | Puerto por defecto del servicio `MySQL80` en Windows. |
| **Username** | `root` | Usuario administrador principal de MySQL. |
| **Password** | Clic en **Store in Vault...** e ingresar `admin` | Contraseña configurada en tu servicio local de MySQL. |

> Haz clic en **Test Connection** para comprobar que la conexión sea exitosa.

### 2. Creación de Esquemas en MySQL Workbench
1. Entra a tu conexión en MySQL Workbench.
2. Abre una pestaña de consulta SQL (`File > New Query Tab` o presiona `Ctrl + T`).
3. Abre o copia el archivo [`scripts/init-mysql-databases.sql`](file:///C:/Users/USUARIO/IdeaProjects/backend-smardrop-funda/scripts/init-mysql-databases.sql).
4. Ejecuta el script completo con el botón del **RAYO ()** o presionando `Ctrl + Shift + Enter`.
5. En el panel izquierdo de **SCHEMAS**, haz clic en el botón de actualizar (icono de flechas circulares). Verás creados los 5 esquemas:
   - `drop_iam_db`
   - `drop_inventory_db`
   - `drop_analytics_db`
   - `drop_support_db`
   - `drop_finance_db`

### 3. ¿Cómo se crean las tablas? (Autogeneración con Hibernate)
**No tienes que escribir sentencias `CREATE TABLE` a mano en Workbench.**
Cada microservicio cuenta con su archivo `application-mysql.properties` configurado con `spring.jpa.hibernate.ddl-auto=update`. Al arrancar el microservicio con el perfil `mysql`, Spring Boot se conecta a su respectivo esquema y **crea automáticamente todas las tablas, columnas, índices y claves foráneas**, insertando también los datos iniciales de prueba.

### 4. Cómo arrancar en modo MySQL
* **En Eclipse:** Clic derecho sobre los lanzadores `SmartDrop-*-MySQL.launch` en la carpeta `eclipse-launchers/` > **Run As**.
* **En Terminal (Maven Wrapper):**
  ```bash
  # Arrancar IAM Service apuntando a MySQL:
  ./mvnw spring-boot:run -pl smartdrop-iam-service -Dspring-boot.run.profiles=mysql
  ```

---

## Patrones de Diseno Implementados

1. **Strategy (GoF) — Evaluación Polimórfica de Alertas (`smartdrop-analytics-service`):**
   * `NightLeakDetectionStrategy`: Detecta caídas volumétricas anormales en agua residencial (>5 cm/h) en horario nocturno (01:00 AM - 05:00 AM, US05).
   * `ThermalExcursionStrategy`: Detecta excursiones de temperatura (>20.5°C) que ponen en riesgo la fermentación cervecera (US07).
   * `AlertStrategyResolver`: Resuelve dinámicamente la regla según el tipo de líquido (`WATER` vs `BEER_WORT`).
2. **Proxy / Cache-Aside (GoF) — Optimización de Consultas (`smartdrop-inventory-service`):**
   * `CachedTankReadServiceProxy`: Intercepta lecturas volumétricas a tanques; ante *Cache-Hit* responde en <1ms desde memoria; ante *Cache-Miss*, delega a JPA e invalida proactivamente (`evictCache`).
3. **Factory Method (GoF) — Normalización de Sensores IoT (`smartdrop-inventory-service`):**
   * `SensorNormalizerFactory`: Normaliza lecturas crudas heterogéneas de sensores ultrasónicos (HC-SR04 a porcentaje) y térmicos (PT100 a grados Celsius canónicos).
4. **Transactional Outbox (EIP) — Publicación Confiable de Eventos (`smartdrop-shared`):**
   * `OutboxMessage` y `OutboxPublisherService`: Registra eventos de dominio en estado `PENDING` en la misma transacción ACID de negocio y los despacha asíncronamente a `PUBLISHED`.

---

## Ejecucion en Eclipse IDE (Múltiples Runs Concurrentes)

Eclipse permite importar el monorepo y arrancar todos los microservicios simultáneamente en distintas consolas:

### Paso 1: Importar en Eclipse
1. Abre **Eclipse IDE** (con plugin *Spring Tools Suite / STS* o *Eclipse IDE for Enterprise Java*).
2. Ve al menú: **File > Import...**
3. Selecciona **Maven > Existing Maven Projects** y haz clic en **Next**.
4. En **Root Directory**, selecciona la carpeta del repositorio: `C:\Users\USUARIO\IdeaProjects\backend-smardrop-funda`.
5. Eclipse detectará automáticamente los 7 proyectos:
   * `backend-smardrop-funda` (Parent)
   * `smartdrop-shared`
   * `smartdrop-iam-service`
   * `smartdrop-inventory-service`
   * `smartdrop-analytics-service`
   * `smartdrop-support-service`
   * `smartdrop-finance-service`
6. Haz clic en **Finish**.

### Paso 2: Ejecución Concurrente de los Microservicios
Para cada uno de los microservicios (`smartdrop-iam-service`, `smartdrop-inventory-service`, etc.):
* **Opción A:** Haz clic derecho sobre el proyecto en el explorador > **Run As > Spring Boot App** (o **Run As > Java Application** seleccionando la clase Application correspondiente).
* **Opción B:** Usa los lanzadores pre-configurados en la carpeta `eclipse-launchers/`:
  1. Haz clic derecho sobre `SmartDrop-IAM-Service.launch` > **Run As > SmartDrop-IAM-Service**.
  2. Haz clic derecho sobre `SmartDrop-Inventory-Service.launch` > **Run As > SmartDrop-Inventory-Service**.
  3. Repite para los demás servicios.

Cada servicio se abrirá en su propia pestaña de la vista **Console** de Eclipse, corriendo en paralelo en los puertos **8081, 8082, 8083, 8084 y 8085** sin conflicto.

---

##  Ejecución en IntelliJ IDEA (Run Dashboard)

1. Abre la carpeta `backend-smardrop-funda` en IntelliJ IDEA.
2. IntelliJ detectará automáticamente el archivo `pom.xml` raíz y los 6 submódulos Maven.
3. En la barra superior derecha o en la pestaña **Services / Run Dashboard** (Alt+8), aparecerán directamente las 5 configuraciones pre-armadas en `.run/`:
   * `IamServiceApplication (8081)`
   * `InventoryServiceApplication (8082)`
   * `AnalyticsServiceApplication (8083)`
   * `SupportServiceApplication (8084)`
   * `FinanceServiceApplication (8085)`
4. Puedes hacer clic en **Run All** o iniciar cada servicio individualmente con un solo clic.

---

##  Ejecución por Consola con Maven Wrapper

### Compilar todos los módulos a la vez:
```bash
./mvnw clean compile
```

### Ejecutar las pruebas unitarias y de integración de todo el sistema:
```bash
./mvnw test
```

### Ejecutar un microservicio específico desde la terminal:
```bash
# Terminal 1: IAM Service (Puerto 8081)
./mvnw spring-boot:run -pl smartdrop-iam-service

# Terminal 2: Inventory Service (Puerto 8082)
./mvnw spring-boot:run -pl smartdrop-inventory-service

# Terminal 3: Analytics Service (Puerto 8083)
./mvnw spring-boot:run -pl smartdrop-analytics-service

# Terminal 4: Support Service (Puerto 8084)
./mvnw spring-boot:run -pl smartdrop-support-service

# Terminal 5: Finance Service (Puerto 8085)
./mvnw spring-boot:run -pl smartdrop-finance-service
```

---

##  Despliegue Multi-Contenedor con Docker Compose

Para levantar toda la arquitectura de microservicios con un solo comando Docker:

```bash
# Construir las imágenes y levantar los 5 contenedores
docker compose up --build -d

# Ver el estado de todos los contenedores
docker compose ps

# Ver logs unificados
docker compose logs -f
```

---

## Suite de Pruebas Automatizadas

Todos los microservicios cuentan con pruebas automatizadas integradas en el pipeline Maven Reactor:

* **Pruebas de Patrones GoF y EIP:**
  * `AlertStrategyResolverTest` (Patrón Strategy en Analytics)
  * `NightLeakDetectionStrategyTest` (Fuga nocturna en Analytics)
  * `ThermalExcursionStrategyTest` (Excursión térmica en Analytics)
  * `CachedTankReadServiceProxyTest` (Patrón Proxy Cache-Aside en Inventory)
  * `SensorNormalizerFactoryTest` (Patrón Factory Method en Inventory)
  * `OutboxPublisherServiceTest` (Patrón Transactional Outbox en Shared)
* **Pruebas de Carga de Contexto Spring Boot por Microservicio:**
  * `IamServiceApplicationTests`
  * `InventoryServiceApplicationTests`
  * `AnalyticsServiceApplicationTests`
  * `SupportServiceApplicationTests`
  * `FinanceServiceApplicationTests`

Para verificar la suite completa:
```bash
./mvnw test
```
Resultado: **BUILD SUCCESS** con 0 fallos.
