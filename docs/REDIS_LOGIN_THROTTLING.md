# Redis Login Throttling Runbook

This project uses Redis for login brute-force protection.

## What It Does

- Tracks failed login attempts by username in Redis.
- 5 failed attempts within 5 minutes locks that username for 10 minutes.
- A successful login clears the failed-attempt and lock keys.
- If Redis is temporarily unavailable, login still works, but throttling is skipped and a warning is logged.

## Local Startup

Start Docker Desktop first, then run Redis:

```powershell
docker run --name daily-blog-redis -p 6379:6379 -d redis:7
```

If the container already exists but is stopped:

```powershell
docker start daily-blog-redis
```

Check Redis status:

```powershell
docker exec daily-blog-redis redis-cli ping
```

Expected output:

```text
PONG
```

Then start the Spring Boot app normally:

```powershell
mvn spring-boot:run
```

## Useful Redis Checks

List login throttling keys:

```powershell
docker exec daily-blog-redis redis-cli keys "login:*"
```

Check remaining lock time for a username:

```powershell
docker exec daily-blog-redis redis-cli ttl "login:lock:admin"
```

Manually unlock a username:

```powershell
docker exec daily-blog-redis redis-cli del "login:fail:admin" "login:lock:admin"
```

## Render Notes

The current code can start even when Redis is not configured, but login throttling will be skipped until Redis is available.

When adding Redis on Render later, configure these environment variables on the Web Service:

```text
REDIS_HOST=<your redis host>
REDIS_PORT=<your redis port>
```

Keep the existing production database variables:

```text
SPRING_PROFILES_ACTIVE=prod
DB_URL=jdbc:postgresql://...
DB_USERNAME=...
DB_PASSWORD=...
```

After changing environment variables, use Render's `Save, rebuild, and deploy`.
