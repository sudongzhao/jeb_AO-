package com.sudongzhao.server.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.sudongzhao.server.pojo.Department;
import com.sudongzhao.server.pojo.Employee;
import com.sudongzhao.server.pojo.ResponseBean;
import com.sudongzhao.server.service.IDepartmentService;
import com.sudongzhao.server.service.IEmployeeService;
import com.sudongzhao.server.service.impl.EmployeeServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/")
    private List<Department> getAllDepartment(){
        return departmentService.getAllDepartment();
    }


    @ApiOperation(value ="添加部门")
    @PostMapping("/")
    private ResponseBean addDepartment(@RequestBody Department department){
        return departmentService.addDepartment(department);
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/{id}")
    private ResponseBean deleteDepartment(@PathVariable Integer id){
        return departmentService.deleteDepartment(id);
    }

}
