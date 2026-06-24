# Render Deploy Runbook

?遢?辣閮? `daily-blog` ??Render 銝蝵脯????舐?璅?瘚??靘???Render deploy 憭望?嚗??折遢?辣瑼Ｘ嚗?閬?交蝔???
## ?桀??嗆?

```text
GitHub main
  -> Render Web Service: daily-blog-68lf
  -> Dockerfile multi-stage build
  -> Spring Boot prod profile
  -> Render PostgreSQL
```

?賊?瑼?嚗?
- `Dockerfile`
- `src/main/resources/application-prod.properties`
- `.dockerignore`
- `pom.xml`

## 甇?虜?函蔡瘚?

1. 蝣箄??祆?撌乩??銋暹楊嚗?
```powershell
git status
```

2. 蝣箄????commit嚗?
```powershell
git log --oneline -5
```

3. ?典 GitHub嚗?
```powershell
git push origin main
```

4. Render ??auto deploy?瘝??芸??函蔡嚗?Render Web Service嚗?
```text
Manual Deploy -> Deploy latest commit
```

5. ?函蔡??敺???

```text
https://daily-blog-68lf.onrender.com/login
```

6. ?餃 admin 敺?dashboard嚗?
```text
https://daily-blog-68lf.onrender.com/admin/dashboard
```

## Render Web Service 閮剖?

Web Service ?蝙??Docker ?函蔡嚗ranch ?嚗?
```text
main
```

Render service ???迤蝣?GitHub repo嚗?
```text
daily-blog
```

Dockerfile runtime ???誘敹?靽???摨?

```dockerfile
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
```

銝??孵?銝?車??嚗?
```dockerfile
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
```

??嚗-Dspring.profiles.active=prod` ??JVM ?嚗????`-jar` ?嚗??prod profile ?航銝?鋡?Spring Boot 甇?Ⅱ雿輻??
## Render Environment Variables

Web Service ??Environment 敹???

```text
SPRING_PROFILES_ACTIVE=prod
DB_URL=jdbc:postgresql://<internal-hostname>:5432/<database-name>
DB_USERNAME=<database-user>
DB_PASSWORD=<database-password>
```

??嚗DB_URL` 敹???JDBC ?澆???
甇?Ⅱ嚗?
```text
jdbc:postgresql://dpg-example-a:5432/dailyblog_db
```

?航炊嚗?
```text
postgresql://user:password@dpg-example-a/dailyblog_db
postgres://user:password@dpg-example-a/dailyblog_db
```

憒?雿輻 Render PostgreSQL ??Internal Database URL嚗ender ?航蝯衣???`postgresql://...` ?澆??pring Boot ??`spring.datasource.url` 銝?湔?車?澆?嚗?????JDBC ?澆???
## PostgreSQL 瑼Ｘ??

Render PostgreSQL 敹???Web Service ?典?銝??region嚗??賭蝙??internal hostname??
?閬?撠?

- Web Service region
- PostgreSQL region
- PostgreSQL internal hostname
- Database name
- Username
- Password

憒? Free plan 瘝? Shell/SSH access嚗??賢 Web Service 鋆∟? `echo $DB_URL` ??`getent hosts`??賡? Render Dashboard ??logs ???
## 甇?虜?? Log ?府?

Render latest deploy log ?府?嚗?
```text
The following 1 profile is active: "prod"
Finished Spring Data repository scanning ... Found 8 JPA repository interfaces.
HikariPool-1 - Start completed.
Tomcat started on port 10000
Started DailyBlogApplication
```

`Found 8 JPA repository interfaces` 銵函內?桀? RBAC/audit ???撘?鋡恍蝵脯?
憒?? `Found 5 JPA repository interfaces`嚗虜隞?” Render 頝??commit?? branch嚗??? repo??
## 撣貉??航炊撠

### 1. Build failure: cannot find symbol

靘?嚗?
```text
cannot find symbol
method findOperationDistribution()
```

