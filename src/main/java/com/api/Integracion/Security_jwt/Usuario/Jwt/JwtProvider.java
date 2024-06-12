package com.api.Integracion.Security_jwt.Usuario.Jwt;

import com.api.Integracion.Security_jwt.Usuario.Util.UsuarioPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.el.ELException;
import java.util.Date;



@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        UsuarioPrincipal usuario =  (UsuarioPrincipal) authentication.getPrincipal();

        return Jwts.builder().setSubject(usuario.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    public String getNombreUsuarioFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {

        try {

            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
            
        } catch (MalformedJwtException e) {
            logger.error("token mal formado");

        } catch (UnsupportedJwtException e) {
            logger.error("token no soportado");

        } catch (ELException e) {
            logger.error("token expirado");
        } catch (IllegalArgumentException e) {
            logger.error("token vacio");
        }
        return false;
    }

}
