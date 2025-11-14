package fr.epsi.spring.security;

import java.util.Map;


public class SecurityContext {

    private static final ThreadLocal<Map<String, Object>> context = new ThreadLocal<>();

    public static void setClaims(Map<String, Object> claims) {
        context.set(claims);
    }

    public static Map<String, Object> getClaims() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
