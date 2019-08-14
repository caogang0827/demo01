package com.caogang.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 作者：LCG
 * 创建时间：2018/11/23 15:57
 * 描述：返回结果映射
 */
@Data
@ApiModel("这是自定义返回对象类")
public class ResponseResult implements Serializable {

    //返回信息编码  0失败 1成功
    @ApiModelProperty("返回信息编码  0失败 1成功")
    private Integer code;

    //错误信息
    @ApiModelProperty("错误信息")
    private String error;

    //程序返回结果
    @ApiModelProperty("程序返回结果")
    private Object result;

    //成功信息
    @ApiModelProperty("成功信息")
    private String success;

    //创建实例
    public static ResponseResult getResponseResult(){
        return new ResponseResult ();
    }

    //登陆成功的标识(这里存储了一些用户的信息)
    @ApiModelProperty("登陆成功的标识(这里存储了一些用户的信息)")
    private String token;

    //用来表示token的一个唯一的字符串
    @ApiModelProperty("用来表示token的一个唯一的字符串")
    private String tokenkey;

    //选中的需要回显的菜单ID
    @ApiModelProperty("选中的需要回显的菜单ID")
    private String[] menuIds;

}
