package com.caogang.dao;

import com.caogang.entity.UserToRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: xiaogang
 * @date: 2019/8/12 - 16:00
 */
public interface UserToRoleDao extends JpaRepository<UserToRole,String> {

    List<UserToRole> findAllByUserId(List<String> userId);

    Integer deleteByUserId(String userId);

    @Query(value = "SELECT count(*) from demo01_user du INNER JOIN user_to_role utr on utr.userId = du.id where utr.roleId = ?1",nativeQuery = true)
    Integer selectUserByRoleId(String roleId);
}
