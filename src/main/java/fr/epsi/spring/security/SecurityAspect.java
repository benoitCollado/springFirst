package fr.epsi.spring.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


import fr.epsi.spring.security.annotation.*;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class SecurityAspect {

    private static final Logger logger = LoggerFactory.getLogger(SecurityAspect.class);

    @Before("@annotation(authenticated)")
    public void checkAuthenticated(Authenticated authenticated) {
        if (SecurityContext.getClaims() == null) {
               logger.info("pas de claims");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilisateur non authentifié");
        }
    }

    @Before("@annotation(hasRole)")
    public void checkRole(JoinPoint joinPoint, HasRole hasRole) {
        Map<String, Object> claims = SecurityContext.getClaims();
        if (claims == null) {
            logger.info("pas de claims");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilisateur non authentifié");
        }
        String userRole = (String) claims.get("role");
        if (!hasRole.value().equals(userRole)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Rôle insuffisant");
        }
    }
}
