package com.sudongzhao.server.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sudongzhao.server.pojo.Department;
import com.sudongzhao.server.pojo.ResponseBean;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartment();

    /**
     * 添加部门
     * @param department
     * @return
     */
    ResponseBean addDepartment(Department department);

    /**
     * 删除部门
     * @param id
     * @return
     */
    ResponseBean deleteDepartment(Integer id);

}
