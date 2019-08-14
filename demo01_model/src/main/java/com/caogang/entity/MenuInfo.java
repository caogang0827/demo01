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
@Table(name = "demo01_menu")
public class MenuInfo extends BaseAuditable {

    private String name;

    private String parentId;

    private Integer level;

    private String url;

    @Transient
    private List<MenuInfo> menuInfoList;

}
