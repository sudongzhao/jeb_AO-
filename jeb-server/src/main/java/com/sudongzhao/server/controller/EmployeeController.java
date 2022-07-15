package com.sudongzhao.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportExcelItem;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.baomidou.mybatisplus.extension.api.R;
import com.sudongzhao.server.pojo.*;
import com.sudongzhao.server.service.*;
import com.sudongzhao.server.service.impl.EmployeeServiceImpl;
import io.swagger.annotations.ApiOperation;
import javafx.scene.paint.ImagePattern;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
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
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private INationService nationService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IJoblevelService joblevelService;

    @ApiOperation(value = "获取所有员工列表")
    @GetMapping("/")
    public RPageBean getAllEmployees(@RequestParam(defaultValue = "1")Integer currentPage,
                                     @RequestParam(defaultValue = "10")Integer size,
                                     Employee employee,
                                     LocalDate[] beginDateScope){
        return employeeService.getEmployeeByPage(currentPage,size,employee,beginDateScope);
    }

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/deps")
    public List<Department> getAllDepartment(){
        return departmentService.list();
    }

    @ApiOperation(value = "获取所有民族")
    @GetMapping("/nations")
    public List<Nation> getAllNations(){
        return nationService.list();
    }

    @ApiOperation(value = "获取所有的政治面貌")
    @GetMapping("/pos")
    public List<PoliticsStatus> getAllPoliticsStatus(){
        return politicsStatusService.list();
    }

    @ApiOperation(value = "获取所有职位")
    @GetMapping("/poions")
    public List<Position> getAllPosition(){
        return positionService.list();
    }

    @ApiOperation(value = "获取工号")
    @GetMapping("/maxWorkID")
    public ResponseBean getMaxWorkId(){
        return employeeService.getMaxWorkId();
    }

    @ApiOperation(value = "添加员工")
    @PostMapping("/")
    public ResponseBean addEmp(@RequestBody Employee employee){
        return employeeService.addEmp(employee);
    }

    @ApiOperation(value = "更新员工")
    @PutMapping("/")
    public ResponseBean updateEmp(@RequestBody Employee employee){
        if (employeeService.updateById(employee)){
            return ResponseBean.success("更新成功！");
        }
        return ResponseBean.error("更新失败！");
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping("/{id}")
    public ResponseBean deleteEmp(@PathVariable Integer id){
        if (employeeService.removeById(id)){
            return ResponseBean.success("删除成功！");
        }
        return ResponseBean.error("删除失败！");
    }

    @ApiOperation(value = "导出员工数据")
    @GetMapping(value = "/export",produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response){
        List<Employee> list = employeeService.getEmployee(null);
        for (Employee e:list) {
            System.out.println(e.toString());
        }
        //导出参数
        ExportParams exportParams = new ExportParams("员工表头", "员工表", ExcelType.HSSF);
        //导出工具类
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Employee.class, list);
        ServletOutputStream out=null;


        try {
            response.setHeader("content-type","application/octet-stream");
            //防止中文乱码
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("员工表.xls","UTF-8"));
            out=response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @ApiOperation(value = "导入员工数据")
    @PostMapping(value = "")
    public ResponseBean importEmployee(MultipartFile file){
        //导入参数
        ImportParams params = new ImportParams();
        //去掉标题行
        params.setTitleRows(1);
        List<Nation> nationList = nationService.list();
        List<PoliticsStatus> politicsStatusList = politicsStatusService.list();
        List<Department> departmentList = departmentService.list();
        List<Joblevel> joblevelList = joblevelService.list();
        List<Position> positionList = positionService.list();

        //创建要导入的数据源
        try {
            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);
            //将民族、政治身份、部门、职称和职位从中文转为数字
            list.forEach(employee->{
                employee.setNationId(nationList.get(nationList.indexOf(new Nation(employee.getNation().getName()))).getId());
                //政治 ID
                employee.setPoliticId(politicsStatusList.get(politicsStatusList.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
                //部门 id
                employee.setDepartmentId(departmentList.get(departmentList.indexOf(new Department(employee.getDepartment().getName()))).getId());
                //职位id
                employee.setJobLevelId(joblevelList.get(joblevelList.indexOf(new Joblevel(employee.getJoblevel().getName()))).getId());
                //职称id
                employee.setPosId(positionList.get(positionList.indexOf(new Position(employee.getPosition().getName()))).getId());
            });

            if (employeeService.saveBatch(list)){
                return ResponseBean.success("导入成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBean.error("导入员工失败");
    }
}
