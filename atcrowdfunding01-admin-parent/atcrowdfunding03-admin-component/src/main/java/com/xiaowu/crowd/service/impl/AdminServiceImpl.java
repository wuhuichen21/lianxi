package com.xiaowu.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaowu.crowd.constant.CrowdConstant;
import com.xiaowu.crowd.entity.po.Admin;
import com.xiaowu.crowd.entity.po.AdminExample;
import com.xiaowu.crowd.exception.LoginAcctAlreadyInUseException;
import com.xiaowu.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.xiaowu.crowd.exception.LoginFailedException;
import com.xiaowu.crowd.mapper.AdminMapper;
import com.xiaowu.crowd.service.AdminService;
import com.xiaowu.crowd.util.CrowdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

   private Logger Adminlogger = LoggerFactory.getLogger(AdminService.class);

    @Override
    public void saveAdmin(Admin admin) {
//    生成当前时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        admin.setCreateTime(createTime);

//      对密码进行加密
        String userPswd = admin.getUserPswd();
        String encode = bCryptPasswordEncoder.encode(userPswd);
//        String encoded = CrowdUtil.md5(userPswd);
        admin.setUserPswd(encode);

//        执行保存
        try {
            adminMapper.insert(admin);
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                Adminlogger.debug("账号已存在异常");
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
            throw e;
        }

    }


    //SMM测试方法，查出所有的数据
    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }



//    登录
    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
//        根据登录账号查询Admin对象

        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);

//        判断admin对象是否为null等异常情况
        if (admins.isEmpty() || admins.size() == 0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        if (admins.size() > 1){
            throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        Admin admin = admins.get(0);
        if (admin == null){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
//        如果Admin对象不为Null则将数据库密码从admin中取出来做比较
        String adminUserPswd = admin.getUserPswd();

        String md5 = CrowdUtil.md5(userPswd);
        if (!Objects.equals(adminUserPswd,md5)){
            // 7.如果比较结果是不一致则抛出异常 throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
//      如果一直则返回admin对象
        return admin;
    }


//    分页查询
    @Override
    public PageInfo<Admin> getAdminPage(String keyword, Integer pageNum, Integer pageSize) {
//        开启分页功能
        PageHelper.startPage(pageNum,pageSize);

//        查询Admin数据
        List<Admin> admins = adminMapper.selectAdminListByKeyword(keyword);

        Logger logger = LoggerFactory.getLogger(AdminService.class);

        logger.debug("adminList 的全类名是："+admins.getClass().getName());

        if (admins.isEmpty()){
            return null;
        }

        // ※辅助代码：打印 adminList 的全类名

        PageInfo<Admin> adminPageInfo = new PageInfo<>(admins);

        return adminPageInfo;
    }

    /**
     *  删除管理员
     * @param adminId
     * @return
     */
    @Override
    public Boolean removeAdmin(Integer adminId) {
        int i = adminMapper.deleteByPrimaryKey(adminId);
        Adminlogger.info("删除返回值"+i);
        if (i<=0){
            return false;
        }

        return true;
    }

    /**
     *  根据Id获取到管理员对象信息
     * @param adminId
     * @return
     */
    @Override
    public Admin getAdminById(Integer adminId) {
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        if (StringUtils.isEmpty(admin)){
            return null;
        }
        return admin;
    }


    /**
     *  更新信息对象
     * @param admin
     * @return
     */
    @Override
    public Boolean updateAdmin(Admin admin) {
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
        return true;
    }


    /**
     *  根据用户名查询到admin对象
     * @param username
     * @return
     */
    @Override
    public Admin selectAdminByLoginAcct(String username) {
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andLoginAcctEqualTo(username);

        List<Admin> admins = adminMapper.selectByExample(adminExample);
        Admin admin = admins.get(0);
        return admin;
    }
}
