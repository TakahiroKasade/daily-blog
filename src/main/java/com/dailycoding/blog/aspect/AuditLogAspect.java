package com.dailycoding.blog.aspect;

import com.dailycoding.blog.annotation.LogOperation;
import com.dailycoding.blog.entity.AuditLog;
import com.dailycoding.blog.repository.AuditLogRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class AuditLogAspect {

    private final AuditLogRepository auditLogRepository;

    public AuditLogAspect(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Around("@annotation(logOperation)")
    public Object recordLog(ProceedingJoinPoint joinPoint, LogOperation logOperation) throws Throwable {
        long startTime = System.currentTimeMillis();
        String status = "SUCCESS";
        
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            status = "FAIL";
            throw e;
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            saveLog(joinPoint, logOperation, status, executionTime);
        }
    }

    private void saveLog(ProceedingJoinPoint joinPoint, LogOperation logOperation, String status, long executionTime) {
        AuditLog log = new AuditLog();
        
        // 1. 操作描述
        log.setOperation(logOperation.value());
        
        // 2. 操作者 (從 SecurityContext 獲取)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null) ? auth.getName() : "ANONYMOUS";
        log.setOperator(username);
        
        // 3. 方法名稱
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
        
        // 4. 參數
        Object[] args = joinPoint.getArgs();
        log.setParams(Arrays.toString(args));
        
        // 5. 狀態與時間
        log.setStatus(status);
        log.setExecutionTime(executionTime);
        log.setCreatedAt(LocalDateTime.now());
        
        auditLogRepository.save(log);
    }
}
