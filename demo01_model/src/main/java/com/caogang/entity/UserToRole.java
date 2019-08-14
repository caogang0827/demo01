package com.caogang.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author: xiaogang
 * @date: 2019/8/5 - 19:44
 */
@Entity
@Data
@Table(name = "user_to_role")
public class UserToRole extends BaseAuditable {

    private String userId;

    private String roleId;

}
