package com.sudongzhao.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("t_salary")
@ApiModel(value="Salary对象", description="")
public class Salary implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    @Excel(name = "工资")
    private String name;

    @ApiModelProperty(value = "基本工资")
    @TableField("basic_salary")
    private Double basicSalary;

    @ApiModelProperty(value = "奖金")
    private Double bonus;

    @ApiModelProperty(value = "午餐补助")
    @TableField("lunch_salary")
    private Double lunchSalary;

    @ApiModelProperty(value = "交通补助")
    @TableField("traffic_salary")
    private Double trafficSalary;

    @ApiModelProperty(value = "应发工资")
    @TableField("all_salary")
    private Double allSalary;

    @ApiModelProperty(value = "养老基金数")
    @TableField("pension_base")
    private Double pensionBase;

    @ApiModelProperty(value = "养老金比率")
    @TableField("pension_per")
    private Double pensionPer;

    @ApiModelProperty(value = "启用时间")
    @TableField("create_date")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "医疗基数")
    @TableField("medical_base")
    private Integer medicalBase;

    @ApiModelProperty(value = "医疗保险比率")
    @TableField("medical_per")
    private Float medicalPer;

    @ApiModelProperty(value = "公积金基数")
    @TableField("accumulation_fund_base")
    private Integer accumulationFundBase;

    @ApiModelProperty(value = "公积金比率")
    @TableField("accumulation_fund_per")
    private Float accumulationFundPer;


}
