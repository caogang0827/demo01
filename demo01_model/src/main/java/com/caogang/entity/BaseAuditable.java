package com.caogang.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;

/**
 * @author: xiaogang
 * @date: 2019/8/12 - 9:15
 */
@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseAuditable {

    @Id
    private String id;

    @LastModifiedDate
    private Date updatedtime;

    @CreatedDate
    private Date createdtime;

    @Version
    private Long version;

}
