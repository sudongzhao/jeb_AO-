package com.sudongzhao.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudongzhao.server.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
@Mapper
@Repository
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartments(Integer parentId);

    /**
     * 添加部门
     * @param department
     */
    void addDep(Department department);

    /**
     * 删除部门
     * @param department
     */
    void deleteDep(Department department);

}
