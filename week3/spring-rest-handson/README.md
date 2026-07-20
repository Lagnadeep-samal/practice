# spring-rest-handson

Spring Boot 3 / Java 21 project implementing the full hands-on sequence:

| # | Exercise | Where it lives |
|---|----------|-----------------|
| 1 | Create a Spring Web Project using Maven | `pom.xml`, `SpringRestHandsonApplication.java` |
| 1 | Spring Core - Load Country from Spring Configuration XML | `src/main/resources/spring-config.xml`, `model/Country.java` |
| 2 | Hello World RESTful Web Service | `controller/HelloWorldController.java` |
| 2 | REST - Country Web Service | `controller/CountryController.java`, `service/CountryService.java` |
| 2 | REST - Get country based on country code | `CountryController.getCountryByCode` |
| 5 | JWT-handson: authentication service that returns JWT | `security/*`, `controller/AuthController.java` |

## Run it

```bash
mvn spring-boot:run
```

The app starts on `http://localhost:8080`.

## Try each step

**Hello World**
```bash
curl http://localhost:8080/hello
curl "http://localhost:8080/hello?name=Claude"
```

**Countries (open list, no auth wired to this path itself is protected by
JWT once Step 5 is in place — get a token first, see below)**

```bash
# 1. Log in to get a JWT (demo user: user / password)
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user","password":"password"}'
# -> { "token": "eyJhbGciOi..." }

# 2. Use the token to call the protected country endpoints
TOKEN="paste-the-token-here"

curl http://localhost:8080/countries \
  -H "Authorization: Bearer $TOKEN"

curl http://localhost:8080/countries/IN \
  -H "Authorization: Bearer $TOKEN"

curl -X POST http://localhost:8080/countries \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"code":"DE","name":"Germany","capital":"Berlin","population":83000000}'

curl -X DELETE http://localhost:8080/countries/DE \
  -H "Authorization: Bearer $TOKEN"
```

## Notes

- The country data seeded in `spring-config.xml` is loaded once at startup
  via `@ImportResource` and copied into an in-memory list in
  `CountryService`, so POST/DELETE work without a real database.
- `/hello` and `/auth/login` are the only endpoints open to anonymous
  callers; everything else requires a valid `Authorization: Bearer <jwt>`
  header (see `security/SecurityConfig.java`).
- The JWT signing key is generated at application startup for demo
  purposes only — in a real deployment it must come from a secret store
  and stay stable across restarts.
