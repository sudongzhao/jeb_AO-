package com.sudongzhao.server.execption;

import com.sudongzhao.server.pojo.ResponseBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.ws.Response;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(SQLException.class)
    public ResponseBean mySqlException(SQLException e){
        if(e instanceof SQLIntegrityConstraintViolationException){
            return ResponseBean.error("该数据有关联数据，操作失败");
        }
        return ResponseBean.error("数据库异常，操作失败");
    }
}
