package com.xiaowu.crowd.service.impl;

import com.xiaowu.crowd.entity.po.MemberPO;
import com.xiaowu.crowd.entity.po.MemberPOExample;
import com.xiaowu.crowd.mapper.MemberPOMapper;
import com.xiaowu.crowd.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberPOMapper memberPOMapper;

    /**
     * 根据用户名获取用户对象
     *
     * @param loginacct
     * @return
     */
    @Override
    public MemberPO getMemberPOByLoginAcct(String loginacct) {
        MemberPOExample memberPOExample = new MemberPOExample();
        MemberPOExample.Criteria criteria = memberPOExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginacct);

        List<MemberPO> memberPOS = memberPOMapper.selectByExample(memberPOExample);
        return memberPOS.get(0);
    }
}
