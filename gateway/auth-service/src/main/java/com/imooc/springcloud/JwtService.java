package com.imooc.springcloud;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtService {

    //生产环境不能这么用
    private static final String KEY= "changeIt";

    private static final String ISSUER= "zh";

    private static final long TOKEN_EXP_TIME= 60000;

    private static final String USER_NAME= "userName";


    /**
     * 生产token
     * @param account
     * @return
     */
    public String token(Account account){
        Date now = new Date();

        Algorithm algorithm = Algorithm.HMAC256(KEY);

        String token = JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + TOKEN_EXP_TIME))
                .withClaim(USER_NAME, account.getUserName())
                .sign(algorithm);

        log.info("jwt generated user = {}", account.getUserName());
        return token;
    }

    /**
     * 校验token
     * @param token
     * @param userName
     * @return
     */
    public boolean verify(String token, String userName){
        log.info("verifying jwt - username = {}", userName);

        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .withClaim(USER_NAME,userName)
                    .build();

            verifier.verify(token);
            return true;
        }catch (Exception e){
            log.error("auth failed", e);
            return false;
        }
    }
}
