//package com.farmersbyte.enterprise.usermodule.user.security;
//
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import java.security.SecureRandom;
//import java.util.Base64;
//import java.util.Date;
//import java.util.Optional;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class JWTUtil {
//
//    private static final String ISSUER = "com.farmersbyte.enterprise";
//    private static final String SECRET = "AaR6ygrYojKD986hM331PH8mfWDw1rfWOpBSq3FSb9P9lDSPlBC29D90WNLVBVb";
//
//    public static Optional<String> createJwt(SecurityContext context) {
//        long now = System.currentTimeMillis();
//        Algorithm algo = Algorithm.HMAC256(getSecret());
//        Date resultDate = new Date(now + context.getTokenValidity());
//        String token = JWT.create()
//            .withIssuer(ISSUER)
//            .withExpiresAt(resultDate)
//            .withSubject(context.getUserName())
//            .withClaim("aud", context.getOrganizationHostName())
//            .withClaim("rtv", context.getRefreshTokenValidity())
//            .withClaim("non", context.getNonce())
//            .withClaim("zid", context.getZid())
//            .withClaim("spid", context.getUserId())
//            .withClaim("aid", context.getAid())
//            .withClaim("uid", context.getUserId())
//            .withClaim("locale", context.getLocale())
//            .withClaim("language", context.getLanguage())
//            .withClaim("organizationId", context.getOrganizationId())
//            .sign(algo);
//        return Optional.of(token);
//    }
//
//    public static Optional<String> createJwtFromContext(SecurityContext context) {
//        return JWTUtil.createJwt(context);
//    }
//
//    public static boolean validateCsrfToken(String inputJwt, String csrfToken) {
//        DecodedJWT decodedJWT = JWT.decode(inputJwt);
//        return decodedJWT.getClaim("non").asString().equals(csrfToken);
//    }
//
//    public static String getCsrfTokenFromJwt(String inputJwt) {
//        DecodedJWT decodedJWT = JWT.decode(inputJwt);
//        return decodedJWT.getClaim("non").asString();
//    }
//
//    public static String getOrganizationIdFromJwt(String inputJwt) {
//        DecodedJWT decodedJWT = JWT.decode(inputJwt);
//        return decodedJWT.getClaim("zid").asString();
//    }
//
//    public static boolean verifyJwt(String token) {
//        Algorithm algorithm = Algorithm.HMAC256(getSecret());
//        JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
//        DecodedJWT jwt = verifier.verify(token);
//        Optional<DecodedJWT> result = Optional.of(jwt);
//        return result.isPresent();
//    }
//
//    public static Optional<DecodedJWT> validateJwt(String token, OrganizationContext organizationContext) {
//        log.info("validateJwt before instantiating Algorithm");
//        Algorithm algorithm = Algorithm.HMAC256(getSecret());
//        log.info("validateJwt before instantiating verifier with algo and issuer");
//        JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
//        log.info("validateJwt before verifying jwt");
//        DecodedJWT jwt = verifier.verify(token);
//        log.info("validateJwt after verify jwt");
//        Optional<DecodedJWT> result = Optional.of(jwt);
//        if (result.isEmpty()) {
//            log.info("returning empty result");
//            return result;
//        }
//        if (jwt.getClaim("aud").asString().equals(organizationContext.getHostName())) {
//            log.info("returning jwt after audience check");
//            return result;
//        }
//        log.info("returning empty result ... ");
//        return Optional.empty();
//    }
//
//    public static SecurityContext createJwtTokenContext(DecodedJWT inputJwt) {
//        return SecurityContext.builder()
//                .userName(inputJwt.getSubject())
//                .refreshTokenValidity(inputJwt.getClaim("rtv").asInt())
//                .nonce(inputJwt.getClaim("non").asString())
//                .zid(inputJwt.getClaim("zid").asString())
//                .organizationId(inputJwt.getClaim("organizationId").asString())
//                .aid(inputJwt.getClaim("aid").asString())
//                .userId(inputJwt.getClaim("uid").asString())
//                .locale(inputJwt.getClaim("locale").asString())
//                .language(inputJwt.getClaim("language").asString())
//                .organizationHostName(inputJwt.getClaim("aud").asString())
//                .build();
//    }
//
//    public static boolean isJwtValid(String token, OrganizationContext organizationContext) {
//        Optional<DecodedJWT> jwt = validateJwt(token, organizationContext);
//
//        return jwt.isPresent();
//    }
//
//    public static String getSecret() {
//        return SECRET;
//    }
//
//    public static String generateSecureRandomString() {
//        SecureRandom random = new SecureRandom();
//        byte[] bytes = new byte[20];
//        random.nextBytes(bytes);
//        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
//        return encoder.encodeToString(bytes);
//    }
//
//    private JWTUtil() {}
//}