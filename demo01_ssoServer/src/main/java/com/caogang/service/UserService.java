package com.caogang.service;

import com.caogang.dao.MenuDao;
import com.caogang.dao.RoleDao;
import com.caogang.dao.UserDao;
import com.caogang.entity.MenuInfo;
import com.caogang.entity.RoleInfo;
import com.caogang.entity.UserInfo;
import com.caogang.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author: xiaogang
 * @date: 2019/8/5 - 20:12
 */
@Transactional
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private MenuDao menuDao;

    public UserInfo selectOneUser(String loginName){

        UserInfo userInfo = userDao.findByLoginname(loginName);

        if(userInfo!=null){

            return this.together(userInfo);
        }

        return null;

    }

    private UserInfo together(UserInfo userInfo) {

        RoleInfo roleInfo = roleDao.selectRoleInfoByUserId(userInfo.getId());

        if(roleInfo!=null){

            List<MenuInfo> menuInfoList = menuDao.selectMenuInfoByRoleId(roleInfo.getId());

            Map<String,String> authMap=new Hashtable<>();

            this.getChildrenMenuInfo(roleInfo.getId(),menuInfoList,authMap);

            userInfo.setAuthormap(authMap);

            userInfo.setMenuInfoList(menuInfoList);

            //userInfo.setRoleInfoList(roleDao.findAll());
        }

        return userInfo;

    }


    public void getChildrenMenuInfo(String roleId,List<MenuInfo> menuInfoList,Map<String,String> authMap){

        for(MenuInfo menuInfo : menuInfoList) {

            int leval=menuInfo.getLevel()+1;

            List<MenuInfo> menuInfoList1 = menuDao.selectChildrenMenuInfo(roleId,menuInfo.getId());

            if(menuInfoList1.size() > 0){

                if(leval==4){

                    for(MenuInfo menu:menuInfoList1){

                        authMap.put(menu.getUrl(),"");
                    }
                }

                menuInfo.setMenuInfoList(menuInfoList1);

                getChildrenMenuInfo(roleId,menuInfoList1,authMap);
            }
            
        }

    }


    public UserInfo selectByTel(String tel) {

        UserInfo userDaoByTel = userDao.findByTel(tel);

        if(userDaoByTel!=null){

            return this.together(userDaoByTel);
        }

        return null;
    }

    public UserInfo selectUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public Integer updateUserPasswordById(String password, String userId) {
        return userDao.updateUserPasswordById(MD5.encryptPassword(password,"lcg"),userId);
    }
}
