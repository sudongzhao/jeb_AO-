package com.sudongzhao.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBean {
    //状态码
    private long code;
    //提示信息
    private String message;
    //返回的对象
    private Object obj;

    public static ResponseBean success(String message){
        return new ResponseBean(200,message,null);
    }

    public static ResponseBean success(String message,Object obj){
        return new ResponseBean(200,message,obj);
    }

    public static ResponseBean error(String message){
        return new ResponseBean(500,message,null);
    }

    public static ResponseBean error(String message,Object obj){
        return new ResponseBean(500,message,obj);
    }
}
