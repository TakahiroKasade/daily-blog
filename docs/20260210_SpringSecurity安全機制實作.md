# [20260210] Spring Security 安全機制實作 (Phase 2: Security Guard)

## 📋 概述
為 Daily Blog 專案整合 Spring Security，實現身份驗證 (Authentication) 與授權 (Authorization) 機制。
本文件記錄了實作過程中的概念學習、Q&A 問答、以及完成的程式碼。

## 🎯 完成項目
- [x] `pom.xml` 新增 Spring Security 與 Thymeleaf Security 依賴
- [x] `User.java` (使用者 Entity)
- [x] `UserRepository.java` (使用者 Repository)
- [x] `CustomUserDetailsService.java` (自訂認證服務)
- [x] `SecurityConfig.java` (安全性設定)
- [ ] `DataSeeder.java` 新增管理員帳號 — 待實作
- [ ] `index.html` 前端權限控制 — 待實作

## 📁 異動檔案
| 檔案路徑 | 異動類型 | 說明 |
|---------|---------|-----|
| `pom.xml` | 修改 | 新增 `spring-boot-starter-security` 和 `thymeleaf-extras-springsecurity6` |
| `src/main/java/.../entity/User.java` | 新增 | 使用者實體，對應 `users` 資料表 |
| `src/main/java/.../repository/UserRepository.java` | 新增 | 使用者資料存取介面 |
| `src/main/java/.../service/CustomUserDetailsService.java` | 新增 | 自訂驗證服務，橋接 UserRepository 與 Spring Security |
| `src/main/java/.../config/SecurityConfig.java` | 新增 | 安全性設定：定義公開/管制路徑、登入登出行為、密碼加密方式 |

---

## 💡 核心觀念筆記

### 1. Spring Security 四大核心
| 概念 | 白話文 | 比喻 |
|------|--------|------|
| **Authentication (認證)** | 你是誰？ | 保全查驗你的證件 |
| **Authorization (授權)** | 你可以去哪裡？ | 門禁卡只能刷特定樓層 |
| **Filter Chain (過濾鏈)** | 每個請求都要過安檢 | 機場安檢的多道關卡 |
| **Principal (當事人)** | 系統認得的「你」 | 登入後發的通行證 |

### 2. PostgreSQL 與 MySQL 的差異 (Schema 層級)
```
MySQL:    Database = Schema (幾乎同義)
          → SELECT * FROM table 就可以

PostgreSQL: Database > Schema > Table
          → 預設 Schema 叫 "public"
          → 完整寫法：SELECT * FROM public.table
          → pgAdmin 會自動加上 public. 前綴
```

### 3. `@Table(name = "users")` 為什麼不能用 `user`？
因為 `user` 是 PostgreSQL 的**保留字** (Reserved Keyword)。
如果用 `@Table(name = "user")`，JPA 生成的 SQL 會是 `SELECT * FROM user`，
Postgres 會誤以為你在查系統內建的 `user` 關鍵字，導致錯誤。

---

## ❓ Q&A 問答記錄

### Q1：為什麼有兩個 Service？(`CustomUserDetailsService` vs `UserDetailsService`)

**答：**
- `UserDetailsService` = Spring Security 定義的 **Interface (契約)**，來自 `org.springframework.security.core.userdetails`。
- `CustomUserDetailsService` = **我們自己寫的 Class (實作)**，負責去我們的資料庫查帳號密碼。
- 關係是 `implements` (實作)，不是內部類別 (Inner Class)。

```java
// Spring 的契約：「你必須提供 loadUserByUsername 方法」
public interface UserDetailsService {
    UserDetails loadUserByUsername(String username);
}

// 我們的實作：「好，我用 UserRepository 去查」
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) { ... }
}
```

### Q2：`UserDetails` 是什麼？是我們自己宣告的嗎？

**答：** 不是！`UserDetails` 也是 Spring Security 框架提供的 Interface。

```
📦 org.springframework.security.core.userdetails (Spring Security 套件)
├── UserDetailsService   (Interface - 認證服務契約)
├── UserDetails          (Interface - 使用者資料規格)
└── User                 (Class - UserDetails 的內建實作)
```

### Q3：為什麼有兩個 `User`？

**答：** 專案中同時存在兩個叫 `User` 的類別：

