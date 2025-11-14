package fr.epsi.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.Cookie;

import fr.epsi.spring.util.JWTUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Order(1)
public class JwtFilter implements Filter {

     private static final Logger logger = LoggerFactory.getLogger(SecurityAspect.class);

    @Autowired
    private JWTUtil jwtUtil;

    public JwtFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        try {
            Cookie[] cookies = req.getCookies();
            String token = null;
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("token".equals(cookie.getName())) {
                            logger.info("le cookie token est trouvé");
                            token = cookie.getValue();
                            break;
                        }
                    }
                }
            
            if (token != null) {
                if (jwtUtil.isValid(token)) {
                    logger.info("le token est valide");
                    Map<String, Object> claims = jwtUtil.getClaims(token);
                    SecurityContext.setClaims(claims);
                }
            }else{
                logger.info("le cookie token est null donc pas trouvé");
            }

            chain.doFilter(request, response);

        } finally {
            SecurityContext.clear();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }
}