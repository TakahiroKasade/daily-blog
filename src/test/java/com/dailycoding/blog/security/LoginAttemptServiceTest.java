package com.dailycoding.blog.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginAttemptServiceTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    private LoginAttemptService loginAttemptService;

    @BeforeEach
    void setUp() {
        loginAttemptService = new LoginAttemptService(redisTemplate);
    }

    @Test
    void recordFailureStartsCounterWithTtlOnFirstFailure() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.increment("login:fail:admin")).thenReturn(1L);

        loginAttemptService.recordFailure("Admin");

        verify(valueOperations).increment("login:fail:admin");
        verify(redisTemplate).expire("login:fail:admin", Duration.ofMinutes(5));
        verify(valueOperations, never()).set("login:lock:admin", "locked", Duration.ofMinutes(10));
    }

    @Test
    void recordFailureLocksAccountAtMaximumAttempts() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.increment("login:fail:admin")).thenReturn(5L);

        loginAttemptService.recordFailure("admin");

        verify(valueOperations).set("login:lock:admin", "locked", Duration.ofMinutes(10));
        verify(redisTemplate).delete("login:fail:admin");
    }

    @Test
    void recordFailureDoesNotThrowWhenRedisIsUnavailable() {
        when(redisTemplate.opsForValue()).thenThrow(new IllegalStateException("Redis unavailable"));

        assertThatCode(() -> loginAttemptService.recordFailure("admin"))
                .doesNotThrowAnyException();
    }

    @Test
    void isLockedReturnsTrueWhenLockKeyExists() {
        when(redisTemplate.hasKey("login:lock:admin")).thenReturn(true);

        boolean locked = loginAttemptService.isLocked("admin");

        assertThat(locked).isTrue();
    }

    @Test
    void isLockedReturnsFalseWhenRedisIsUnavailable() {
        when(redisTemplate.hasKey("login:lock:admin")).thenThrow(new IllegalStateException("Redis unavailable"));

        boolean locked = loginAttemptService.isLocked("admin");

        assertThat(locked).isFalse();
    }

    @Test
    void clearRemovesFailureAndLockKeys() {
        loginAttemptService.clear("admin");

        verify(redisTemplate).delete(List.of("login:fail:admin", "login:lock:admin"));
    }
}
