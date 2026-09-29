# SCENTRA 

Sistema de escritorio para la gestión de inventarios de empresas que fabrican velas y productos aromáticos, desarrollado en **Java Swing** con **MySQL** y aplicando **Programación Orientada a Objetos**. Permite controlar materia prima, material de empaque y productos terminados, registrar entradas y salidas, y exportar el historial a Excel. Varias empresas pueden usar el sistema al mismo tiempo, cada una con sus propios datos y empleados. Proyecto presentado en **EXPOTEC 2025**.

> *Desktop inventory management system for candle and aromatic product businesses, built with Java Swing and MySQL using object-oriented programming. It tracks raw materials, packaging and finished products, logs stock movements and exports the history to Excel. Multiple companies can use it, each with its own data and staff. Presented at EXPOTEC 2025.*

## Funcionalidades

**Usuarios y empresas**
- Registro de usuarios con validación de contraseña (mínimo 6 caracteres, con números y mayúsculas).
- Un administrador puede **crear su empresa**, que recibe un código único; los empleados se **unen a ella** ingresando ese código.
- Inicio de sesión con dos roles: **Administrador** y **Empleado**, con permisos distintos.
- Cada empresa ve únicamente su propio inventario, movimientos y personal.

**Inventario**
- Tres categorías: **materia prima**, **material de empaque** y **productos terminados**.
- Crear, consultar, editar y eliminar artículos de cada categoría.
- Vista unificada de todo el inventario de la empresa.
- Registro de cantidades mínimas, precios unitarios, costos de producción y precios de venta.

**Movimientos e historial**
- Registro de entradas y salidas de inventario, con costo unitario, costo total y comentarios.
- Historial de movimientos con el usuario que realizó cada uno.
- **Exportación del historial a Excel (.xlsx)** con Apache POI.

**Administración**
- Dashboard con el resumen de la empresa.
- Gestión de empleados: consulta, cambio de rol y estado dentro de la empresa.
- Configuración de la información de la empresa (nombre, tipo, color, iniciales y logo) y de los datos personales.

## Tecnologías

- **Java 21** y **Java Swing**
- **MySQL**, mediante **JDBC** (MySQL Connector/J 5.1.49)
- **Apache POI 5.2.3** para la exportación a Excel
- **NetBeans** (proyecto Ant)
- Programación Orientada a Objetos, separando la interfaz (`Vista`) del acceso a datos (`ManejoBase`)

## Estructura del proyecto

```
SCENTRA/
├── src/
│   ├── Vista/          # Ventanas y paneles de la interfaz (Swing)
│   ├── ManejoBase/     # Conexión y operaciones con la base de datos
│   └── Imagenes/       # Íconos e imágenes de la interfaz
├── database/
│   └── scentra.sql     # Creación de tablas y datos de prueba
├── nbproject/          # Configuración del proyecto de NetBeans
└── build.xml
```

## Requisitos

- JDK 21
- Apache NetBeans
- MySQL Server (y opcionalmente MySQL Workbench)
- [Apache POI 5.2.3](https://archive.apache.org/dist/poi/release/bin/) (distribución binaria `poi-bin-5.2.3`)
- [MySQL Connector/J 5.1.49](https://downloads.mysql.com/archives/c-j/)

## Instalación y ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Grodriguezdl/SCENTRA.git
   ```
2. Coloca las librerías en la **carpeta que contiene el repositorio** (no dentro de él), porque el proyecto las busca en `../`:
   ```
   carpeta-contenedora/
   ├── SCENTRA/                          # el repositorio
   ├── poi-bin-5.2.3/                    # Apache POI descomprimido
   └── mysql-connector-java-5.1.49.jar
   ```
3. Crea la base de datos ejecutando el script, desde MySQL Workbench o por consola:
   ```bash
   mysql -u root -p < database/scentra.sql
   ```
4. En `src/ManejoBase/Conexion.java`, ajusta el usuario y la contraseña de tu servidor MySQL.
5. Abre el proyecto en NetBeans y ejecútalo con **F6**.

### Usuarios de prueba

El script incluye 20 empresas de ejemplo con sus empleados. Para probar con la empresa *Velas Aromáticas Luna*:

| Rol | Usuario | Contraseña |
|---|---|---|
| Administrador | `Gabriel Rodriguez` | `admin001` |
| Empleado | `Roberto Fuentes` | `emp021` |

## Lo que aprendí

- Diseñar una base de datos **multiempresa**, donde cada registro pertenece a una empresa y los datos quedan aislados entre ellas.
- Modelar movimientos de inventario que pueden referirse a tres tipos de artículo distintos.
- Separar la interfaz de la lógica de acceso a datos para mantener el código organizado.
- Generar archivos de Excel desde Java con Apache POI.
- Controlar permisos y vistas según el rol del usuario.

## Autores

- **Gabriel Rodríguez** · [GitHub](https://github.com/Grodriguezdl)
