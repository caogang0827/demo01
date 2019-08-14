package com.caogang.dao;

import com.caogang.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: xiaogang
 * @date: 2019/8/5 - 20:05
 */
public interface UserDao extends JpaRepository<UserInfo, String> {

    UserInfo findByLoginname(String loginName);

}
