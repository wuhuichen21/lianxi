package com.xiaowu.crowd.service;

import com.github.pagehelper.PageInfo;
import com.xiaowu.crowd.entity.po.Admin;

import java.util.List;

public interface AdminService {
    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getAdminPage(String keyword,Integer pageNum,Integer pageSize);

    Boolean removeAdmin(Integer adminId);

    Admin getAdminById(Integer adminId);

    Boolean updateAdmin(Admin admin);

    Admin selectAdminByLoginAcct(String username);

}
