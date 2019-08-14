package com.caogang.controller;

import com.caogang.entity.QueryEntity;
import com.caogang.entity.RoleInfo;
import com.caogang.entity.UserInfo;
import com.caogang.service.UserServiceImpl;
import com.caogang.utils.UID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author: xiaogang
 * @date: 2019/8/12 - 15:01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceimpl;

    @RequestMapping("/listUser")
    private Page<UserInfo> listUser(@RequestBody QueryEntity queryEntity){

        Page<UserInfo> userInfos = null;

        if(queryEntity.getStart()==null&&queryEntity.getEnd()==null&&queryEntity.getIsex()==null){

            userInfos = userServiceimpl.selectAllUser1("%"+queryEntity.getIname()+"%",queryEntity.getPage(),queryEntity.getPageSize());

        }else if (queryEntity.getStart()==null&&queryEntity.getEnd()==null&&queryEntity.getIsex()!=null){

            userInfos = userServiceimpl.selectAllUser2("%"+queryEntity.getIname()+"%",queryEntity.getIsex(),queryEntity.getPage(),queryEntity.getPageSize());

        }else if(queryEntity.getStart()!=null&&queryEntity.getEnd()!=null&&queryEntity.getIsex()==null){

            userInfos = userServiceimpl.selectAllUser3("%"+queryEntity.getIname()+"%",queryEntity.getStart(),queryEntity.getEnd(),queryEntity.getPage(),queryEntity.getPageSize());

        }else{

            userInfos = userServiceimpl.selectAllUser(queryEntity);
        }

        List<UserInfo> userInfoList = userInfos.getContent();

        for (UserInfo userInfo: userInfoList) {

            RoleInfo roleInfo = userServiceimpl.selectRole(userInfo.getId());

            userInfo.setRoleInfo(roleInfo);

        }

        return userInfos;

    }

    @RequestMapping("/deleteUser")
    private Boolean deleteUser(String[] id){

        userServiceimpl.deleteUser(id);

        userServiceimpl.deleteUserToRole(id);

        return true;
    }

    @CrossOrigin
    @RequestMapping("/addPhoto")
    private void addPhoto(@RequestParam("file") MultipartFile multipartFile) {

        String filename = multipartFile.getOriginalFilename();

        File file = new File("E:\\image\\" + filename);

        try {

            multipartFile.transferTo(file);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    @CrossOrigin
    @RequestMapping("/addExcel")
    private void addExcel(@RequestParam("file") MultipartFile multipartFile) {

        String filename = multipartFile.getOriginalFilename();

        File file = new File("E:\\excel\\" + filename);

        try {

            multipartFile.transferTo(file);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    @RequestMapping("/addUser")
    private Boolean addUser(@RequestBody UserInfo userInfo) {

        userInfo.setId(UID.getUUID16());

        userServiceimpl.addUser(userInfo);

        return true;

    }

    @RequestMapping("/updateUser")
    private Boolean updateUser(@RequestBody UserInfo userInfo) {

        userServiceimpl.updateUser(userInfo);

        return true;

    }


    @RequestMapping("/uploadUser")
    private Boolean uploadUser(@RequestBody QueryEntity queryEntity, HttpServletResponse response){

        Page<UserInfo> userInfos = null;

        if(queryEntity.getStart()==null&&queryEntity.getEnd()==null&&queryEntity.getIsex()==null){

            userInfos = userServiceimpl.selectAllUser1("%"+queryEntity.getIname()+"%",queryEntity.getPage(),queryEntity.getPageSize());

        }else if (queryEntity.getStart()==null&&queryEntity.getEnd()==null&&queryEntity.getIsex()!=null){

            userInfos = userServiceimpl.selectAllUser2("%"+queryEntity.getIname()+"%",queryEntity.getIsex(),queryEntity.getPage(),queryEntity.getPageSize());

        }else if(queryEntity.getStart()!=null&&queryEntity.getEnd()!=null&&queryEntity.getIsex()==null){

            userInfos = userServiceimpl.selectAllUser3("%"+queryEntity.getIname()+"%",queryEntity.getStart(),queryEntity.getEnd(),queryEntity.getPage(),queryEntity.getPageSize());

        }else{

            userInfos = userServiceimpl.selectAllUser(queryEntity);
        }

        List<UserInfo> userInfoList = userInfos.getContent();

        for (UserInfo userInfo: userInfoList) {

            RoleInfo roleInfo = userServiceimpl.selectRole(userInfo.getId());

            userInfo.setRoleInfo(roleInfo);

        }

        List<UserInfo> userList = userInfos.getContent();

        try {

            userServiceimpl.exportUserList(userList);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return true;
    }




}
