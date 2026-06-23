package com.dailycoding.blog.audit;

import com.dailycoding.blog.repository.AuditLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuditLogTest {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private TestService testService;

    @Test
    public void should_record_log_when_annotated_method_is_called() {
        // Act
        testService.performAction("Test Param");

        // Assert
        long count = auditLogRepository.count();
        assertThat(count).isGreaterThan(0); 
        
        var logs = auditLogRepository.findAll();
        assertThat(logs.get(0).getOperation()).isEqualTo("測試操作");
    }
}
