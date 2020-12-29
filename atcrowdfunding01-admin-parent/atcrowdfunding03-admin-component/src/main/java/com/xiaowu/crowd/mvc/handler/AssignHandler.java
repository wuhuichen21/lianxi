package com.xiaowu.crowd.mvc.handler;

import com.xiaowu.crowd.entity.po.Auth;
import com.xiaowu.crowd.entity.po.Role;
import com.xiaowu.crowd.service.AuthService;
import com.xiaowu.crowd.service.RoleService;
import com.xiaowu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {
    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;


    /**
     *  获取角色分配状态
     * @return
     */
    @RequestMapping(value = "/assign/to/assign/role/page.html",method = RequestMethod.GET)
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId,
                                   ModelMap modelMap
    ){
//        查询已经分配的角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);

//        查询未分配的角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);

//        放入modelMap
        modelMap.addAttribute("assignedRoleList",assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList",unAssignedRoleList);
        return "assign-role";
    }


    /**
     *  给管理员分配角色
     * @param adminId
     * @param pageNum
     * @param keyword
     * @param roleIdList
     * @return
     */
    @RequestMapping(value = "/assign/do/role/assign.html",method = RequestMethod.POST)
    public String saveAdminRoleRelationship(@RequestParam("adminId")Integer adminId,
                                            @RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("keyword") String keyword,
                                            @RequestParam(value = "roleIdList",required = false) List<Integer> roleIdList
                                            ){

        Boolean adminRoleRelationship = roleService.saveAdminRoleRelationship(adminId, roleIdList);

        return "redirect:/admin/page.html?pageNum="+pageNum+"&keyword="+keyword;


    }

    /**
     *  获取所有的auth
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/assgin/get/all/auth.json",method = RequestMethod.GET)
    public ResultEntity<List<Auth>>  getAllAuth(){
        List<Auth> auth = authService.getAllAuth();
        return ResultEntity.successWithData(auth);
    }

    /**
     *  根据roleid获取已经选择的auth
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/assign/get/assigned/auth/id/by/role/id.json",method = RequestMethod.POST)
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(@RequestParam("roleId") Integer roleId){
        List<Integer> authIdByRoleId = authService.getAssignedAuthIdByRoleId(roleId);
        if ( authIdByRoleId!=null && authIdByRoleId.size()>0 ){
            return ResultEntity.successWithData(authIdByRoleId);
        }
        return ResultEntity.failed("获取信息为空");
    }


    /**
     *  给角色role分配权限
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/assign/do/role/assign/auth.json",method = RequestMethod.POST)
    public ResultEntity<String> saveRoleAuthRelathinship(@RequestBody Map<String,List<Integer>> map){
        Boolean saveRoleAuthRelathinship = authService.saveRoleAuthRelathinship(map);
        if (saveRoleAuthRelathinship){
            return ResultEntity.successWithoutData();
        }
        return ResultEntity.failed("保存失败");
    }
}
