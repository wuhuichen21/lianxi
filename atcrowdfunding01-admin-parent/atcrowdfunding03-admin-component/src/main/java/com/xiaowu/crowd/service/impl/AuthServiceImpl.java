package com.xiaowu.crowd.service.impl;

import com.xiaowu.crowd.entity.po.Auth;
import com.xiaowu.crowd.entity.po.AuthExample;
import com.xiaowu.crowd.mapper.AuthMapper;
import com.xiaowu.crowd.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private Logger AuthServiceImplLogger =  LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthMapper authMapper;

    /**
     *  获取所有的auth
     * @return
     */
    @Override
    public List<Auth> getAllAuth() {
        List<Auth> authList = authMapper.selectByExample(new AuthExample());
        return authList;
    }


    /**
     *  根据roleid获取已选择的auth
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        List<Integer> authIdByRoleId = authMapper.getAssignedAuthIdByRoleId(roleId);
        if (authIdByRoleId!=null && authIdByRoleId.size()>0){
            return authIdByRoleId;
        }
        return null;
    }

    /**
     *  给role执行分配权限
     * @param map
     * @return
     */
    @Override
    public Boolean saveRoleAuthRelathinship(Map<String, List<Integer>> map) {
        List<Integer> roleList = map.get("roleId");
        Integer roleId = roleList.get(0);

//        删除旧记录
        Boolean deleteOldRelationship = authMapper.deleteOldRelationship(roleId);

        List<Integer> authIdList = map.get("authIdArray");
        if (authIdList != null && authIdList.size()>0){
            Integer integer = authMapper.insertNewRelationship(roleId, authIdList);
            if (integer<1){
                return false;
            }
        }
        return true;
    }


    /**
     *  登录时获取角色和权限
     * @param adminId
     * @return
     */
    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
        List<String> byAdminId = authMapper.selectAssignedAuthNameByAdminId(adminId);
        return byAdminId;
    }
}
