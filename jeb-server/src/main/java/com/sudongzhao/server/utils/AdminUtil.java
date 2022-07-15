package com.sudongzhao.server.utils;

import com.sudongzhao.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 获取当前用户
 */
public class AdminUtil {
    public static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
