package com.caogang.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author: xiaogang
 * @date: 2019/8/7 - 14:26
 */
@Data
public class QueryEntity {

    private String iname;

    private Integer isex;

    private Date start;

    private Date end;

    private Integer page;

    private Integer pageSize;

}
