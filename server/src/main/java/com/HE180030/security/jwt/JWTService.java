package com.HE180030.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.logging.Logger;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class JWTService {
    final Logger logger = Logger.getLogger(this.getClass().getName());

    @Value("${spring.app.jwtExpirationMs}")
    long jwtExpirationMs;

    @Value("${spring.app.signerKey}")
    String signer;

    public String createAccessToken(String userID) throws Exception {
        logger.info("Creating access token for userID: " + userID);
        return generateToken(userID, "access");
    }

    public String createRefreshToken(String userID) throws Exception {
        logger.info("Creating refresh token for userID: " + userID);
        return generateToken(userID, "refresh");
    }

    public JWTClaimsSet validateToken(String token) throws Exception {
        logger.info("Validating token: " + token);
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(signer);
        if (!signedJWT.verify(verifier)) {
            throw new Exception("Token signature invalid");
        }

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (new Date().after(expirationTime)) {
            throw new Exception("Token expired");
        }

        return signedJWT.getJWTClaimsSet();
    }

    private String generateToken(String userID, String type) throws JOSEException {
        logger.info("Generating access token for userID: " + userID);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userID)
                .issuer("your-app")
                .expirationTime(Date.from(Instant.now().plusSeconds(jwtExpirationMs)))
                .issueTime(Date.from(Instant.now()))
                .claim("typ", type)
                .build();

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        signedJWT.sign(new MACSigner(signer.getBytes()));
        return signedJWT.serialize();
    }
}
