package com.sudongzhao.server.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sudongzhao.server.mapper.DepartmentMapper;
import com.sudongzhao.server.pojo.Department;
import com.sudongzhao.server.pojo.ResponseBean;
import com.sudongzhao.server.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 获取所有部门
     * @return
     */
    @Override
    public List<Department> getAllDepartment() {
        return departmentMapper.getAllDepartments(-1);
    }

    /**
     * 添加部门
     * @param department
     * @return
     */
    @Override
    public ResponseBean addDepartment(Department department) {
        department.setEnabled(true);
        departmentMapper.addDep(department);
        if(1==department.getResult()){
            department = departmentMapper.selectById(department.getId());
            return ResponseBean.success("添加成功！",department);
        }
        return ResponseBean.error("添加失败！");
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @Override
    public ResponseBean deleteDepartment(Integer id) {

        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDep(department);
        if(-2==department.getResult()){
            return ResponseBean.error("该部门还有子部门，删除失败");
        }
        if(-1==department.getResult()){
            return ResponseBean.error("该部门下还有员工，删除失败");
        }
        if(1==department.getResult()){
            return ResponseBean.success("删除成功");
        }
        return ResponseBean.error("删除失败");
    }
}
