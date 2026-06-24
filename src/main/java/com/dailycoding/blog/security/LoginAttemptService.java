package com.dailycoding.blog.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

@Service
public class LoginAttemptService {

    private static final Logger logger = LoggerFactory.getLogger(LoginAttemptService.class);

    static final int MAX_FAILED_ATTEMPTS = 5;
    static final Duration FAILURE_WINDOW = Duration.ofMinutes(5);
    static final Duration LOCK_DURATION = Duration.ofMinutes(10);

    private static final int MAX_USERNAME_LENGTH = 100;
    private static final String FAILURE_PREFIX = "login:fail:";
    private static final String LOCK_PREFIX = "login:lock:";

    private final StringRedisTemplate redisTemplate;

    public LoginAttemptService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void recordFailure(String username) {
        String normalizedUsername = normalizeUsername(username);
        if (normalizedUsername == null) {
            return;
        }

        try {
            String failureKey = failureKey(normalizedUsername);
            Long attempts = redisTemplate.opsForValue().increment(failureKey);
            if (attempts != null && attempts == 1L) {
                redisTemplate.expire(failureKey, FAILURE_WINDOW);
            }

            if (attempts != null && attempts >= MAX_FAILED_ATTEMPTS) {
                redisTemplate.opsForValue().set(lockKey(normalizedUsername), "locked", LOCK_DURATION);
                redisTemplate.delete(failureKey);
            }
        } catch (RuntimeException ex) {
            logger.warn("Unable to record login failure in Redis; login throttling is temporarily disabled. Cause: {}", ex.toString());
        }
    }

    public boolean isLocked(String username) {
        String normalizedUsername = normalizeUsername(username);
        if (normalizedUsername == null) {
            return false;
        }

        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(lockKey(normalizedUsername)));
        } catch (RuntimeException ex) {
            logger.warn("Unable to check login lock in Redis; login throttling is temporarily disabled. Cause: {}", ex.toString());
            return false;
        }
    }

    public void clear(String username) {
        String normalizedUsername = normalizeUsername(username);
        if (normalizedUsername == null) {
            return;
        }

        try {
            redisTemplate.delete(List.of(failureKey(normalizedUsername), lockKey(normalizedUsername)));
        } catch (RuntimeException ex) {
            logger.warn("Unable to clear login throttling keys in Redis. Cause: {}", ex.toString());
        }
    }

    private String failureKey(String username) {
        return FAILURE_PREFIX + username;
    }

    private String lockKey(String username) {
        return LOCK_PREFIX + username;
    }

    private String normalizeUsername(String username) {
        if (username == null) {
            return null;
        }

        String normalized = username.trim().toLowerCase(Locale.ROOT);
        if (normalized.isBlank()) {
            return null;
        }

        if (normalized.length() > MAX_USERNAME_LENGTH) {
            return normalized.substring(0, MAX_USERNAME_LENGTH);
        }
        return normalized;
    }
}

