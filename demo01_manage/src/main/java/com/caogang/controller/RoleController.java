package com.caogang.controller;

import com.caogang.entity.RoleInfo;
import com.caogang.service.RoleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author: xiaogang
 * @date: 2019/8/12 - 19:25
 */
@RestController
@RequestMapping("/role")
@Api(tags = "这是一个角色增、删、改、查的接口")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @PostMapping("/listRole")
    @ApiOperation("这是接口类RoleController中的查询角色方法")
    private Page<RoleInfo> listRole(@RequestParam(defaultValue = "") String iname, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer find) {

        Page<RoleInfo> listRole = null;

        if(find==1){

            listRole = roleServiceImpl.selectAll();

        }else{

            listRole = roleServiceImpl.selectAllRole(iname, page);

        }

        return listRole;
    }

    @PostMapping("/bindRole")
    @ApiOperation("这是接口类RoleController中的用户绑定角色方法")
    private Boolean bindRole(String userId, String roleId) {

        roleServiceImpl.insertUserToRole(userId, roleId);

        return true;
    }

    @PostMapping("/notbindRole")
    @ApiOperation("这是接口类RoleController中的用户解绑角色方法")
    private Boolean notbindRole(String userId) {

        roleServiceImpl.deleteUserToRole(userId);

        return true;
    }

    @PostMapping("/addRole")
    @ApiOperation("这是接口类RoleController中的添加角色方法")
    private Boolean addRole(@RequestBody RoleInfo roleInfo) {

        roleServiceImpl.addRole(roleInfo);

        return true;
    }

    @PostMapping("/updateRole")
    @ApiOperation("这是接口类RoleController中的修改角色方法")
    private Boolean updateRole(@RequestBody RoleInfo roleInfo) {

        roleServiceImpl.updateRole(roleInfo);

        return true;
    }

    @PostMapping("/deleteRole")
    @ApiOperation("这是接口类RoleController中的删除角色方法")
    private Integer deleteRole(String[] id){

        Integer number = 0;

        List<String> ids = Arrays.asList(id);

        for (String i : ids) {

            Integer user = roleServiceImpl.selectUserByRoleId(i);

            number += user;

        }

        if(number==0){

            roleServiceImpl.deleteRole(id);

        }

        return number;
    }

}