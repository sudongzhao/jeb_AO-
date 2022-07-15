package com.sudongzhao.server.controller;

import com.sudongzhao.server.pojo.Admin;
import com.sudongzhao.server.pojo.AdminLoginParam;
import com.sudongzhao.server.pojo.ResponseBean;
import com.sudongzhao.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@Api(tags = "LoginController")
public class LoginController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("login")
    public ResponseBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(captcha)||(!captcha.equalsIgnoreCase(adminLoginParam.getCode()))){
            return ResponseBean.error("验证码输入错误！请重新输入！");
        }
        return adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);
    }

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if (null==principal){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        admin.setRoles(adminService.getRoles(admin.getId()));
        admin.setPassword(null);
        return admin;
    }

    @ApiOperation(value = "退出登录")
    @PostMapping
    public ResponseBean logout(){
        return ResponseBean.success("注销成功！");
    }
}
