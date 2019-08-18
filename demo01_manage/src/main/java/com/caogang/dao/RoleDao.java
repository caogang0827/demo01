package com.caogang.dao;

import com.caogang.entity.RoleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * @author: xiaogang
 * @date: 2019/8/5 - 20:08
 */
public interface RoleDao extends JpaRepository<RoleInfo,String> {

    @Query(value = "SELECT dr.* from demo01_role dr INNER JOIN user_to_role utr on utr.roleId = dr.id where utr.userId = ?1",nativeQuery = true)
    RoleInfo selectRole(String userId);

    Page<RoleInfo> findAllByRolenameLike(String iname, Pageable pageable);

}
