package com.caogang.service;

import com.caogang.dao.RoleDao;
import com.caogang.dao.RoleToMenuDao;
import com.caogang.dao.UserDao;
import com.caogang.dao.UserToRoleDao;
import com.caogang.entity.RoleInfo;
import com.caogang.entity.RoleToMenu;
import com.caogang.entity.UserInfo;
import com.caogang.entity.UserToRole;
import com.caogang.utils.UID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author: xiaogang
 * @date: 2019/8/12 - 19:27
 */
@Transactional
@Service
public class RoleServiceImpl {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserToRoleDao userToRoleDao;

    @Autowired
    private RoleToMenuDao roleToMenuDao;

    public Page<RoleInfo> selectAllRole(String iname,Integer page) {

        Page<RoleInfo> roleInfos = roleDao.findAllByRolenameLike("%" + iname + "%", PageRequest.of(page, 3, Sort.by(Sort.Order.desc("createdtime"))));

        List<RoleInfo> content = roleInfos.getContent();

        for (RoleInfo roleInfo : content) {

            roleInfo.setAuthKeys(roleToMenuDao.findAllKeysByRoleId(roleInfo.getId()));

            roleInfo.setUsers(selectSomeUserByRoleId(roleInfo.getId()));

        }

        return roleInfos;
    }

    public String selectSomeUserByRoleId(String roleInfoId){

        String usersName = "";

        List<UserInfo> userInfoList = userDao.selectSomeUserByRoleId(roleInfoId);

        for (UserInfo userInfo : userInfoList) {

            usersName+=userInfo.getUsername()+"、";

        }

        return usersName;
    }

    public void insertUserToRole(String userId, String roleId) {

        UserToRole userToRole = new UserToRole();

        userToRole.setId(UID.getUUID16());

        userToRole.setUserId(userId);

        userToRole.setRoleId(roleId);

        Integer integer = userToRoleDao.deleteByUserId(userId);

        System.out.println("***"+integer);

        userToRoleDao.save(userToRole);
    }

    public void addRole(RoleInfo roleInfo) {

        roleInfo.setId(UID.getUUID16());

        roleDao.save(roleInfo);

        List<String> roleInfoAuthKeys = roleInfo.getAuthKeys();

        RoleToMenu roleToMenu = null;

        for (String authKey:roleInfoAuthKeys) {

            roleToMenu = new RoleToMenu();

            roleToMenu.setMenuId(authKey);

            roleToMenu.setRoleId(roleInfo.getId());

            roleToMenu.setId(UID.getUUID16());

            roleToMenuDao.save(roleToMenu);

        }


    }

    public void updateRole(RoleInfo roleInfo) {

        roleDao.save(roleInfo);

        roleToMenuDao.deleteByRoleId(roleInfo.getId());

        List<String> roleInfoAuthKeys = roleInfo.getAuthKeys();

        RoleToMenu roleToMenu = null;

        for (String authKey : roleInfoAuthKeys) {

            roleToMenu = new RoleToMenu();

            roleToMenu.setMenuId(authKey);

            roleToMenu.setRoleId(roleInfo.getId());

            roleToMenu.setId(UID.getUUID16());

            roleToMenuDao.save(roleToMenu);


        }
    }

    public Integer selectUserByRoleId(String roleId) {

        return userToRoleDao.selectUserByRoleId(roleId);
    }

    public void deleteRole(String[] id) {

        roleDao.deleteAll(roleDao.findAllById(Arrays.asList(id)));
    }

    public Page<RoleInfo> selectAll() {
        return roleDao.findAll(PageRequest.of(0,999));
    }

    public Integer deleteUserToRole(String userId) {
        return userToRoleDao.deleteByUserId(userId);
    }
}
