package com.caogang.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: xiaogang
 * @date: 2019/8/6 - 8:39
 */
@Component
public class myGatewayFilter implements GlobalFilter {

    @Value("${gateway.urls}")
    private String[] urls;

    @Value("${gateway.loginPath}")
    private String loginPath;

    @Autowired
    private RedisTemplate<String , String> redisTemplate;


    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

//        ServerHttpRequest request = exchange.getRequest();
//
//        ServerHttpResponse response = exchange.getResponse();
//
//        //请求的绝对路径
//        String uri0=request.getURI().toString();
//
//        //请求的相对路径
//        String uri = request.getPath().value();
//
//        System.out.println("*******"+uri0+"*******");
//
//        System.out.println("*******"+uri+"*******");
//
//        List<String> strings = Arrays.asList(urls);
//
//        if(strings.contains(uri0)){
//
//            return chain.filter(exchange);
//
//        }else{
//
//            List<String> tokens = request.getHeaders().get("token");
//
//            JSONObject jsonObject = null;
//
//            try {
//
//                jsonObject = JWTUtils.decodeJwtTocken(tokens.get(0));
//
//                String token = JWTUtils.generateToken(jsonObject.toJSONString());
//
//                response.getHeaders().set("token",token);
//
//            } catch (JwtException e) {
//
//                e.printStackTrace();
//
//                response.getHeaders().set("loginPath",loginPath);
//
//                response.setStatusCode(HttpStatus.SEE_OTHER);
//
//                return exchange.getResponse().setComplete();
//            }
//
//            Boolean flag = redisTemplate.opsForHash().hasKey("USERDATAAUTH" + jsonObject.get("id"), uri);
//
//            if(flag){
//
//                return chain.filter(exchange);
//
//            }else{
//
//                throw new RuntimeException("不能访问该资源！");
//            }
//        }

        return chain.filter(exchange);
    }


}
