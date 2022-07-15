package com.sudongzhao.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sudongzhao.server.config.security.JwtTokenUtil;
import com.sudongzhao.server.mapper.AdminMapper;
import com.sudongzhao.server.mapper.RoleMapper;
import com.sudongzhao.server.pojo.Admin;
import com.sudongzhao.server.pojo.ResponseBean;
import com.sudongzhao.server.pojo.Role;
import com.sudongzhao.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userds;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private String tokenHead="Bearer";

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public ResponseBean login(String username, String password, String code, HttpServletRequest request) {
        //获取验证码
        //通过security方法使用用户名登录
        UserDetails userDetails = userds.loadUserByUsername(username);
        //没有token或者密码不正确
        if (null==userDetails||!passwordEncoder.matches(password,userDetails.getPassword())){
            return ResponseBean.error("用户名或密码不正确！");
        }
        if (!userDetails.isEnabled()){
            return ResponseBean.error("账号被禁用，请联系管理员！");
        }

        //更新security登录对象，三个参数分别为实体，密码，权限列表
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        //放在上下文中

        //获取令牌
        String token = jwtTokenUtil.generateToken(userDetails);
        HashMap<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        //创建map放头部信息和请求头
        return ResponseBean.success("登录成功",tokenMap);
    }

    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",true));
    }

    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }
}
