package com.caogang.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("这是一个用户实体类")
public class UserInfo extends BaseAuditable {

    @ApiModelProperty("这是用户的名称")
    private String username;

    @ApiModelProperty("这是用户的登录名称")
    private String loginname;

    @ApiModelProperty("这是用户的登录密码")
    private String password;

    @ApiModelProperty("这是用户的电话")
    private String tel;

    @ApiModelProperty("这是用户的性别 1代表男、2代表女")
    private Integer sex;

    @ApiModelProperty("这是用户的父ID")
    private String parentId;

    @ApiModelProperty("这是用户的头像名称")
    private String url;

    @ApiModelProperty("这是用户的邮箱")
    private String email;

    @Transient
    @ApiModelProperty("这是用户的权限列表")
    private List<MenuInfo> menuInfoList;

    @Transient
    @ApiModelProperty("这是用户的角色")
    RoleInfo roleInfo;

    @Transient
    @ApiModelProperty("未用到")
    List<RoleInfo> roleInfoList;

    @Transient
    @ApiModelProperty("这是用户的具体操作")
    private Map<String,String> authormap;


}
