package com.caogang.controller;

import com.caogang.entity.QueryEntity;
import com.caogang.entity.RoleInfo;
import com.caogang.entity.UserInfo;
import com.caogang.service.UserServiceImpl;
import com.caogang.utils.UID;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author: xiaogang
 * @date: 2019/8/12 - 15:01
 */
@RestController
@RequestMapping("/user")
@Api(tags = "这是用户增、删、改、查接口")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceimpl;


    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Autowired
    private FastFileStorageClient storageClient;


    @PostMapping("/listUser")
    @ApiOperation("这是接口类UserController中的用户查询方法")
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

    @PostMapping("/deleteUser")
    @ApiOperation("这是接口类UserController中的删除用户方法")
    private Boolean deleteUser(String[] id){

        userServiceimpl.deleteUser(id);

        userServiceimpl.deleteUserToRole(id);

        return true;
    }

    @CrossOrigin
    @RequestMapping("/addPhoto")
    @ApiOperation("这是接口类UserController中的上传用户头像方法")
    private void addPhoto(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        String filename = multipartFile.getOriginalFilename();

        File file = new File("E:\\image\\" + filename);

        try {

            multipartFile.transferTo(file);

        } catch (IOException e) {

            e.printStackTrace();

        }




//        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(multipartFile.getInputStream(), multipartFile.getSize(), "png", null);
//
//        System.out.println(storePath.getFullPath());
//
//        String path = thumbImageConfig.getThumbImagePath(storePath.getPath());
//
//        System.out.println(path.substring(0,path.lastIndexOf("_")));

        //imageName=path.substring(0,path.lastIndexOf("_"));


    }

    @CrossOrigin
    @RequestMapping("/addExcel")
    @ApiOperation("这是接口类UserController中的使用Excel批量导入方法")
    private void addExcel(@RequestParam("file") MultipartFile multipartFile) {

        String filename = multipartFile.getOriginalFilename();

        File file = new File("E:\\excel\\" + filename);

        try {

            multipartFile.transferTo(file);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    @PostMapping("/addUser")
    @ApiOperation("这是接口类UserController中的添加用户方法")
    private Boolean addUser(@RequestBody UserInfo userInfo) {

        userInfo.setId(UID.getUUID16());

        userServiceimpl.addUser(userInfo);

        return true;

    }

    @PostMapping("/updateUser")
    @ApiOperation("这是接口类UserController中的修改用户方法")
    private Boolean updateUser(@RequestBody UserInfo userInfo) {

        userServiceimpl.updateUser(userInfo);

        return true;

    }


    @PostMapping("/uploadUser")
    @ApiOperation("这是接口类UserController中的批量导出用户成Excel表方法")
    private Boolean uploadUser(@RequestBody QueryEntity queryEntity){

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
