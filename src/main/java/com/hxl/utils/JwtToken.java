package com.hxl.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * JWT token 工具类
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/30 14:45
 */
@Component
public class JwtToken {

    private static String jwtKey;

    private static Integer expiredTimeIn;

    private static Integer defaultScope = 8;

    @Value("${ehree.security.jwt-key}")
    public void setJwykey(String jwtKey) {
        JwtToken.jwtKey = jwtKey;
    }

    @Value("${ehree.security.token-expired-in}")
    public void setExpiredTimeIn(Integer expiredTimeIn){
        JwtToken.expiredTimeIn = expiredTimeIn;
    }

    public static Optional<Map<String, Claim>> getClaims(String token) {
        Algorithm algorithm = Algorithm.HMAC256(token);
        // 解析验证 Token
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedjwt;
        try {
            decodedjwt = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
        return Optional.of(decodedjwt.getClaims());
    }

    // TODO 目前功能未知
    public static String makeToken(Long uid, Integer scope) {
        return JwtToken.getToken(uid, scope);
    }

    // TODO 目前功能未知
    public static String makeToken(Long uid) {
        return JwtToken.getToken(uid, JwtToken.defaultScope);
    }

    public static String getToken(Long uid, Integer scope) {
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
        Map<String, Date> map = JwtToken.calculateExpiredTimeIssues();
        return JWT.create()
                .withClaim("uid", uid)
                .withClaim("scope", scope)
                .withExpiresAt(map.get("expiredTime"))
                .withIssuedAt(map.get("now"))
                .sign(algorithm);
    }

    public static Boolean verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(token);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedjwt;
        try {
            decodedjwt = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    private static Map<String, Date> calculateExpiredTimeIssues() {
        Map<String, Date> map = new HashMap<>(4);
        Calendar calendar = Calendar.getInstance();
        Date nowDate = calendar.getTime();
        // 在现有的时间点上 + expiredTimeIn 这个时间【前面的参数是单位】
        calendar.add(Calendar.SECOND, JwtToken.expiredTimeIn);
        map.put("now", nowDate);
        map.put("expiredTime", calendar.getTime());
        return map;
    }

}
