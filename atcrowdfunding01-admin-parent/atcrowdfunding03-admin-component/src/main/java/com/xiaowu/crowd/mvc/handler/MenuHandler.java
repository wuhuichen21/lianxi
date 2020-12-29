package com.xiaowu.crowd.mvc.handler;

import com.xiaowu.crowd.entity.po.Menu;
import com.xiaowu.crowd.service.MenuService;
import com.xiaowu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class MenuHandler {
    @Autowired
    private MenuService menuService;


    /**
     * 获取所有菜单信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/menu/get/whole/tree.json", method = RequestMethod.GET)
    public ResultEntity<Menu> getWholeTreeNew() {
//        1,查询全部的Menu对象
        List<Menu> menuList = menuService.getAll();

//        2,声明一个变量用来存储找到的根节点
        Menu root = null;

//        3,创建Map对象用来存储id和Menu对象的对应关系便于查找父节点
        HashMap<Integer, Menu> menuMap = new HashMap<>();

//        4,遍历menuList填充 menuMap
        for (Menu menu : menuList) {
            Integer id = menu.getId();
            menuMap.put(id, menu);
        }
//        5,再次遍历menuList查找根节点，组装父节点
        for (Menu menu : menuList) {
//            6,获取当前menu对象的pid属性值
            Integer pid = menu.getPid();
//            7,如果Pid为null，则这个节点是父节点
            if (pid == null) {
                root = menu;
//            8,如果当前节点是根节点，那么肯定没有父节点，不必继续执行
                continue;
            }
//          9,如果Pid不为Null,说明当前节点有父节点，那么可以根据pid到menuMap中查找到对应的Menu对象
            Menu father = menuMap.get(pid);
//          10,将当前节点存入父节点的children集合
            List<Menu> children = father.getChildren();
            children.add(menu);
        }
        return ResultEntity.successWithData(root);
    }


    /**
     * 添加菜单
     *
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/menu/save.json", method = RequestMethod.POST)
    public ResultEntity<String> saveMenu(Menu menu) {
        Boolean saveMenu = menuService.saveMenu(menu);
        if (saveMenu) {
            return ResultEntity.successWithoutData();
        }
        return ResultEntity.failed("添加失败");
    }


    /**
     * 更新菜单
     *
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/menu/update.json", method = RequestMethod.POST)
    public ResultEntity<String> updateMenu(Menu menu) {
        Boolean updateMenu = menuService.updateMenu(menu);
        if (updateMenu) {
            return ResultEntity.successWithoutData();
        }
        return ResultEntity.failed("更新失败");
    }


    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/menu/remove.json", method = RequestMethod.POST)
    public ResultEntity<String> removeMenu(@RequestParam("id") Integer id) {
        Boolean removeMenu = menuService.removeMenu(id);
        if (removeMenu) {
            return ResultEntity.successWithoutData();
        }
        return ResultEntity.failed("删除失败!");
    }
}
