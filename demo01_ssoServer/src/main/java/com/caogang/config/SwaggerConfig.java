package com.caogang.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author: xiaogang
 * @date: 2019/8/14 - 20:54
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private Environment environment;

    @Bean
    public Docket getDocketForUser(){

        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        docket.select().apis(RequestHandlerSelectors.basePackage("com.caogang.controller")).build();

        docket.groupName("SSO");

        docket.apiInfo(setApiInfo());

        return docket;

    }

    public ApiInfo setApiInfo(){

        Contact contact=new Contact("晓刚","http://www.baidu.com","939178611@qq.com");

        ApiInfo apiInfo=new ApiInfo(

                "这是认证中心的服务操作",

                "获取验证码、登录认证等操作",

                "v-1.0",

                "http://www.baidu.com",

                contact,"监听信息",

                "http://www.baidu.com",

                new ArrayList<VendorExtension>()

        );

        return apiInfo;
    }


}
