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
@Table(name = "demo01_role")
@ApiModel("这是一个角色实体类")
public class RoleInfo extends BaseAuditable {

    @ApiModelProperty("这是角色的名称")
    private String rolename;

    @ApiModelProperty("这是角色的描述")
    private String description;

    @Transient
    @ApiModelProperty("这是角色拥有的权限列表")
    private List<String> authKeys;

}
