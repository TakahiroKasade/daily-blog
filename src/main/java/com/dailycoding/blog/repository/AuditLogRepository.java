package com.dailycoding.blog.repository;

import com.dailycoding.blog.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    /**
     * 統計最近 7 天的每日操作次數
     */
    @Query(value = "SELECT CAST(created_at AS DATE) as date, COUNT(*) as count " +
                   "FROM audit_logs " +
                   "WHERE created_at >= CURRENT_DATE - INTERVAL '7 days' " +
                   "GROUP BY CAST(created_at AS DATE) " +
                   "ORDER BY date ASC", nativeQuery = true)
    List<Map<String, Object>> findLast7DaysActivity();

    /**
     * 統計特定使用者最近 7 天的每日操作次數
     */
    @Query(value = "SELECT CAST(created_at AS DATE) as date, COUNT(*) as count " +
                   "FROM audit_logs " +
                   "WHERE operator = :operator AND created_at >= CURRENT_DATE - INTERVAL '7 days' " +
                   "GROUP BY CAST(created_at AS DATE) " +
                   "ORDER BY date ASC", nativeQuery = true)
    List<Map<String, Object>> findLast7DaysActivityByOperator(String operator);

    /**
     * 統計特定使用者的操作類型佔比
     */
    @Query(value = "SELECT operation as label, COUNT(*) as value " +
                   "FROM audit_logs " +
                   "WHERE operator = :operator " +
                   "GROUP BY operation", nativeQuery = true)
    List<Map<String, Object>> findOperationDistributionByOperator(String operator);

    /**
     * 獲取特定使用者最近的 10 筆操作紀錄
     */
    List<AuditLog> findTop10ByOperatorOrderByCreatedAtDesc(String operator);
}
