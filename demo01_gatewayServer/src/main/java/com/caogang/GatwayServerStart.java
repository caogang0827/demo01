package com.caogang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: xiaogang
 * @date: 2019/8/12 - 11:08
 */
@SpringBootApplication
public class GatwayServerStart {

    public static void main(String[] args){

        SpringApplication.run(GatwayServerStart.class);

        System.out.println("gateway网关服务启动...");
    }

}
