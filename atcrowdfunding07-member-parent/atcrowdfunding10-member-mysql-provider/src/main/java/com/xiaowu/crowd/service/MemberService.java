package com.xiaowu.crowd.service;

import com.xiaowu.crowd.entity.po.MemberPO;

public interface MemberService {
    MemberPO getMemberPOByLoginAcct(String loginacct);
}
