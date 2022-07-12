package com.labs.lab5;

import com.labs.lab4.DBService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;

public class Authorizer extends Authenticator {
    private DBService dbservice;
    private byte[] secret;

    Authorizer(DBService service, byte[] secret) {
        this.dbservice = service;
        this.secret = secret;
    }


    @Override
    public com.sun.net.httpserver.Authenticator.Result authenticate(final HttpExchange httpExchange) {
        final String token = httpExchange.getRequestHeaders().getFirst("Authorization");
        System.out.println(token);
        if (token != null) {
            try {
                final String username = getUsernameFromToken(token);
                final User user = dbservice.getUser(username);
                if (user != null) {
                    return new Success(new HttpPrincipal(username, user.getLogin()));
                } else {
                    return new Retry(401);
                }
            } catch (Exception e) {
                return new Failure(401);
            }
        }
        return new Failure(403);
    }

    private String getUsernameFromToken(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(new SecretKeySpec(this.secret, SignatureAlgorithm.HS256.getJcaName())).parseClaimsJws(jwt).getBody();
        return claims.getSubject();
    }
}

