package com.sudongzhao.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sudongzhao.server.pojo.Menu;
import com.sudongzhao.server.pojo.ResponseBean;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getMenusByAdminId();

    /**
     * 根据角色获取菜单列表
     */
    List<Menu> getMenusWithRole();

    List<Menu> getAllMenus();


}
