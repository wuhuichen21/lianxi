package com.xiaowu.crowd.mvc.config;

import com.xiaowu.crowd.entity.po.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * User对象中仅仅包含账号和密码，为了能获取到原始的admin对象，专门创建来扩展User
 */
public class SecurityAdmin extends User {

    private static final long serialVersionUID = 1l;

    //原始的admin对象
    private Admin originalAdmin;

    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities){
            super(originalAdmin.getLoginAcct(),originalAdmin.getUserPswd(),authorities);

        // 给本类的this.originalAdmin赋值
        this.originalAdmin = originalAdmin;
        this.originalAdmin.setUserPswd(null);
    }

    public Admin getOriginalAdmin(){
        return originalAdmin;
    }

}
