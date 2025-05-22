//package com.chinasoft.authservice.filter;
//
//import com.chinasoft.authservice.utils.JWTUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//
//
//@Component
//public class JwtAuthenticationFilter implements WebFilter {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        // 如果请求路径是登录或注册，则直接放行
//        if (request.getURI().getPath().contains("/register") || request.getURI().getPath().contains("/authorized")) {
//            return chain.filter(exchange);
//        }
//
//        // 获取 Authorization 请求头
//        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            // 如果没有 Token 或格式不正确，返回 401 未授权
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        String token = authHeader.substring(7); // 提取 Token
//        try {
//            // 解析 Token 并获取声明
//            JWTUtils.verify(token);
//        } catch (Exception e) {
//            // 如果解析失败，返回 401 未授权
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        // 继续处理请求
//        return chain.filter(exchange);
//    }
//}