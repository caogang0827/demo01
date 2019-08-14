package com.caogang.controller;

import com.caogang.entity.MenuInfo;
import com.caogang.service.MenuServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: xiaogang
 * @date: 2019/8/13 - 23:50
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "这是一个权限（菜单）增、删、改、查接口")
public class MenuController {

    @Autowired
    private MenuServiceImpl menuServiceImpl;

    @PostMapping("/listMenu")
    @ApiOperation("这是接口类MenuController中的查询权限方法")
    private List<MenuInfo> listMenu(){

        List<MenuInfo> menuInfoList = menuServiceImpl.selectAllMenu();

        return menuInfoList;
    }

    @PostMapping("/deleteMenu")
    @ApiOperation("这是接口类MenuController中的删除权限方法")
    private Integer deleteMenu(@RequestBody Map<String , Object> map){

        Integer number = menuServiceImpl.selectRoleByMenuId(map.get("id").toString());

        if(number==0){

            menuServiceImpl.deleteMenu(map.get("id").toString());

        }

        return number;
    }


    @PostMapping("/addMenu")
    @ApiOperation("这是接口类MenuController中的添加权限方法")
    private Boolean addMenu(@RequestBody MenuInfo menuInfo){

        menuServiceImpl.addMenu(menuInfo);

        return true;
    }

    @PostMapping("/updateMenu")
    @ApiOperation("这是接口类MenuController中的修改权限方法")
    private Boolean updateMenu(@RequestBody MenuInfo menuInfo){

        menuServiceImpl.updateMenu(menuInfo);

        return true;
    }
}
