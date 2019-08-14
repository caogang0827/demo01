package com.caogang.controller;

import com.caogang.entity.RoleInfo;
import com.caogang.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author: xiaogang
 * @date: 2019/8/12 - 19:25
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @RequestMapping("/listRole")
    private Page<RoleInfo> listRole(@RequestParam(defaultValue = "") String iname, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer find) {

        Page<RoleInfo> listRole = null;

        if(find==1){

            listRole = roleServiceImpl.selectAll();

        }else{

            listRole = roleServiceImpl.selectAllRole(iname, page);

        }

        return listRole;
    }

    @RequestMapping("/bindRole")
    private Boolean bindRole(String userId, String roleId) {

        roleServiceImpl.insertUserToRole(userId, roleId);

        return true;
    }

    @RequestMapping("/addRole")
    private Boolean addRole(@RequestBody RoleInfo roleInfo) {

        roleServiceImpl.addRole(roleInfo);

        return true;
    }

    @RequestMapping("/updateRole")
    private Boolean updateRole(@RequestBody RoleInfo roleInfo) {

        roleServiceImpl.updateRole(roleInfo);

        return true;
    }

    @RequestMapping("/deleteRole")
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