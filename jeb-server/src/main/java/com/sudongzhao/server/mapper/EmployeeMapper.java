package com.sudongzhao.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sudongzhao.server.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */

public interface EmployeeMapper extends BaseMapper<Employee> {


    IPage<Employee> getEmployeeByPage(Page<Employee> page,
                                      @Param("employee") Employee employee,
                                      @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
     * 查询员工
     * @return
     */
    List<Employee> getEmployee(Integer eid);
}
