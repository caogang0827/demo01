package com.caogang.dao;

import com.caogang.entity.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: xiaogang
 * @date: 2019/8/5 - 20:08
 */
public interface RoleDao extends JpaRepository<RoleInfo,String> {

    @Query(value = "select dr.* from user_to_role utr INNER JOIN demo01_role dr ON utr.roleId=dr.id where utr.userId=?1",nativeQuery = true)
    RoleInfo selectRoleInfoByUserId(String userId);

}
