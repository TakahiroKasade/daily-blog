package com.dailycoding.blog.rbac;

import com.dailycoding.blog.entity.Permission;
import com.dailycoding.blog.entity.Role;
import com.dailycoding.blog.entity.User;
import com.dailycoding.blog.repository.UserRepository;
import com.dailycoding.blog.repository.RoleRepository;
import com.dailycoding.blog.repository.PermissionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class RBACRelationshipTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    public void testUserRolePermissionRelationship() {
        // 1. 建立權限
        Permission readPost = new Permission();
        readPost.setName("POST_READ_TEST");
        permissionRepository.save(readPost);

        Permission writePost = new Permission();
        writePost.setName("POST_WRITE_TEST");
        permissionRepository.save(writePost);

        // 2. 建立角色並分配權限
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN_TEST");
        adminRole.setPermissions(Set.of(readPost, writePost));
        roleRepository.save(adminRole);

        // 3. 建立使用者並分配角色
        User user = new User();
        user.setUsername("testuser_rbac");
        user.setPassword("password");
        user.setRoles(Set.of(adminRole));
        userRepository.save(user);

        // 4. 驗證關聯
        User savedUser = userRepository.findByUsername("testuser_rbac").orElseThrow();
        assertThat(savedUser.getRoles()).hasSize(1);
        
        Role savedRole = savedUser.getRoles().iterator().next();
        assertThat(savedRole.getName()).isEqualTo("ROLE_ADMIN_TEST");
        assertThat(savedRole.getPermissions()).hasSize(2);
        assertThat(savedRole.getPermissions()).extracting(Permission::getName)
                .containsExactlyInAnyOrder("POST_READ_TEST", "POST_WRITE_TEST");
    }
}
