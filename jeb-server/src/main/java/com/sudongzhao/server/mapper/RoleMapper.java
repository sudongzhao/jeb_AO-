package com.sudongzhao.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudongzhao.server.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
public interface RoleMapper extends BaseMapper<Role> {

    public List<Role> getRoles(Integer adminId);
}
