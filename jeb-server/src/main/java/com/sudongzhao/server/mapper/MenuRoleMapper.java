package com.sudongzhao.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudongzhao.server.pojo.MenuRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    Integer insertRecord(Integer rid, Integer[] mids);
}
