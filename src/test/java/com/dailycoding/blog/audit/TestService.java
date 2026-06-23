package com.dailycoding.blog.audit;

import com.dailycoding.blog.annotation.LogOperation;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @LogOperation("測試操作")
    public void performAction(String param) {
        // 做一些事
    }
}
