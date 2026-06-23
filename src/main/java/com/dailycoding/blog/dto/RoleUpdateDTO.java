package com.dailycoding.blog.dto;

import lombok.Data;
import java.util.Set;

@Data
public class RoleUpdateDTO {
    private Long roleId;
    private String name;
    private Set<Long> permissionIds;
}
