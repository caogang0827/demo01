package com.caogang.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Date;

/**
 * @author: xiaogang
 * @date: 2019/8/12 - 9:15
 */
@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@ApiModel("这是一个公共父类类")
public abstract class BaseAuditable {

    @Id
    @ApiModelProperty("这是公共主键")
    private String id;

    @LastModifiedDate
    @ApiModelProperty("这是公共最后一次修改时间")
    private Date updatedtime;

    @CreatedDate
    @ApiModelProperty("这是公共创建时间")
    private Date createdtime;

    @Version
    @ApiModelProperty("这是公共版本号")
    private Long version;

}
