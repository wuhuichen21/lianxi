package com.xiaowu.crowd.handler;

import com.xiaowu.crowd.entity.po.MemberPO;
import com.xiaowu.crowd.service.MemberService;
import com.xiaowu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberProviderHandler {
    @Autowired
    private MemberService memberService;

    /**
     * 根据用户名获取MemberPO
     * @param loginacct
     * @return
     */
    @RequestMapping(value = "/get/memberpo/by/login/acct/remote",method = RequestMethod.GET)
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct){
       try {
           MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginacct);
           return ResultEntity.successWithData(memberPO);
       }catch (Exception e){
           e.printStackTrace();

           // 3.如果捕获到异常则返回失败的结果
         return ResultEntity.failed(e.getMessage());
       }
    }

}
