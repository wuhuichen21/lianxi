package com.xiaowu.crowd.mapper;

import com.xiaowu.crowd.entity.po.Role;
import com.xiaowu.crowd.entity.po.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRoleByKeyword(String keyword);

    List<Role> selectAssignedRole(Integer adminId);

    List<Role> selectUnAsxsignedRole(Integer adminId);

    int insertNewRelationship(@Param("adminId") Integer adminId,@Param("roleIdList") List<Integer> roleIdList);

    int deleteOLdRelationship(Integer adminId);
}