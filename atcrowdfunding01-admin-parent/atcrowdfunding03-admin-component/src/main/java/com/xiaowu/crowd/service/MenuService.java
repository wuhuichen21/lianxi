package com.xiaowu.crowd.service;

import com.xiaowu.crowd.entity.po.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getAll();

    Boolean saveMenu(Menu menu);

    Boolean updateMenu(Menu menu);

    Boolean removeMenu(Integer id);
}
