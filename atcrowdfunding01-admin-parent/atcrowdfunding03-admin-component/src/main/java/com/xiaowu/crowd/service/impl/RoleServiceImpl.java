package com.xiaowu.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaowu.crowd.entity.po.Role;
import com.xiaowu.crowd.entity.po.RoleExample;
import com.xiaowu.crowd.mapper.RoleMapper;
import com.xiaowu.crowd.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private Logger RoleServiceImplLogger =  LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 角色分页查询
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
//        开启分页功能
        PageHelper.startPage(pageNum,pageSize);

//        执行查询
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);
        if (roleList.isEmpty()){
            RoleServiceImplLogger.info("获取到的roleList数据为空");
        }

        return new PageInfo<>(roleList);
    }


    /**
     *  保存角色
     * @param role
     * @return
     */
    @Override
    public Boolean saveRole(Role role) {
        int insert = roleMapper.insert(role);
        if (insert != 1){
            return false;
        }
        return true;
    }

    /**
     *  更新角色
     * @param role
     * @return
     */
    @Override
    public Boolean updateRole(Role role) {
        int i = roleMapper.updateByPrimaryKey(role);
        if (i!=1){
            return false;
        }
        return true;
    }

    /**
     *  删除角色
     * @param roleIdList
     * @return
     */
    @Override
    public Boolean removeRole(List<Integer> roleIdList) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(roleIdList);


        int i = roleMapper.deleteByExample(roleExample);
        if (i<1){
            return false;
        }
        return true;
    }

    /**
     *  获取已分配的角色
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        List<Role> roleList = roleMapper.selectAssignedRole(adminId);
        RoleServiceImplLogger.debug("已分配的数据",roleList);
        return roleList;
    }

    /**
     *  获取未分配的角色
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getUnAssignedRole(Integer adminId) {
        List<Role> unAsxsignedRole = roleMapper.selectUnAsxsignedRole(adminId);
        RoleServiceImplLogger.debug("未分配的角色",unAsxsignedRole);
        return unAsxsignedRole;
    }


    /**
     *  给管理员分配权限
     * @param adminId
     * @param roleIdList
     * @return
     */
    @Override
    public Boolean saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        RoleServiceImplLogger.debug("roleIdList",roleIdList.toString());
        int deleteAdmin = roleMapper.deleteOLdRelationship(adminId);


        if (roleIdList!=null && roleIdList.size()>0){
            int insertNewRelationship = roleMapper.insertNewRelationship(adminId,roleIdList);
            if (insertNewRelationship<=0){
                RoleServiceImplLogger.info("给管理员分配权限失败");
            }
        }
        return true;

    }
}
