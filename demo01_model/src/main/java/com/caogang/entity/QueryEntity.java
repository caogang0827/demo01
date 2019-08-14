package com.caogang.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: xiaogang
 * @date: 2019/8/7 - 14:26
 */
@Data
@ApiModel("这是一个查询实体类")
public class QueryEntity {

    @ApiModelProperty("这是查询实体的名称")
    private String iname;

    @ApiModelProperty("这是查询实体的性别")
    private Integer isex;

    @ApiModelProperty("这是查询实体的开始时间")
    private Date start;

    @ApiModelProperty("这是查询实体的结束")
    private Date end;

    @ApiModelProperty("这是查询实体的当前页")
    private Integer page;

    @ApiModelProperty("这是查询实体的分页条数")
    private Integer pageSize;

}
