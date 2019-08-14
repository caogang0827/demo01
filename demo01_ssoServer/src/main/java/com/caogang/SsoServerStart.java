package com.caogang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author: xiaogang
 * @date: 2019/8/5 - 14:34
 */

@EnableJpaAuditing
@SpringBootApplication
@EntityScan(basePackages = {"com.caogang.entity.**"})
public class SsoServerStart {

    public static void main(String[] args){

        SpringApplication.run(SsoServerStart.class,args);

        System.out.println("sso认证中心启动...");
    }

}
