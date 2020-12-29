package com.xiaowu.crowd;

import com.xiaowu.crowd.entity.po.Admin;
import com.xiaowu.crowd.mapper.AdminMapper;
import com.xiaowu.crowd.service.AdminService;
import com.xiaowu.crowd.service.impl.AdminServiceImpl;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class test11 {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;
    @Test
    public void test() throws SQLException {
        Logger logger = LoggerFactory.getLogger(test11.class);
        Connection connection = dataSource.getConnection();
        Admin admin = new Admin(null, "jerry", "123456", "杰瑞", "jerry@qq.com", null);
        adminMapper.insert(admin);

        logger.debug(admin.toString());
    }

    @Test
    public void setAdminService(){
        Admin admin = new Admin();
        admin.setUserName("傅大便");
        adminService.saveAdmin(admin);
    }

    @Test
    public void ass(){
            for(int i = 0; i < 238; i++) {
                adminMapper.insert(new Admin(null, "loginAcct"+i, "userPswd"+i, "userName"+i, "email"+i, null));
            }
        }
}
