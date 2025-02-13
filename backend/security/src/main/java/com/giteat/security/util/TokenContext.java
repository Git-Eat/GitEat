package com.giteat.security.util;

public class TokenContext {
    private static final ThreadLocal<String> accessTokenThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> refreshTokenThreadLocal = new ThreadLocal<>();

    public static void setAccessToken(String accessToken) {
        accessTokenThreadLocal.set(accessToken);
    }

    public static String getAccessToken() {
        return accessTokenThreadLocal.get();
    }

    public static void removeAccessToken() {
        accessTokenThreadLocal.remove();
    }

    public static void setRefreshToken(String refreshToken) {
        refreshTokenThreadLocal.set(refreshToken);
    }

    public static String getRefreshToken() {
        return refreshTokenThreadLocal.get();
    }

    public static void removeRefreshToken() {
        refreshTokenThreadLocal.remove();
    }
}
