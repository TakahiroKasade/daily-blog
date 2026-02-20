package com.dailycoding.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class GithubEvent {

    private String type; // e.g., "PushEvent"

    private Repo repo;

    private Payload payload;

    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    @Data
    public static class Repo {
        private String name;
        private String url;
    }

    @Data
    public static class Payload {
        private List<Commit> commits;
    }

    @Data
    public static class Commit {
        private String sha;
        private String message;
    }
}
