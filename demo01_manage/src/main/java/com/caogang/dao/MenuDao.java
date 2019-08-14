package com.caogang.dao;

import com.caogang.entity.MenuInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: xiaogang
 * @date: 2019/8/5 - 20:10
 */
public interface MenuDao extends JpaRepository<MenuInfo, String> {


    @Query(value = "SELECT count(*) from demo01_role dr INNER JOIN role_to_menu rtm on rtm.roleId = dr.id where rtm.menuId = ?1",nativeQuery = true)
    Integer selectRoleByMenuId(String menuId);

    @Query(value = "SELECT * from demo01_menu dm where dm.parentId is null",nativeQuery = true)
    List<MenuInfo> findPrentMenu();

    @Query(value = "SELECT * from demo01_menu dm where dm.parentId = ?1",nativeQuery = true)
    List<MenuInfo> selectChildrenMenuInfo(String id);
}
