package com.caogang.controller;

import com.caogang.entity.MenuInfo;
import com.caogang.service.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MenuController {

    @Autowired
    private MenuServiceImpl menuServiceImpl;

    @RequestMapping("/listMenu")
    private List<MenuInfo> listMenu(){

        List<MenuInfo> menuInfoList = menuServiceImpl.selectAllMenu();

        return menuInfoList;
    }

    @RequestMapping("/deleteMenu")
    private Integer deleteMenu(@RequestBody Map<String , Object> map){

        Integer number = menuServiceImpl.selectRoleByMenuId(map.get("id").toString());

        if(number==0){

            menuServiceImpl.deleteMenu(map.get("id").toString());

        }

        return number;
    }


    @RequestMapping("/addMenu")
    private Boolean addMenu(@RequestBody MenuInfo menuInfo){

        menuServiceImpl.addMenu(menuInfo);

        return true;
    }

    @RequestMapping("/updateMenu")
    private Boolean updateMenu(@RequestBody MenuInfo menuInfo){

        menuServiceImpl.updateMenu(menuInfo);

        return true;
    }
}
