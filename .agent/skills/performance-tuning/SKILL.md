---
name: performance-tuning
description: 性能調教優化 - 資料庫查詢、快取策略、前端優化
---

# Performance Tuning Skill

## 概述
全方位性能優化建議，涵蓋後端資料庫優化、快取策略、前端載入優化等面向。

## 使用方式

### 全面性能分析
```
請幫我分析專案性能並給予優化建議
```

### 特定檔案優化
```
請幫我優化 [檔案路徑] 的性能
```

### 資料庫查詢優化
```
請幫我檢查資料庫查詢效能
```

## 檢查項目

### 1. 資料庫優化

#### N+1 查詢問題
```java
// ❌ N+1 問題
List<Order> orders = orderRepository.findAll();
for (Order order : orders) {
    User user = order.getUser(); // 每次迴圈都查詢一次
}

// ✅ 使用 JOIN FETCH
@Query("SELECT o FROM Order o JOIN FETCH o.user")
List<Order> findAllWithUser();
```

#### 索引優化
```sql
-- 檢查慢查詢
EXPLAIN ANALYZE SELECT * FROM users WHERE email = 'test@example.com';

-- 建議索引
CREATE INDEX idx_users_email ON users(email);
```

#### 分頁查詢
```java
// ✅ 使用分頁避免大量資料載入
Page<User> users = userRepository.findAll(PageRequest.of(0, 20));
```

### 2. JPA/Hibernate 優化

#### 延遲載入
```java
// ✅ 使用 LAZY loading
@OneToMany(fetch = FetchType.LAZY)
private List<Order> orders;
```

#### 批次處理
```properties
# application.properties
spring.jpa.properties.hibernate.jdbc.batch_size=50
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true
```

#### 二級快取
```java
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category {
    // 適合快取的實體：少變動、頻繁讀取
}
```

### 3. API 優化

#### 快取策略
```java
@Cacheable(value = "users", key = "#id")
public User findById(Long id) {
    return userRepository.findById(id).orElseThrow();
}

@CacheEvict(value = "users", key = "#user.id")
public User update(User user) {
    return userRepository.save(user);
}
```

#### 壓縮回應
```properties
# 啟用 GZIP 壓縮
server.compression.enabled=true
server.compression.mime-types=application/json,text/html,text/css,application/javascript
server.compression.min-response-size=1024
```

#### 非同步處理
```java
@Async
public CompletableFuture<Report> generateReportAsync(Long userId) {
    // 耗時操作
    return CompletableFuture.completedFuture(report);
}
```

### 4. 前端優化

#### 資源載入
```html
<!-- CSS 放在 head -->
<link rel="stylesheet" href="/css/style.css">

<!-- JS 放在 body 結尾或使用 defer -->
<script src="/js/app.js" defer></script>
```

#### 圖片優化
```html
<!-- 使用適當尺寸 -->
<img src="/images/hero.webp" 
     loading="lazy"
     width="800" 
     height="600"
     alt="Hero image">
```

#### API 呼叫優化
```javascript
// ✅ 使用 debounce 避免過度呼叫
const searchUsers = debounce(async (query) => {
    const result = await apiRequest(`/api/users?q=${query}`);
    // ...
}, 300);

// ✅ 批次請求
const [users, orders] = await Promise.all([
    fetchUsers(),
    fetchOrders()
]);
```

### 5. 連線池優化

```properties
# HikariCP 設定
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.connection-timeout=20000
```

## 性能指標

| 指標 | 建議值 | 說明 |
|-----|-------|-----|
| API 回應時間 | < 200ms | 一般 API 請求 |
| 頁面載入時間 | < 3s | 首次載入 |
| 資料庫查詢 | < 100ms | 單一查詢 |
| 記憶體使用 | < 80% | 持續監控 |

## 輸出報告格式

```markdown
# 性能分析報告

## 📊 效能評分: 75/100

## 🔴 Critical Issues
1. **N+1 查詢**: OrderController.java - 每次請求產生 50+ 查詢

## 🟠 建議優化
1. 新增資料庫索引: users.email
2. 啟用二級快取: Category 實體
3. 前端資源使用 CDN

## 📈 預期改善
- API 回應時間: 500ms → 100ms
- 頁面載入: 5s → 2s
```
