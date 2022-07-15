package com.sudongzhao.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudongzhao.server.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */

public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 通过用户id查询菜单列表
     */
    List<Menu> getMenusByAdminId(Integer id);

    List<Menu> getMenusWithRole();

    List<Menu> getAllMenus();


}
