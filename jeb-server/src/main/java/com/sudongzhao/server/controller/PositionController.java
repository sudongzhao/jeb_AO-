package com.sudongzhao.server.controller;


import com.sudongzhao.server.mapper.PositionMapper;
import com.sudongzhao.server.pojo.Position;
import com.sudongzhao.server.pojo.ResponseBean;
import com.sudongzhao.server.service.IPositionService;
import com.sun.org.apache.regexp.internal.REUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
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
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/")
    public List<Position> getAllPosition(){
       return positionService.list();
    }

    @ApiOperation(value = "添加职位信息")
    @PostMapping("/")
    public ResponseBean addPosition(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        if (positionService.save(position)){
            return ResponseBean.success("添加成功！");
        }
        return ResponseBean.error("添加失败！");
    }

    @ApiOperation(value = "更新职位信息")
    @PutMapping("/update")
    public ResponseBean updatePosition(@RequestBody Position position){
        if (positionService.updateById(position)){
            return ResponseBean.success("更新成功！");
        }
        return ResponseBean.error("更新失败！");
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public ResponseBean deletePosition(@PathVariable Integer id){
        if (positionService.removeById(id)){
            return ResponseBean.success("删除成功");
        }
        return ResponseBean.error("删除失败！");
    }

    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public ResponseBean deletePositionByIds(Integer[] ids){
        if (positionService.removeByIds(Arrays.asList(ids))){
            return ResponseBean.success("删除成功！");
        }
        return ResponseBean.error("删除失败！");
    }

}
