package com.sudongzhao.server.controller;


import com.sudongzhao.server.pojo.Joblevel;
import com.sudongzhao.server.pojo.Position;
import com.sudongzhao.server.pojo.ResponseBean;
import com.sudongzhao.server.service.IJoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
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
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {

    @Autowired
    private IJoblevelService joblevelService;

    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/")
    public List<Joblevel> getAllPosition(){
        return joblevelService.list();
    }

    @ApiOperation(value = "添加职位信息")
    @PostMapping("/")
    public ResponseBean addPosition(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        if (joblevelService.save(joblevel)){
            return ResponseBean.success("添加成功！");
        }
        return ResponseBean.error("添加失败！");
    }

    @ApiOperation(value = "更新职位信息")
    @PutMapping("/update")
    public ResponseBean updatePosition(@RequestBody Joblevel joblevel){
        if (joblevelService.updateById(joblevel)){
            return ResponseBean.success("更新成功！");
        }
        return ResponseBean.error("更新失败！");
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public ResponseBean deletePosition(@PathVariable Integer id){
        if (joblevelService.removeById(id)){
            return ResponseBean.success("删除成功");
        }
        return ResponseBean.error("删除失败！");
    }

    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public ResponseBean deletePositionByIds(Integer[] ids){
        if (joblevelService.removeByIds(Arrays.asList(ids))){
            return ResponseBean.success("删除成功！");
        }
        return ResponseBean.error("删除失败！");
    }

}
