//package com.giteat.common.config;
//
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//
//import javax.crypto.spec.SecretKeySpec;
//import java.security.Key;
//import java.util.Base64;
//
//public class JwtConfig {
//    // application.properties에서 jwt.secret 값을 가져온다
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    /**
//     *
//     * JWT 토큰을 만들 때 사용할 암호화 키 생성
//     *
//     * @return 암호화된 키 객체
//     */
//
//    @Bean
//    public Key key() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        // HMAC-SHA 알고리즘용 키 생성
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//}
