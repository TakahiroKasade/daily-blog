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
     * Count daily audit activity across the last 7 days.
     */
    @Query(value = "SELECT CAST(created_at AS DATE) as date, COUNT(*) as count " +
                   "FROM audit_logs " +
                   "WHERE created_at >= CURRENT_DATE - INTERVAL '7 days' " +
                   "GROUP BY CAST(created_at AS DATE) " +
                   "ORDER BY date ASC", nativeQuery = true)
    List<Map<String, Object>> findLast7DaysActivity();

    /**
     * Count daily audit activity for one operator across the last 7 days.
     */
    @Query(value = "SELECT CAST(created_at AS DATE) as date, COUNT(*) as count " +
                   "FROM audit_logs " +
                   "WHERE operator = :operator AND created_at >= CURRENT_DATE - INTERVAL '7 days' " +
                   "GROUP BY CAST(created_at AS DATE) " +
                   "ORDER BY date ASC", nativeQuery = true)
    List<Map<String, Object>> findLast7DaysActivityByOperator(String operator);

    /**
     * Count audit operations grouped by operation for one operator.
     */
    @Query(value = "SELECT operation as label, COUNT(*) as value " +
                   "FROM audit_logs " +
                   "WHERE operator = :operator " +
                   "GROUP BY operation", nativeQuery = true)
    List<Map<String, Object>> findOperationDistributionByOperator(String operator);

    /**
     * Count all audit operations grouped by operation for the admin dashboard.
     */
    @Query(value = "SELECT operation as label, COUNT(*) as value " +
                   "FROM audit_logs " +
                   "GROUP BY operation " +
                   "ORDER BY value DESC", nativeQuery = true)
    List<Map<String, Object>> findOperationDistribution();

    /**
     * Find the latest 10 audit logs for one operator.
     */
    List<AuditLog> findTop10ByOperatorOrderByCreatedAtDesc(String operator);
}