??Java 蝺刻陌憭望?嚗ender build 瘝???
??嚗?
1. ?冽璈?嚗?
```powershell
mvn compile -B
```

2. 靽格迤 missing method/import/class??3. commit 敺?push??
### 2. No active profile set

靘?嚗?
```text
No active profile set, falling back to 1 default profile: "default"
```

??prod profile 瘝??啜?
??嚗?
1. 瑼Ｘ Dockerfile ENTRYPOINT ?????2. 瑼Ｘ Render Environment ?臬??

```text
SPRING_PROFILES_ACTIVE=prod
```

3. ? deploy latest commit??
### 3. Driver claims to not accept jdbcUrl

靘?嚗?
```text
Driver org.postgresql.Driver claims to not accept jdbcUrl, postgresql://user:password@host/db
```

??`DB_URL` 銝 JDBC ?澆???
??嚗?
??Render Environment ??`DB_URL` 敺?

```text
postgresql://user:password@host/db
```

?寞?嚗?
```text
jdbc:postgresql://host:5432/db
```

撣喳???曉嚗?
```text
DB_USERNAME=user
DB_PASSWORD=<database-password>
```

### 4. Unable to determine Dialect without JDBC metadata

靘?嚗?
```text
Unable to determine Dialect without JDBC metadata
```

??Hibernate ?⊥??? PostgreSQL ?? metadata?虜銝 dialect ?祈澈??嚗 DB ???鞈?銝迤蝣箸??????DB??
瑼Ｘ??嚗?
1. log ?臬??`The following 1 profile is active: "prod"`
2. `DB_URL` ?臬??`jdbc:postgresql://...`
3. PostgreSQL ?臬摮
4. Web Service ??PostgreSQL ?臬??region
5. `DB_USERNAME` / `DB_PASSWORD` ?臬?舐?憿?DB ?董撖?6. `DB_URL` host ?臬?舐?憿?DB ??internal hostname

### 5. No open ports detected

靘?嚗?
```text
No open ports detected, continuing to scan...
```

憒?敺 Spring Boot ????嚗虜?芣 Render ?函?敺?app bind port嚗?銝摰?航炊??
?砍?獢?prod 閮剖?雿輻嚗?
```properties
server.port=${PORT:8080}
```

Render ??靘?`PORT`嚗?憒?`10000`??
## ?祆?皜祈岫

銝?祆璈???

```powershell
mvn spring-boot:run
```

璅⊥ prod profile嚗?
```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/dailyblog"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="雿??祆? PostgreSQL 撖Ⅳ"

mvn spring-boot:run "-Dspring-boot.run.profiles=prod"
```

銝???Render production password 撖怠瑼???commit??
## 摰瘜冽?鈭?

- 銝???`DB_PASSWORD` 鞎澆?予摰扎?隞嗆? GitHub??- 憒?撖Ⅳ?曄?鋡怨票?箔?嚗???Render PostgreSQL ????撱?credentials??- `uploads/` 銝?閰?commit ??GitHub??- `.agent/` 銝?閰?commit ??GitHub??- `.env` / `application-local.properties` 銝?閰?commit ??GitHub??- ?身 admin 撖Ⅳ `admin123` ?芷?葫閰佗??函蔡敺??寞???
## ?撠??仿?摨?
瘥活 Render 憯?嚗??瑼Ｘ嚗?
1. ???deploy commit hash ?臬蝑 GitHub ???commit??2. Build ?臬????3. Log ?臬憿舐內 `prod` profile??4. Log ?臬憿舐內 `Found 8 JPA repository interfaces`??5. `DB_URL` ?臬??JDBC ?澆???6. PostgreSQL ??Web Service ?臬??region??7. ?臬??`HikariPool-1 - Start completed`??8. ?臬??`Started DailyBlogApplication`??
憒?蝚?7 甇乩??仃??????DB ????蝚?8 甇交?????航炊嚗???web/controller/security log?