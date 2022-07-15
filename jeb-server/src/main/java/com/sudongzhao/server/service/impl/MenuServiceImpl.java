package com.sudongzhao.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sudongzhao.server.mapper.MenuMapper;
import com.sudongzhao.server.mapper.MenuRoleMapper;
import com.sudongzhao.server.pojo.Menu;
import com.sudongzhao.server.pojo.MenuRole;
import com.sudongzhao.server.pojo.ResponseBean;
import com.sudongzhao.server.service.IMenuService;
import com.sudongzhao.server.utils.AdminUtil;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Override
    public List<Menu> getMenusByAdminId() {
        Integer adminId = AdminUtil.getCurrentAdmin().getId();
        ValueOperations<String,Object> redisUtils = redisTemplate.opsForValue();
        //从redis中获取菜单数据
        List<Menu> menus=((List<Menu>) redisUtils.get("menu_" + adminId));
        if (Collections.isEmpty(menus)){
            menus = menuMapper.getMenusByAdminId(adminId);
            redisUtils.set("menu_"+adminId,menus);
        }
        return menus;
    }

    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }


}
