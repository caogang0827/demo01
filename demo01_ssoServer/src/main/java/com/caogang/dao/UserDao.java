package com.caogang.dao;

import com.caogang.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: xiaogang
 * @date: 2019/8/5 - 20:05
 */
public interface UserDao extends JpaRepository<UserInfo, String> {

    UserInfo findByLoginname(String loginName);

    UserInfo findByTel(String tel);

    UserInfo findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query(value = "update demo01_user du set du.password =?1 where du.id = ?2",nativeQuery = true)
    Integer updateUserPasswordById(String password, String userId);
}
