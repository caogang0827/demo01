package com.caogang.entity;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.Map;

/**
 * @author: xiaogang
 * @date: 2019/8/5 - 19:44
 */
@Entity
@Data
@Table(name = "demo01_user")
public class UserInfo extends BaseAuditable {

    private String username;

    private String loginname;

    private String password;

    private String tel;

    private Integer sex;

    private String parentId;

    private String url;

    @Transient
    private List<MenuInfo> menuInfoList;

    @Transient
    RoleInfo roleInfo;

    @Transient
    List<RoleInfo> roleInfoList;

    @Transient
    private Map<String,String> authormap;


}
