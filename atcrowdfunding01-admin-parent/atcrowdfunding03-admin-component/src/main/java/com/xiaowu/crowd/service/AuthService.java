package com.xiaowu.crowd.service;

import com.xiaowu.crowd.entity.po.Auth;


import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Auth> getAllAuth();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    Boolean saveRoleAuthRelathinship(Map<String,List<Integer>> map);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);

}
