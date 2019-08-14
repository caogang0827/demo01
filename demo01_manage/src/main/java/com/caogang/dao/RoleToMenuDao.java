package com.caogang.dao;

import com.caogang.entity.RoleToMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: xiaogang
 * @date: 2019/8/13 - 9:16
 */
public interface RoleToMenuDao extends JpaRepository<RoleToMenu, String> {

    @Query(value = "SELECT rtm.menuId from role_to_menu rtm inner join demo01_menu dm on dm.id = rtm.menuId where rtm.roleId = ?1 and dm.level = 4",nativeQuery = true)
    List<String> findAllKeysByRoleId(String roleId);

    void deleteByRoleId(String roleId);

}
