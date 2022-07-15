package com.sudongzhao.server.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudongzhao.server.pojo.ResponseBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当未登录或者token失效的时候的自定义返回结果
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //编码格式
        response.setCharacterEncoding("UTF-8");
        //返回类型
        response.setContentType("application/json");
        //获取输出流
        PrintWriter out = response.getWriter();
        ResponseBean error = ResponseBean.error("未登录");
        error.setCode(401);
        out.write(new ObjectMapper().writeValueAsString(error));
        out.flush();
        out.close();
    }
}
