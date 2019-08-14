package com.caogang.service;

import com.caogang.dao.MenuDao;
import com.caogang.entity.MenuInfo;
import com.caogang.utils.UID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: xiaogang
 * @date: 2019/8/13 - 23:55
 */
@Transactional
@Service
public class MenuServiceImpl {

    @Autowired
    private MenuDao menuDao;


    public Integer selectRoleByMenuId(String id) {

        return menuDao.selectRoleByMenuId(id);
    }

    public void deleteMenu(String id) {

        menuDao.deleteById(id);
    }

    public void addMenu(MenuInfo menuInfo) {

        menuInfo.setId(UID.getUUID16());

        menuInfo.setLevel(menuInfo.getLevel()+1);

        menuDao.save(menuInfo);
    }

    public void updateMenu(MenuInfo menuInfo) {

        menuDao.save(menuInfo);
    }

    public List<MenuInfo> selectAllMenu() {

        List<MenuInfo> menuInfoList = menuDao.findPrentMenu();

        if(!menuInfoList.isEmpty()){

            this.getChildrenMenuInfo(menuInfoList);

        }

        return menuInfoList;
    }

    public void getChildrenMenuInfo(List<MenuInfo> menuInfoList){

        for(MenuInfo menuInfo : menuInfoList) {

            List<MenuInfo> menuInfoList1 = menuDao.selectChildrenMenuInfo(menuInfo.getId());

            if(menuInfoList1.size() > 0){

                menuInfo.setMenuInfoList(menuInfoList1);

                getChildrenMenuInfo(menuInfoList1);

            }
        }
    }

}
