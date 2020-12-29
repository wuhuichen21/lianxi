package com.xiaowu.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.xiaowu.crowd.constant.CrowdConstant;
import com.xiaowu.crowd.entity.po.Admin;
import com.xiaowu.crowd.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

//    登录
    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct")
                         String loginAcct,
                          @RequestParam("userPswd") String userPswd,
                          HttpSession session
                          ){
//        调用service
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);
        return "redirect:/admin/to/main/page.html";
    }
//  退出登录
    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session){

        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

//    分页显示管理员信息部分
    @RequestMapping("/admin/page.html")
    public String getAdminPage(@RequestParam(value = "keyword",defaultValue = "") String keyword,
                               @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                               ModelMap modelMap
    ){

        PageInfo<Admin> adminPage = adminService.getAdminPage(keyword, pageNum, pageSize);

        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,adminPage);

        return "admin-page";
    }

//    新增管理员
    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/save/admin.html")
    public String saveAdmin(Admin admin){
        adminService.saveAdmin(admin);

        return "redirect:/admin/page.html?pageNum="+Integer.MAX_VALUE;
    }

//   删除管理员
    @RequestMapping("admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String removeAdmin(@PathVariable("adminId") Integer adminId,
                              @PathVariable("pageNum") Integer pageNum,
                              @PathVariable("keyword") String keyword
                              ){
        Boolean removeAdmin = adminService.removeAdmin(adminId);
        if (removeAdmin){
            return "redirect:/admin/page.html?pageNum="+pageNum+"&keyword="+keyword;
        }
        return null;
    }

//    更新对象的时候反显对象信息
    @RequestMapping("/admin/to/edit/page.html")
    public String getAdminById(@RequestParam("adminId") Integer adminId,
                               ModelMap modelMap){
        Admin admin = adminService.getAdminById(adminId);

        if (StringUtils.isEmpty(admin)){
            return null;
        }
        modelMap.addAttribute("admin",admin);
        return "admin-edit";
    }

    /**
     *  更新管理员
     * @param admin
     * @return
     */
    @RequestMapping(value = "/admin/update.html",method = RequestMethod.POST)
    public String updateAdmin(Admin admin,
                              @RequestParam("pageNum") Integer pageNum,
                              @RequestParam("keyword") String keyword
                              ){
        Boolean updateAdmin = adminService.updateAdmin(admin);
        if (updateAdmin){
            return "redirect:/admin/page.html?pageNum="+pageNum+"&keyword="+keyword;
        }
        return null;
    }

}
