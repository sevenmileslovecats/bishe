# AGENTS.md

## Build & Run

- **Build (jar):** `.\mvnw.cmd clean package` (or `package -DskipTests=false` to run tests)
- **Build (war):** `.\mvnw.cmd clean package -f pom-war.xml`
- **Run:** `.\mvnw.cmd spring-boot:run` (or run the jar)
- **Tests are skipped by default** in both POMs (`<skipTests>true</skipTests>`). Override with `-DskipTests=false`.
- **Java 8**, **Spring Boot 2.2.2**, **Maven wrapper** (no local Maven install needed).

## Database

- MySQL at `127.0.0.1:3306`, database name `springboot02b8755d`, user `root` / password `123456`.
- Import the schema before first run: source `db/springboot02b8755d.sql`.
- SQL Server alternative is commented out in `application.yml`.

## Key Architecture Decisions

### Package base is `com`, NOT `com.jlwl`
The `pom.xml` groupId is `com.jlwl`, but all Java source lives under `com.*`. The main class is `com.SpringbootSchemaApplication`.

### MyBatis-Plus 2.x (old API)
This project uses **MyBatis-Plus 2.3 + mybatisplus-spring-boot-starter 1.0.5** — NOT the 3.x API.
- Pagination: `com.baomidou.mybatisplus.plugins.PaginationInterceptor`
- Logical delete: `com.baomidou.mybatisplus.mapper.LogicSqlInjector`
- Entity base class: `com.baomidou.mybatisplus.activerecord.Model` (if used)
- Do NOT use MyBatis-Plus 3.x APIs (`com.baomidou.mybatisplus.core.*`, `IPage`, etc.).

### Auth: Token-based with `@IgnoreAuth` escape hatch
`AuthorizationInterceptor` checks for a `Token` request header on every request. Method-level `@IgnoreAuth` annotation skips the check. Login/public endpoints should use `@IgnoreAuth`.

### Response wrapper: `com.utils.R`
ALL controllers must return `com.utils.R` (a `HashMap` subclass).
- Success: `R.ok().put("key", value)` or `R.ok("message")`
- Error: `R.error("message")` or `R.error(code, "message")`

### Global exception handler
`com.handler.GlobalExceptionHandler` (`@RestControllerAdvice`) catches uncaught exceptions and returns `R` with parsed DB error details (constraint violations, missing tables/columns, etc.).

### Static resources: manual config required
`InterceptorConfig` extends `WebMvcConfigurationSupport`, which disables Spring Boot's default static resource mapping. Static resources are configured manually:
- `/admin/**` → `classpath:/admin/`
- `/front/**` → `classpath:/front/`
- `/front-pc/**` → `classpath:/front-pc/` (directory does not exist yet — planned)

### Service/DAO pattern
- DAOs are plain interfaces with `Map<String, Object>` params (dynamic SQL via XML mappers in `src/main/resources/mapper/*.xml`).
- Services are thin pass-throughs annotated `@Service("camelCaseName")`, delegating to DAOs.

## Server Config

- Port: `8080`
- Context path: `/springboot02b8755d`
- App URL: `http://localhost:8080/springboot02b8755d/`

## Planning Docs

Feature implementation plans live in `.trae/documents/` (Chinese-language markdown).