| | 你的 User | Spring 的 User |
|---|---|---|
| 完整路徑 | `com.dailycoding.blog.entity.User` | `org.springframework.security.core.userdetails.User` |
| 用途 | 存在資料庫裡的資料 | 給 Spring Security 認證用的格式 |
| 特有方法 | getId(), getUsername()... | withUsername(), password(), roles(), build() |

**在 `CustomUserDetailsService` 中兩個都有用到：**
```java
// ① 用「你的 User」去資料庫查資料
com.dailycoding.blog.entity.User user = userRepository.findByUsername(username)...

// ② 用「Spring 的 User」把資料轉換成 Security 格式
return org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPassword())
        .roles(user.getRole())
        .build();
```

### Q4：`Optional` 的 `.orElseThrow()` 是什麼？

**答：** `Optional` 是 Java 8 引入的「禮物盒」：
- 盒子裡面**可能有東西** (找到資料)
- 盒子裡面**可能是空的** (查無此人)

| 方法 | 行為 | 使用場景 |
|------|------|---------|
| `.orElse(null)` | 找不到就回傳 null | `PostService.getPostById()` |
| `.orElseThrow(...)` | 找不到就丟出例外 | `CustomUserDetailsService` (登入失敗) |

### Q5：`@Repository` 在 `JpaRepository` 子介面上是多餘的嗎？

**答：** 是的。當一個 Interface 繼承了 `JpaRepository` 之後，Spring Boot 會**自動**將它註冊為 Bean。
加了 `@Repository` 不會出錯，但就像穿了兩件雨衣 — 功能重複了。

### Q6：SecurityConfig 裡面 `authorizeHttpRequests`、`formLogin`、`logout` 各是做什麼的？

**答：**
| 方法 | 用途 | 比喻 |
|------|------|------|
| `authorizeHttpRequests()` | 設定哪些網址要登入、哪些不用 | 大樓平面圖：標出「公共區域」和「禁區」 |
| `formLogin()` | 設定登入行為 (登入頁面、登入成功後跳到哪) | 大廳的刷卡機 |
| `logout()` | 設定登出行為 (登出後跳到哪) | 離開大樓時繳回門禁卡 |

### Q7：`@Bean` 的 `passwordEncoder()` 每次加密都會 new 新物件嗎？

**答：** 不會！`@Bean` 標註的方法，Spring 啟動時 **只會執行一次**，建立出來的物件會被 Spring 容器保管。
之後不管系統在哪裡需要密碼加密，Spring 都會自動把 **同一個** `BCryptPasswordEncoder` 物件拿出來用。
這就是 Spring 的 **IoC (控制反轉)** 精神 — 物件的生命週期由 Spring 管理，不需要手動 new。

### Q8：為什麼 `formLogin()` 和 `logout()` 後面都要加 `.permitAll()`？

**答：** 因為我們設定了 `.anyRequest().authenticated()`（所有路徑都需要登入）。
如果不加 `.permitAll()`，登入頁面 `/login` 本身也會需要登入才能存取，造成 **死鎖 (Dead Lock)**：

> 保全：「你需要門禁卡才能進大樓。」
> 你：「那我去服務台辦卡。」
> 保全：「服務台在大樓裡面，你需要門禁卡才能進去辦卡。」
> 🔴 永遠進不去！

所以 `.permitAll()` 的意思是：「登入頁面和登出請求本身是公開的，不受 `.anyRequest().authenticated()` 限制。」

---

## 🔧 程式碼變更重點

### User Entity (`User.java`)
```java
@Entity
@Table(name = "users")  // ⚠️ 不能用 "user" (PostgreSQL 保留字)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)  // 帳號唯一且不可為空
    private String username;

    @Column(nullable = false)  // 密碼不可為空
    private String password;

    private String role;  // "ADMIN" / "USER"
}
```

### CustomUserDetailsService (`CustomUserDetailsService.java`)
```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
    // 注入我們的 UserRepository
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // ① 用我們的 User 查資料庫
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("找不到使用者"));

        // ② 轉換成 Spring Security 的 UserDetails 格式
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
```

### SecurityConfig (`SecurityConfig.java`)
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

## 📝 備註
- Phase 2 尚未完成，剩餘項目：DataSeeder 管理員初始化、前端權限控制。
- 本次實作採用 **Mentor Mode (導師模式)**，所有程式碼均由學習者手動撰寫。
- IntelliJ 快捷鍵筆記：`Alt + Enter` (自動修正 import), `Ctrl + Alt + O` (整理 import)。
