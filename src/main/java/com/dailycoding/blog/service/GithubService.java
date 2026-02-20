package com.dailycoding.blog.service;

import com.dailycoding.blog.dto.GithubEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {

    private final RestTemplate restTemplate;
    // 預設 GitHub 使用者名稱，可以提取到 application.properties
    private final String GITHUB_USERNAME = "TakahiroKasade"; 
    private final String GITHUB_API_URL = "https://api.github.com/users/" + GITHUB_USERNAME + "/events/public";

    public GithubService() {
        this.restTemplate = new RestTemplate();
    }

    public List<GithubEvent> getRecentEvents() {
        try {
            GithubEvent[] events = restTemplate.getForObject(GITHUB_API_URL, GithubEvent[].class);
            if (events != null) {
                // 只回傳 PushEvent 且只取前 5 筆，避免資訊過多
                return Arrays.stream(events)
                        .filter(e -> "PushEvent".equals(e.getType()))
                        .limit(5)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            // API 呼叫失敗時 (例如 Rate Limit)，回傳空列表，不影響主頁面顯示
            System.err.println("GitHub API Error: " + e.getMessage());
        }
        return Collections.emptyList();
    }
}
