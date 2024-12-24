package com.mansa.user.Security;

import com.mansa.user.Entities.Role;
import com.mansa.user.Entities.User;
import com.mansa.user.Util.KeyLoader;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${token.signing.key}")
    private String secretKey;

    @Value("${token.signing.private-key}")
    private String privateKeyPath;

    @Value("${token.signing.public-key}")
    private String publicKeyPath;


    @Value("${token.expiration.access-token}")
    private long jwtExpiration;

    @Value("${token.expiration.refresh-token}")
    private long refreshExpiration;
    private final KeyLoader keyLoader;

//    @PostConstruct
//    public void init() throws Exception {
//        this.privateKey = keyLoader.loadPrivateKey(privateKeyPath);
//        this.publicKey = keyLoader.loadPublicKey(publicKeyPath);
//    }


    private String createToken(Map<String, Object> claims, String username, long expirationMillis) throws Exception {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(keyLoader.loadPrivateKey(privateKeyPath), SignatureAlgorithm.RS256)
                .compact();
    }

    public String generateAccessToken(User user) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        Set<String> roles = user.getRoles().stream()
                .map(Role::getRole)
                .collect(Collectors.toSet());
        claims.put("role", roles);
        return createToken(claims, user.getEmail(), jwtExpiration);
    }

    public String generateRefreshToken(User user) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        Set<String> roles = user.getRoles().stream()
                .map(Role::getRole)
                .collect(Collectors.toSet());
        claims.put("role", roles);
        return createToken(claims, user.getEmail(), refreshExpiration);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) throws Exception {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws Exception {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws Exception {
        return Jwts
                .parserBuilder()
                .setSigningKey(keyLoader.loadPublicKey(publicKeyPath))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) throws Exception {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) throws Exception {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) throws Exception {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateEmailVerificationToken(String id){
        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+3600000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateInvitationVerificationToken(String id,String type){
        return Jwts.builder()
                .setSubject(id)
                .claim("type",type)
                .setIssuedAt(new Date())
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


}