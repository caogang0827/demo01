package com.caogang.controller;

import com.alibaba.fastjson.JSON;
import com.caogang.entity.UserInfo;
import com.caogang.exception.LoginException;
import com.caogang.random.VerifyCodeUtils;
import com.caogang.result.ResponseResult;
import com.caogang.service.UserService;
import com.caogang.utils.JWTUtils;
import com.caogang.utils.MD5;
import com.caogang.utils.UID;
import javafx.fxml.LoadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;





/**
 * @author: xiaogang
 * @date: 2019/8/5 - 14:36
 */
@Controller
public class MainController {

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    @Autowired
    private UserService userService;

    /**
     * 获取滑动验证的验证码
     * @return
     */
    @RequestMapping("getCode")
    @ResponseBody
    public ResponseResult getCode(HttpServletResponse response){

        //生成一个长度是5的随机字符串
        String code = VerifyCodeUtils.generateVerifyCode(5);

        //新建返回对象
        ResponseResult responseResult = ResponseResult.getResponseResult();

        //存入随机字符串
        responseResult.setResult(code);

        //给随机字符串标识
        String uidCode="CODE"+ UID.getUUID16();

        //将生成的随机字符串标识后存入redis
        redisTemplate.opsForValue().set(uidCode,code);

        //设置过期时间
        redisTemplate.expire(uidCode,1, TimeUnit.MINUTES);

        //回写cookie
        Cookie cookie=new Cookie("authcode",uidCode);
        cookie.setPath("/");
        cookie.setDomain("localhost");

        //响应添加cookie
        response.addCookie(cookie);

        //返回结果对象
        return responseResult;

    }

    @ResponseBody
    @RequestMapping("login")
    public ResponseResult login(@RequestBody Map<String, Object> map) throws LoadException, LoginException {

        ResponseResult responseResult = ResponseResult.getResponseResult();

        //获取redis存入的code码
        String code = (String) redisTemplate.opsForValue().get(map.get("codekey").toString());

        //对比存入redis的code码和从前台带回的code码
        if(code==null || !code.equals(map.get("code").toString())){

            responseResult.setCode(500);

            responseResult.setError("验证码错误，请重新输入验证码！");

            return responseResult;
        }

        //进行用户密码的校验
        if (map!=null&&map.get("loginname")!=null){

            //根据登录名查找用户
            UserInfo userInfo = userService.selectOneUser(map.get("loginname").toString());

            if(userInfo!=null){

                //验证密码
                String encryptPassword = MD5.encryptPassword(map.get("password").toString(), "lcg");

                if(userInfo.getPassword().equals(encryptPassword)){

                    //将登陆的用户信息转存为JSON字符串
                    String jsonString = JSON.toJSONString(userInfo);

                    //用jwt将用户信息进行加密，加密用作token票据
                    String generateToken = JWTUtils.generateToken(jsonString);

                    //将加密信息存入返回实体
                    responseResult.setToken(generateToken);

                    //将登陆用户的token票据存入Redis中
                    redisTemplate.opsForValue().set("USERINFO"+userInfo.getId(),generateToken);

                    //将登陆用户的权限存入Redis中
                    if(userInfo.getAuthormap() != null){

                        redisTemplate.delete("USERDATAAUTH"+userInfo.getId());

                        redisTemplate.opsForHash().putAll("USERDATAAUTH"+userInfo.getId(),userInfo.getAuthormap());

                    }

                    //设置token的过期时间
                    redisTemplate.expire("USERINFO"+userInfo.getId(),3000,TimeUnit.SECONDS);

                    //设置返回实体
                    responseResult.setResult(userInfo);
                    responseResult.setCode(200);
                    responseResult.setSuccess("登陆成功！");

                    return responseResult;

                }else{
                    throw new LoginException("用户名或密码错误");
                }
            }else{
                throw new LoginException("用户名或密码错误");
            }
        }else{
            throw new LoginException("用户名或密码错误");
        }

    }

}
