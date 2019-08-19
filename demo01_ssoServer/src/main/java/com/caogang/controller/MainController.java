package com.caogang.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.caogang.entity.Chart;
import com.caogang.entity.UserInfo;
import com.caogang.exception.LoginException;
import com.caogang.random.VerifyCodeUtils;
import com.caogang.result.ResponseResult;
import com.caogang.service.UserService;
import com.caogang.utils.JWTUtils;
import com.caogang.utils.MD5;
import com.caogang.utils.UID;
import com.zhenzi.sms.ZhenziSmsClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javafx.fxml.LoadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;





/**
 * @author: xiaogang
 * @date: 2019/8/5 - 14:36
 */
@RestController
@Api(tags = "这是一个SSO权限认证中心接口")
public class MainController {

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    @Autowired
    private UserService userService;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    //短信平台相关参数
    //这个不用改
    private String apiUrl = "https://sms_developer.zhenzikj.com";
    //榛子云系统上获取
    private String appId = "102375";
    private String appSecret = "f2b5b622-3317-403a-b240-9a87a5c1f987";


    /**
     * 获取滑动验证的验证码
     * @return
     */
    @PostMapping("getCode")
    @ApiOperation("这是接口类MainController中的获取验证码方法")
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


    @PostMapping("login")
    @ApiOperation("这是接口类MainController中的登录认证方法")
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

                    return this.together(userInfo);

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


    @PostMapping("chart")
    @ApiOperation("这是接口类MainController中的统计登录人数方法")
    private Chart chart(){

        Chart chart = new Chart();

        List<String> oldDate = redisTemplate.opsForList().range("date", 0, -1);

        if(oldDate.size()>30){

            //只保存30天数据
            oldDate.remove(oldDate.size()-1);
        }

        if(oldDate!=null){

            List<Object> num = new ArrayList<>();

            Collections.reverse(oldDate);

            chart.setHeng(oldDate);

            for (String date : oldDate) {

                Object number = redisTemplate.opsForHash().get("number", date);

                num.add(number);

            }

            chart.setZong(num);
        }

        return chart;

    }


    @PostMapping("sendTel")
    @ApiOperation("这是接口类MainController中的接收前台手机号方法")
    private Boolean sendTel(@RequestBody Map<String , String> map){

        Integer newNum = (int)((Math.random()*9+1)*100000);

        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);

        System.out.println(map.get("tel")+"<<<<<");

        try {

            String send = client.send(map.get("tel"), "您的验证码为:" + newNum + "，该码有效期为5分钟，该码只能使用一次!");

            JSONObject jsonObject = JSONObject.parseObject(send);

            if (jsonObject.getIntValue("code")!=0){//发送短信失败

                return false;

            }else{

                redisTemplate.opsForValue().set(map.get("tel"),newNum.toString());

                redisTemplate.expire(map.get("tel"),300,TimeUnit.SECONDS);

                System.out.println(newNum);

                return true;

            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }

    }


    @PostMapping("sendCode")
    @ApiOperation("这是接口类MainController中的接收短信验证码方法")
    private ResponseResult sendTelCode(@RequestBody Map<String , String> map){

        ResponseResult result = new ResponseResult();

        String checkCode = redisTemplate.opsForValue().get(map.get("tel"));

        if(map.get("telCode").equals(checkCode)){

            UserInfo userInfo = userService.selectByTel(map.get("tel"));

            if(userInfo!=null){

                return this.together(userInfo);

            }else{

                //没有此用户
                result.setCode(404);

                return result;
            }

        }else {

            //验证码错误
            result.setCode(500);

            return result;

        }

    }

    @PostMapping("sendEmail")
    @ApiOperation("这是接口类MainController中的接收前台邮箱方法")
    private ResponseResult sendEmail(@RequestBody Map<String , String> map){

        ResponseResult result = new ResponseResult();

        UserInfo userInfo = userService.selectUserByEmail(map.get("email"));

        if(userInfo!=null){

            //发送邮件
            MimeMessage message=mailSender.createMimeMessage();

            try {

                //true表示需要创建一个multipart message
                MimeMessageHelper helper=new MimeMessageHelper(message,true);

                helper.setFrom(from);

                helper.setTo(map.get("email"));

                helper.setSubject("密码重置");

                helper.setText("<html><head></head><body><h1>重置地址：</h1><br><a href='https://127.0.0.1:8080/view/found/foundPasswordSuccess'/>https://localhost:8080/view/found/foundPasswordSuccess</body></html>",true);

                mailSender.send(message);

                result.setCode(200);

                result.setResult(userInfo);

            }catch (Exception e){

                result.setCode(500);

            }

        }else{

            result.setCode(404);
        }

        return result;
    }


    @PostMapping("sendEmailRestat")
    @ApiOperation("这是接口类MainController中的接收前台邮箱方法")
    private ResponseResult sendEmailRestat(@RequestBody Map<String,String> map){

        ResponseResult result = new ResponseResult();

        Integer integer = userService.updateUserPasswordById(map.get("password"),map.get("userId"));

        if(integer > 0){

            //发送邮件

            result.setCode(200);


        }else{

            result.setCode(404);
        }

        return result;
    }


    private ResponseResult together(UserInfo userInfo) {

        ResponseResult result = new ResponseResult();

        String jsonString = JSON.toJSONString(userInfo);

        //用jwt将用户信息进行加密，加密用作token票据
        String generateToken = JWTUtils.generateToken(jsonString);

        //将加密信息存入返回实体
        result.setToken(generateToken);

        //将登陆用户的token票据存入Redis中
        redisTemplate.opsForValue().set("USERINFO"+userInfo.getId(),generateToken);

        //将登陆用户的权限存入Redis中
        if(userInfo.getAuthormap() != null){

            redisTemplate.delete("USERDATAAUTH"+userInfo.getId());

            redisTemplate.opsForHash().putAll("USERDATAAUTH"+userInfo.getId(),userInfo.getAuthormap());

        }

        //设置token的过期时间
        redisTemplate.expire("USERINFO"+userInfo.getId(),3000,TimeUnit.SECONDS);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

        String format = sdf.format(new Date());

        redisTemplate.opsForHash().increment("loginname",userInfo.getLoginname(),1L);

        String date = redisTemplate.opsForList().index("date", 0L);

        if(date==null){

            redisTemplate.opsForList().leftPush("date",format);

        }else {

            if(date.equals(format)){

                redisTemplate.opsForList().leftPop("date");

                redisTemplate.opsForList().leftPush("date",format);

            }else{

                redisTemplate.opsForList().leftPush("date", format);

                redisTemplate.delete("loginname");

                redisTemplate.opsForHash().increment("loginname",userInfo.getLoginname(),1L);

            }
        }

        int loginname = Integer.parseInt((String) redisTemplate.opsForHash().get("loginname", userInfo.getLoginname()));

        if(loginname==1){

            redisTemplate.opsForHash().increment("number",format,1L);

        }

        //设置返回实体
        result.setResult(userInfo);

        result.setCode(200);

        result.setSuccess("登陆成功！");

        return result;

    }

}
