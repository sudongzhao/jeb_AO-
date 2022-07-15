package com.sudongzhao.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sudongzhao.server.pojo.MenuRole;
import com.sudongzhao.server.pojo.ResponseBean;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * 更新菜单
     */
    ResponseBean updateMenuRole(Integer rid, Integer[] mids);
}
