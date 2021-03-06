package com.crunch.crunch_server.util;

import java.security.Key;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {


    private Key key;
    private long validityInMilliseconds;

    UserDetailsService userDetailsService;

    public JwtUtil(String secret)
    {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityInMilliseconds = 72000000;

    }

    public String createToken(int id, String password)
    {
        Date now = new Date();
        Date validity = new Date(now.getTime()
            + validityInMilliseconds);


        String token = Jwts.builder()
            .claim("id", id)
            .claim("password", password)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        return token;
    }

    //find authentication info with JWT token
    public Authentication getAuthentication(String token)
    {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //get user Identity from the token
    public String getUserPk(String token)
    {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }

    //get user Identity from the token
    public int getUserId(String token)
    {
        return (int) Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().get("id");
    }

    //get token value from the request header
    public String resolveToken(HttpServletRequest request)
    {
       return request.getHeader("token");
    }

    public boolean validateToken(String jwtToken)
    {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
    }
}
