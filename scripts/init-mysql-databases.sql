-- ==============================================================================
-- SMARTDROP PLATFORM - CREACIÓN DE BASES DE DATOS EN MYSQL WORKBENCH
-- ==============================================================================
-- Instrucciones en MySQL Workbench:
-- 1. Abre tu conexión local en MySQL Workbench (Host: localhost, Port: 3306, User: root).
-- 2. Abre una pestaña de consulta SQL: File > New Query Tab (o presiona Ctrl + T).
-- 3. Pega todo el contenido de este archivo.
-- 4. Presiona el botón del RAYO (⚡) o Ctrl + Shift + Enter para ejecutar todo.
-- 5. En el panel izquierdo 'SCHEMAS', haz clic en el botón de refrescar (flechas circulares).
-- ==============================================================================

-- 1. Esquema para Microservicio IAM & Profiles (Puerto 8081)
CREATE DATABASE IF NOT EXISTS drop_iam_db 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- 2. Esquema para Microservicio Inventory & Administration (Puerto 8082)
CREATE DATABASE IF NOT EXISTS drop_inventory_db 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- 3. Esquema para Microservicio Analytics (Puerto 8083)
CREATE DATABASE IF NOT EXISTS drop_analytics_db 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- 4. Esquema para Microservicio Support (Puerto 8084)
CREATE DATABASE IF NOT EXISTS drop_support_db 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- 5. Esquema para Microservicio Finance & Planning (Puerto 8085)
CREATE DATABASE IF NOT EXISTS drop_finance_db 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- 6. Esquema unificado para la versión monolítica (Rama main - Puerto 8080)
CREATE DATABASE IF NOT EXISTS drop_db 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- ==============================================================================
-- Verificación: Listar los esquemas creados
-- ==============================================================================
SHOW DATABASES LIKE 'drop_%';
