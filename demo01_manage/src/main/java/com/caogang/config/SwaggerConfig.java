package com.caogang.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
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

        docket.select().apis(RequestHandlerSelectors.basePackage("com.caogang.controller")).paths(PathSelectors.ant("/user/**")).build();

        docket.groupName("USER");

        docket.apiInfo(setApiInfo());

        return docket;

    }

    @Bean
    public Docket getDocketForRole(){

        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        docket.select().apis(RequestHandlerSelectors.basePackage("com.caogang.controller")).paths(PathSelectors.ant("/role/**")).build();

        docket.groupName("ROLE");

        docket.apiInfo(setApiInfo());

        return docket;

    }

    @Bean
    public Docket getDocketForMenu(){

        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        docket.select().apis(RequestHandlerSelectors.basePackage("com.caogang.controller")).paths(PathSelectors.ant("/menu/**")).build();

        docket.groupName("MENU");

        docket.apiInfo(setApiInfo());

        return docket;

    }

    public ApiInfo setApiInfo(){

        Contact contact=new Contact("晓刚","http://www.baidu.com","939178611@qq.com");

        ApiInfo apiInfo=new ApiInfo(

                "这是具体的服务操作",

                "用户、角色、权限的增删改查",

                "v-1.0",

                "http://www.baidu.com",

                contact,"监听信息",

                "http://www.baidu.com",

                new ArrayList<VendorExtension>()

        );

        return apiInfo;
    }


}
