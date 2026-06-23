package com.dailycoding.blog.controller;

import com.dailycoding.blog.annotation.LogOperation;
import com.dailycoding.blog.dto.RoleUpdateDTO;
import com.dailycoding.blog.dto.UserRoleUpdateDTO;
import com.dailycoding.blog.service.AdminService;
import com.dailycoding.blog.service.AuditService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final AuditService auditService;

    public AdminController(AdminService adminService, AuditService auditService) {
        this.adminService = adminService;
        this.auditService = auditService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/api/stats/activity")
    @ResponseBody
    public List<Map<String, Object>> getActivityStats() {
        return adminService.getRecentActivity();
    }

    @GetMapping("/api/stats/distribution")
    @ResponseBody
    public List<Map<String, Object>> getDistributionStats() {
        return adminService.getOperationStats();
    }

    @GetMapping("/api/stats/user")
    @ResponseBody
    public Map<String, Object> getUserStats(@RequestParam(required = false) String targetUser) {
        return auditService.getUserActivityStats(targetUser);
    }

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
        model.addAttribute("allRoles", adminService.getAllRoles());
        return "admin/user_list";
    }

    @PostMapping("/users/roles")
    @LogOperation("更新使用者角色")
    public String updateUserRoles(@ModelAttribute UserRoleUpdateDTO updateDTO) {
        adminService.updateUserRoles(updateDTO.getUserId(), updateDTO.getRoleIds());
        return "redirect:/admin/users";
    }

    @GetMapping("/roles")
    public String roleList(Model model) {
        model.addAttribute("roles", adminService.getAllRoles());
        model.addAttribute("allPermissions", adminService.getAllPermissions());
        return "admin/role_list";
    }

    @PostMapping("/roles")
    @LogOperation("新增/更新角色")
    public String updateRole(@ModelAttribute RoleUpdateDTO updateDTO) {
        adminService.updateRolePermissions(updateDTO.getRoleId(), updateDTO.getName(), updateDTO.getPermissionIds());
        return "redirect:/admin/roles";
    }

    @GetMapping("/roles/delete/{id}")
    @LogOperation("刪除角色")
    public String deleteRole(@PathVariable Long id) {
        adminService.deleteRole(id);
        return "redirect:/admin/roles";
    }
}
