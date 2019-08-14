package com.caogang.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 作者: LCG
 * 日期: 2019/8/4 16:30
 * 描述:
 */
@Entity
@Data
@Table(name = "demo01_role")
public class RoleInfo extends BaseAuditable {

    private String rolename;

    private String description;

    @Transient
    private List<String> authKeys;

}
