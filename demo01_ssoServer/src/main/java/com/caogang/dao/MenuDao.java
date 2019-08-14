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

    @Query(value = "select dm.* from role_to_menu rtm INNER JOIN demo01_menu dm ON rtm.menuId=dm.id where rtm.roleId=?1 and dm.parentId is null",nativeQuery = true)
    List<MenuInfo> selectMenuInfoByRoleId(String roleId);

    @Query(value = "select dm.* from role_to_menu rtm INNER JOIN demo01_menu dm ON rtm.menuId=dm.id where rtm.roleId=?1 and dm.parentId=?2",nativeQuery = true)
    List<MenuInfo> selectChildrenMenuInfo(String roleId,String menuId);
}
