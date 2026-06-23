package com.dailycoding.blog.service;

import com.dailycoding.blog.entity.User;
import com.dailycoding.blog.entity.Role;
import com.dailycoding.blog.entity.Permission;
import com.dailycoding.blog.repository.UserRepository;
import com.dailycoding.blog.repository.RoleRepository;
import com.dailycoding.blog.repository.PermissionRepository;
import com.dailycoding.blog.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final AuditLogRepository auditLogRepository;

    public AdminService(UserRepository userRepository, RoleRepository roleRepository, 
                        PermissionRepository permissionRepository, AuditLogRepository auditLogRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.auditLogRepository = auditLogRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public List<Map<String, Object>> getRecentActivity() {
        return auditLogRepository.findLast7DaysActivity();
    }

    public List<Map<String, Object>> getOperationStats() {
        return auditLogRepository.findOperationDistribution();
    }

    @Transactional
    public void updateUserRoles(Long userId, Set<Long> roleIds) {
        User user = userRepository.findById(userId).orElseThrow();
        List<Role> roles = roleRepository.findAllById(roleIds);
        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);
    }

    @Transactional
    public void updateRolePermissions(Long roleId, String name, Set<Long> permissionIds) {
        Role role;
        if (roleId != null) {
            role = roleRepository.findById(roleId).orElseThrow();
        } else {
            role = new Role();
        }
        role.setName(name);
        List<Permission> permissions = permissionRepository.findAllById(permissionIds);
        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);
    }
    
    @Transactional
    public void deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }
}
