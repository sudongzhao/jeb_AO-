package com.sudongzhao.server.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_log")
@ApiModel(value="SysLog对象", description="")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "操作用户")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "类名")
    @TableField("class_name")
    private String className;

    @ApiModelProperty(value = "方法名")
    private String method;

    @ApiModelProperty(value = "方法参数")
    private String param;

    @ApiModelProperty(value = "访问时间")
    @TableField("visit_time")
    private LocalDateTime visitTime;

    @ApiModelProperty(value = "操作时长")
    @TableField("execution_time")
    private Long executionTime;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "操作系统及浏览器信息")
    @TableField("os_and_browser")
    private String osAndBrowser;

    @ApiModelProperty(value = "session id")
    @TableField("session_id")
    private String sessionId;

    @ApiModelProperty(value = "请求方式")
    @TableField("req_method")
    private String reqMethod;

    @ApiModelProperty(value = "是否是Ajax请求")
    @TableField("is_ajax")
    private String isAjax;

    @ApiModelProperty(value = "操作用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "方法返回结果")
    private String result;

    @ApiModelProperty(value = "异常信息")
    private String exception;


}
