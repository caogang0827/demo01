package com.caogang.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("这是一个权限（菜单）实体类")
public class MenuInfo extends BaseAuditable {

    @ApiModelProperty("这是权限（菜单）的名称")
    private String name;

    @ApiModelProperty("这是权限（菜单）的父ID")
    private String parentId;

    @ApiModelProperty("这是权限（菜单）的等级")
    private Integer level;

    @ApiModelProperty("这是权限（菜单）的具体路径")
    private String url;

    @Transient
    @ApiModelProperty("这是权限（菜单）的列表")
    private List<MenuInfo> menuInfoList;

}
