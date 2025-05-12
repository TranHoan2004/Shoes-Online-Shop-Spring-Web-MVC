package com.HE180030.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class JWTService {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Value("${spring.app.jwtExpirationMs}")
    private long jwtExpirationMs;

    @Value("${spring.app.signerKey}")
    private String signer;

    public String createAccessToken(String userID) throws Exception {
        return generateToken(userID, "access");
    }

    public String createRefreshToken(String userID) throws Exception {
        return generateToken(userID, "refresh");
    }

    public JWTClaimsSet validateToken(String token) throws Exception {
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
