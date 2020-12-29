package com.xiaowu.crowd.service.impl;

import com.xiaowu.crowd.entity.po.Menu;
import com.xiaowu.crowd.entity.po.MenuExample;
import com.xiaowu.crowd.mapper.MenuMapper;
import com.xiaowu.crowd.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取所有菜单数据
     *
     * @return
     */
    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }


    /**
     * 添加菜单
     *
     * @param menu
     * @return
     */
    @Override
    public Boolean saveMenu(Menu menu) {
        int insert = menuMapper.insert(menu);
        if (insert < 1) {
            return false;
        }
        return true;
    }


    /**
     * 更新菜单
     *
     * @param menu
     * @return
     */
    @Override
    public Boolean updateMenu(Menu menu) {
        int i = menuMapper.updateByPrimaryKeySelective(menu);
        if (i < 1) {
            return false;
        }
        return true;
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @Override
    public Boolean removeMenu(Integer id) {
        int i = menuMapper.deleteByPrimaryKey(id);
        if (i < 1) {
            return false;
        }
        return true;
    }
}
