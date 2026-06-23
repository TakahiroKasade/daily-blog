package com.dailycoding.blog.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UserRoleUpdateDTO {
    private Long userId;
    private Set<Long> roleIds;
}
