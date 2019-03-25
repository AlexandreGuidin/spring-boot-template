package com.springboot.template.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private JwtUtils() {
    }

    public static String createJwtToken(Instant expiration, String secret, Map<String, String> claims) {
        JWTCreator.Builder builder = JWT.create();

        builder.withExpiresAt(Date.from(expiration));
        claims.forEach(builder::withClaim);

        return builder.sign(Algorithm.HMAC256(secret));
    }

    public static DecodedJWT decodeToken(String token, String secret) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);

        } catch (TokenExpiredException e) {
            logger.error(String.format("JwtUtils.decodeToken token expired message = %s ", e.getMessage()));
            return null;
        } catch (Exception e) {
            logger.error("JwtUtils.decodeToken error decoding token", e);
            return null;
        }
    }
}
