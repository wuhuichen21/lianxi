package com.xiaowu.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.xiaowu.crowd.entity.po.Role;
import com.xiaowu.crowd.service.RoleService;
import com.xiaowu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    /**
     *  角色分页查询
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    @PreAuthorize("hasRole('部长')")
    @ResponseBody
    @RequestMapping(value = "/role/get/page/info.json",method = RequestMethod.POST)
    public ResultEntity<PageInfo<Role>> getPageInfo(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                                    @RequestParam(value = "keyword") String keyword
                                                    ){
//      调用分service方法获取分页数据
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(pageInfo);
    }

    /**
     *  保存角色
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/save.json",method = RequestMethod.POST)
    public ResultEntity<String> saveRole(Role role){
        Boolean saveRole = roleService.saveRole(role);
        if (saveRole){
            return ResultEntity.successWithoutData();
        }
        return ResultEntity.failed("添加失败");
    }

    /**
     *  更新角色
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/update.json")
    public ResultEntity<String> updateRole(Role role){
        Boolean updateRole = roleService.updateRole(role);
        if (updateRole){
            return ResultEntity.successWithoutData();
        }
        return ResultEntity.failed("更新失败");
    }


    /**
     *  删除角色
     * @param roleIdList
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "role/remove/by/role/id/array.json",method = RequestMethod.POST)
    public ResultEntity<String> removeRole(@RequestBody List<Integer> roleIdList){
        Boolean removeRole = roleService.removeRole(roleIdList);
        if (removeRole){
            return ResultEntity.successWithoutData();
        }
        return ResultEntity.failed("删除失败");
    }
}
