package com.caogang.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author: xiaogang
 * @date: 2019/8/13 - 9:17
 */
@Entity
@Data
@Table(name = "role_to_menu")
public class RoleToMenu extends BaseAuditable{

    private String menuId;

    private String roleId;

}
