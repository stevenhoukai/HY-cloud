package com.yyicbc.utils;

import com.yyicbc.beans.security.SysUserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@ConfigurationProperties("jwt.config")
public class JwtUtil {

    private String key;

    private long ttl;//一天

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * 生成JWT
     *
     * @param id
     * @param subject
     * @return
     */
    public String createJWT(String id, String usercode, List<String> menus,Integer usertype) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(usercode)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key)
                .claim("menus", JsonUtils.objectToJson(menus))
                .claim("usercode", usercode);
//        if("root".equals(usercode)||"admin".equals(usercode)){
//            builder.claim("role","admin");
//        }else{
//            builder.claim("role","user");
//        }
        if(usertype==1){
            builder.claim("role","admin");
        }else{
            builder.claim("role","user");
        }
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }
}