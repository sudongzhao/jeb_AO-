package com.sudongzhao.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sudongzhao.server.mapper.MenuRoleMapper;
import com.sudongzhao.server.pojo.MenuRole;
import com.sudongzhao.server.pojo.ResponseBean;
import com.sudongzhao.server.service.IMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Override
    public ResponseBean updateMenuRole(Integer rid, Integer[] mids) {
        //根据id删除所有
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid",rid));
        if (null==mids||0==mids.length){
            return ResponseBean.success("更新成功");
        }
        //根据id更新，在sql里面进行更新
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        if (result==mids.length){
            return ResponseBean.success("更新成功");
        }
        return ResponseBean.error("更新失败");

    }
}
