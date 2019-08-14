package com.caogang.dao;

import com.caogang.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * @author: xiaogang
 * @date: 2019/8/5 - 20:05
 */
public interface UserDao extends JpaRepository<UserInfo, String> {

    Page<UserInfo> findAllByUsernameLikeAndSexAndCreatedtimeBetween(String iname, Integer isex, Date start, Date end, Pageable pageable);

    Page<UserInfo> findAllByUsernameLike(String iname,Pageable pageable);

    Page<UserInfo> findAllByUsernameLikeAndSex(String iname,Integer isex,Pageable pageable);

    Page<UserInfo> findAllByUsernameLikeAndCreatedtimeBetween(String iname,Date start, Date end, Pageable pageable);

}
