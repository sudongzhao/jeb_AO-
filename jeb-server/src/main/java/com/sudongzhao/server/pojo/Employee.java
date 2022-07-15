package com.sudongzhao.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.bytebuddy.description.NamedElement;

import java.io.Serializable;
import java.time.LocalDate;
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
@TableName("t_employee")
@ApiModel(value="Employee对象", description="")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "姓名")
    @Excel(name = "员工姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    @Excel(name = "性别")
    private String gender;

    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "出生日期",width = 26)
    private LocalDate birthday;

    @ApiModelProperty(value = "身份证号")
    @TableField("idCard")
    @Excel(name = "身份证",width = 30)
    private String idCard;

    @ApiModelProperty(value = "婚姻状况")
    @Excel(name = "婚姻状况")
    private String wedlock;

    @ApiModelProperty(value = "民族")
    @TableField("nationId")
    @Excel(name = "民族")
    private Integer nationId;

    @ApiModelProperty(value = "籍贯")
    @TableField("nativePlace")
    private String nationPlace;

    @ApiModelProperty(value = "政治面貌")
    @TableField("politicId")
    @Excel(name = "政治面貌")
    private Integer politicId;

    @ApiModelProperty(value = "邮箱")
    @Excel(name = "邮箱",width = 30)
    private String email;

    @ApiModelProperty(value = "手机")
    @Excel(name = "电话号码",width = 15)
    private String phone;

    @ApiModelProperty(value = "地址")
    @Excel(name = "联系地址",width = 40)
    private String address;

    @ApiModelProperty(value = "部门编号")
    @TableField("departmentId")
    private Integer departmentId;

    @ApiModelProperty(value = "职称id")
    @TableField("jobLevelId")
    private Integer jobLevelId;

    @ApiModelProperty(value = "职位id")
    @TableField("posId")
    private Integer posId;

    @ApiModelProperty(value = "聘用形式")
    @TableField("engageForm")
    @Excel(name = "聘用方式")
    private String engageForm;

    @ApiModelProperty(value = "最高学历")
    @TableField("tiptopDegree")
    @Excel(name = "最高学历")
    private String tiptopDegree;

    @ApiModelProperty(value = "所属专业")
    @Excel(name = "所属专业")
    private String specialty;

    @ApiModelProperty(value = "毕业院校")
    @Excel(name = "毕业院校")
    private String school;

    @ApiModelProperty(value = "入职日期")
    @TableField("beginDate")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "入职日期",width = 20,format = "yyyy-MM-dd")
    private LocalDate beginDate;

    @ApiModelProperty(value = "在职状态")
    @TableField("workState")
    @Excel(name = "在职状态")
    private String workState;

    @ApiModelProperty(value = "工号")
    @TableField("workID")
    @Excel(name = "工号")
    private String workId;

    @ApiModelProperty(value = "合同期限")
    @TableField("contractTerm")
    @Excel(name = "合同期限",suffix = "年")
    private Double contractTerm;

    @ApiModelProperty(value = "转正日期")
    @TableField("conversionTime")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "转正日期",format = "yyyy-MM-dd",width = 20)
    private LocalDate conversionTime;

    @ApiModelProperty(value = "离职日期")
    @TableField("notWorkDate")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "离职日期",format = "yyyy-MM-dd")
    private LocalDate notWorkTract;

    @ApiModelProperty(value = "合同起始日期")
    @TableField("beginContract")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "合同终止日期",format = "yyyy-MM-dd",width = 20)
    private LocalDate beginContract;

    @ApiModelProperty(value = "合同终止日期")
    @TableField("endContract")
    private LocalDate endContract;

    @ApiModelProperty(value = "工龄")
    @TableField("workAge")
    private Integer workAge;

    @ApiModelProperty(value = "工资账套id")
    @TableField("salaryId")
    private Integer salaryId;

    @ApiModelProperty(value = "民族")
    @TableField(exist = false)
    @ExcelEntity(name = "民族")
    private Nation nation;

    @ApiModelProperty(value = "政治面貌")
    @TableField(exist = false)
    @ExcelEntity(name = "政治面貌")
    private PoliticsStatus politicsStatus;

    @ApiModelProperty(value = "部门")
    @TableField(exist = false)
    @ExcelEntity(name = "部门")
    private Department department;

    @ApiModelProperty(value = "职称")
    @TableField(exist = false)
    @ExcelEntity(name = "职称")
    private Joblevel joblevel;

    @ApiModelProperty(value = "职位")
    @TableField(exist = false)
    @ExcelEntity(name = "职位")
    private Position position;

    @ApiModelProperty(value = "工资账套")
    @TableField(exist = false)
    @ExcelEntity(name = "工资账套")
    private Salary salary;

}
