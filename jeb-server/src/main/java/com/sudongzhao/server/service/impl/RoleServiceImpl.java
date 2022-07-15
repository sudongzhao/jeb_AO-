package com.sudongzhao.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sudongzhao.server.mapper.RoleMapper;
import com.sudongzhao.server.pojo.Role;
import com.sudongzhao.server.service.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
