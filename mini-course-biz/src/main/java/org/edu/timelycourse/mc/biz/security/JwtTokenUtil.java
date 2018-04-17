package org.edu.timelycourse.mc.biz.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by x36zhao on 2018/4/9.
 */
@Component
public class JwtTokenUtil implements Serializable
{
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Clock clock = DefaultClock.INSTANCE;

    public JwtTokenUtil () {}

    public JwtTokenUtil (String secret, Long expiration)
    {
        this.secret = secret;
        this.expiration = expiration;
    }

    public Boolean validateToken(String token, final UserDetails userDetails)
    {
        final JwtUser user = (JwtUser) userDetails;
        final JwtUser claims = getUserDetailsFromToken(token);
        //final Date created = getIssuedAtDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);
        return user.getPhone().equals(claims.getPhone()) && !isTokenExpired(token);
    }

    public String generateToken(JwtUser userDetails)
    {
        Map<String, Object> claims = new HashMap<>();
        try
        {
            return doGenerateToken(claims, new ObjectMapper().writeValueAsString(userDetails));
        }
        catch (JsonProcessingException ex)
        {
            throw new RuntimeException(String.format("Failed to convert user details %s to json string", userDetails));
        }
    }

    public String refreshToken(String token)
    {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean isTokenExpired(String token)
    {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private Claims getAllClaimsFromToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpirationDateFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Date getIssuedAtDateFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    private Date calculateExpirationDate(Date createdDate)
    {
        return new Date(createdDate.getTime() + expiration * 1000);
    }

    public String getUsernameFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public JwtUser getUserDetailsFromToken (String token)
    {
        try
        {
            String claim = getClaimFromToken(token, Claims::getSubject);
            JwtUser jwtUser = new ObjectMapper().readValue(claim, JwtUser.class);
            jwtUser.setToken(token);
            return jwtUser;
        }
        catch (IOException ex)
        {
            throw new RuntimeException(String.format(
                    "Failed to get user details from token: %s", token), ex);
        }
    }

    private String doGenerateToken(Map<String, Object> claims, String subject)
    {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
