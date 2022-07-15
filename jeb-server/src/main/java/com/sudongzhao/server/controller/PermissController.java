package com.sudongzhao.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sudongzhao.server.pojo.*;
import com.sudongzhao.server.service.IMenuRoleService;
import com.sudongzhao.server.service.IMenuService;
import com.sudongzhao.server.service.IRoleService;
import com.sudongzhao.server.service.impl.RoleServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/basic/permiss")
public class PermissController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IMenuRoleService menuRoleService;

    @ApiOperation(value = "获取所有角色信息")
    @GetMapping("/")
    public List<Role> getAllPosition(){
        return roleService.list();
    }

    @ApiOperation(value = "添加角色信息")
    @PostMapping("/")
    public ResponseBean addPosition(@RequestBody Role role){
        if (!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        if (roleService.save(role)){
            return ResponseBean.success("添加成功！");
        }
        return ResponseBean.error("添加失败！");
    }

    @ApiOperation(value = "更新角色信息")
    @PutMapping("/update")
    public ResponseBean updatePosition(@RequestBody Role role){
        if (roleService.updateById(role)){
            return ResponseBean.success("更新成功！");
        }
        return ResponseBean.error("更新失败！");
    }

    @ApiOperation(value = "删除角色信息")
    @DeleteMapping("/{id}")
    public ResponseBean deletePosition(@PathVariable Integer id){
        if (roleService.removeById(id)){
            return ResponseBean.success("删除成功");
        }
        return ResponseBean.error("删除失败！");
    }

    @ApiOperation(value = "批量删除角色信息")
    @DeleteMapping("/")
    public ResponseBean deletePositionByIds(Integer[] ids){
        if (roleService.removeByIds(Arrays.asList(ids))){
            return ResponseBean.success("删除成功！");
        }
        return ResponseBean.error("删除失败！");
    }

    @ApiOperation(value = "查找所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidByRid(@PathVariable Integer rid){
        return menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid",rid)).stream().map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation(value = "更新菜单")
    @PutMapping("/")
    public ResponseBean updateMenuRole(Integer rid,Integer[] mids){
        return menuRoleService.updateMenuRole(rid,mids);
    }
}
