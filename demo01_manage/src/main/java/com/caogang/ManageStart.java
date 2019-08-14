package com.caogang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author: xiaogang
 * @date: 2019/8/12 - 14:56
 */
@EnableJpaAuditing
@SpringBootApplication
@EntityScan(basePackages = {"com.caogang.entity.**"})
public class ManageStart {

    public static void main(String[] args){

        SpringApplication.run(ManageStart.class,args);

        System.out.println("管理服务启动...");

    }

}
