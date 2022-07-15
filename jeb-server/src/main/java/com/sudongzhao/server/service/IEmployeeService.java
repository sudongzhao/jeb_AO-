package com.sudongzhao.server.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sudongzhao.server.pojo.Employee;
import com.sudongzhao.server.pojo.RPageBean;
import com.sudongzhao.server.pojo.ResponseBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
public interface IEmployeeService extends IService<Employee> {

    RPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
     *
     * @return
     */
    ResponseBean getMaxWorkId();

    ResponseBean addEmp(Employee employee);

    List<Employee> getEmployee(Integer eid);
}
