package com.xiaowu.crowd.mvc.config;

import com.xiaowu.crowd.entity.po.Admin;
import com.xiaowu.crowd.entity.po.Role;
import com.xiaowu.crowd.service.AdminService;
import com.xiaowu.crowd.service.AuthService;
import com.xiaowu.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CrowdUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;


    /**
     *  重写loadUserByUsername
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        1，根据账号名称查询admin对象
        Admin admin = adminService.selectAdminByLoginAcct(username);
//        2,根据adminid查询角色信息
        List<Role> assignedRole = roleService.getAssignedRole(admin.getId());
//        3,根据adminid查询权限信息
        List<String> assignedAuthNameByAdminId = authService.getAssignedAuthNameByAdminId(admin.getId());
//        5,创建集合对象用来储存GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
//        6,遍历assignedRoleList存入角色信息
        for (Role role :assignedRole) {
            String roleName = "ROLE_"+role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }
//        7,遍历assignedAuthNameByAdminId存入权限的信息
        for (String authName: assignedAuthNameByAdminId) {
            authorities.add(new SimpleGrantedAuthority(authName));
        }
//        8,封装SecurityAdmin对象
        SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);
        return securityAdmin;
    }
}
