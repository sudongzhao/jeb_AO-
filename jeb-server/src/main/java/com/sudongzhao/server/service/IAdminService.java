package com.sudongzhao.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sudongzhao.server.pojo.Admin;
import com.sudongzhao.server.pojo.ResponseBean;
import com.sudongzhao.server.pojo.Role;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
public interface IAdminService extends IService<Admin> {

    ResponseBean login(String username, String password, String code, HttpServletRequest request);

    Admin getAdminByUserName(String username);

    /**
     * 根据用户id查询角色列表
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);

}
