package com.xiaowu.crowd.service;

import com.github.pagehelper.PageInfo;
import com.xiaowu.crowd.entity.po.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

    Boolean saveRole(Role role);

    Boolean updateRole(Role role);

    Boolean removeRole(List<Integer> roleIdList);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);

    Boolean saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);
}
