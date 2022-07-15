package com.sudongzhao.server.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt的登录过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private String tokenHeader="Authorization";
    private String tokenHead="Bearer";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.tokenHeader);
        //存在token
        if (null != authHeader && authHeader.startsWith(this.tokenHead)) {
            String authToken = authHeader.substring(this.tokenHead.length());
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            //token中存在用户名但未登录
            if (null!=username&&
                    null==SecurityContextHolder.getContext().getAuthentication()){
                //登录
                UserDetails userDetails =
                        this.userDetailsService.loadUserByUsername(username);
                //验证token是否有效，重新设置用户对象
                if (jwtTokenUtil.validateToken(authToken,userDetails)){
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    null, userDetails.getAuthorities());
                    authentication.setDetails(new
                            WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request,response);
    }

}
