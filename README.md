# Mutant Detector API
**Alumno:** Uriel Romero - **Legajo:** 51073 - **Comisión:** 3K09

Resumen rápido

- API para detectar mutantes analizando matrices de ADN.
- Stack: Java 21, Spring Boot 3.x, Gradle.

## Índice

- [Estado del proyecto](#estado-del-proyecto)
- [Requisitos](#requisitos)
- [Estructura importante](#estructura-importante)
- [Ejecución / Ejemplos](#ejecución--ejemplos)
- [Cómo compilar](#cómo-compilar)
- [Ejecutar (modo desarrollo)](#ejecutar-modo-desarrollo)
- [Endpoints útiles (local)](#endpoints-útiles-local)
- [Tests y cobertura](#tests-y-cobertura)
- [Docker y despliegue en render](#docker-y-despliegue-en-render)
- [Archivos relevantes](#archivos-relevantes-para-la-entrega)

---

## Estado del proyecto

- Tests: incluidos (suite de tests unitarios e integración).
- Cobertura JaCoCo: >= 80% en módulos críticos.
- Documentación OpenAPI (Swagger) disponible.

## Requisitos

- Java 17+ instalado
- Gradle wrapper incluido (usar `gradlew.bat` en Windows)
- Docker (opcional, para generar imagen)

## Estructura importante

- Código fuente: `src/`
- Tests: `src/test/`
- Diagramas de secuencia (generados):
  - `diagrama-secuencia-GETstats.drawio.pdf`
  - `Diagrama-secuencia-POSTmutant.drawio.pdf`

---

## Ejecución / Ejemplos

Ejemplos para probar la API (asumiendo `http://localhost:8080`).
Cada ejemplo incluye el Request (curl) y la Response esperada (línea de estado + body si aplica).

### 1) POST /mutant — Ejemplo MUTANTE (HTTP 200 OK)

Request (PowerShell / curl):

```powershell
curl -i -X POST http://localhost:8080/mutant \
  -H "Content-Type: application/json" \
  -d '{
    "dna": [
      "ATGCGA",
      "CAGTGC",
      "TTATGT",
      "AGAAGG",
      "CCCCTA",
      "TCACTG"
    ]
  }'
```

Response esperada:

```
HTTP/1.1 200 OK
# Body: vacío
```

---

### 2) POST /mutant — Ejemplo HUMANO (HTTP 403 Forbidden)

Request:

```powershell
curl -i -X POST http://localhost:8080/mutant \
  -H "Content-Type: application/json" \
  -d '{
    "dna": [
      "ATGCGA",
      "CAGTGC",
      "TTATTT",
      "AGACGG",
      "GCGTCA",
      "TCACTG"
    ]
  }'
```

Response esperada:

```
HTTP/1.1 403 Forbidden
# Body: vacío
```

---

### 3) POST /mutant — Ejemplo INVÁLIDO (HTTP 400 Bad Request)

Casos típicos: ADN nulo, matriz no cuadrada, caracteres inválidos.

Request (carácter inválido `X`):

```powershell
curl -i -X POST http://localhost:8080/mutant \
  -H "Content-Type: application/json" \
  -d '{
    "dna": [
      "ATGCGA",
      "CAGTXC",
      "TTATGT",
      "AGAAGG",
      "CCCCTA",
      "TCACTG"
    ]
  }'
```

Response esperada:

```
HTTP/1.1 400 Bad Request
```

Body de ejemplo (JSON):

```json
{
  "error": "Validation failed",
  "message": "dna: El ADN solo debe contener caracteres A, T, C y G"
}
```

---

### 4) GET /stats — Ejemplo (HTTP 200 OK)

Request:

```powershell
curl -i http://localhost:8080/stats
```

Response esperada (ejemplo):

```
HTTP/1.1 200 OK
```

Body (JSON):

```json
{
  "countMutantDna": 40,
  "countHumanDna": 100,
  "ratio": 0.4
}
```

---

## Cómo compilar

```powershell
cd C:\Users\tinch\Desktop\GDdS\mutant-detector
.\gradlew.bat build
```

## Ejecutar (modo desarrollo)

```powershell
cd C:\Users\tinch\Desktop\GDdS\mutant-detector
.\gradlew.bat bootRun
# la app corre por defecto en http://localhost:8080
```

## Endpoints útiles (local)

- Swagger UI (documentación interactiva): `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`
- H2 Console (base de datos en memoria): `http://localhost:8080/h2-console`

## Tests y cobertura

```powershell
cd C:\Users\tinch\Desktop\GDdS\mutant-detector
.\gradlew.bat test jacocoTestReport
```

Reportes generados:

- `build/reports/tests/test/index.html`
- `build/reports/jacoco/test/html/index.html`

## Docker y despliegue en render

- `Dockerfile`: incluido en el proyecto para crear una imagen Docker de la aplicación.
Construir imagen Docker (ejemplo):


```powershell
cd C:\Users\tinch\Desktop\GDdS\mutant-detector
docker build -t mutantes-api .
# ejecutar contenedor
docker run -p 8080:8080 mutantes-api
```

Despliegue en Render

- La URL pública proporcionada por Render: `https://mutant-detector-api-nsx3.onrender.com`
- URL importantes: `https://mutant-detector-api-nsx3.onrender.com/swagger-ui.html` y `https://mutant-detector-api-nsx3.onrender.com/api-docs`

## Archivos relevantes para la entrega

- `build.gradle` (configuración de dependencias y plugin SpringDoc/OpenAPI)
- `src/main/java/com/magneto/mutant_detector/controller/MutantController.java` (endpoints)
- `src/main/java/com/magneto/mutant_detector/dto/DnaRequest.java` (validaciones)
- `src/main/java/com/magneto/mutant_detector/util/MutantDetector.java` (algoritmo)
- Diagramas: `diagrama-secuencia-GETstats.drawio.png`, `Diagrama-secuencia-POSTmutant.drawio.png`
