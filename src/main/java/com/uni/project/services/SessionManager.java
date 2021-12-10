package com.uni.project.services;

import javax.servlet.http.HttpServletRequest;

public class SessionManager {
    private static final String SESSION_USER_KEY = "USERNAME";
    private static final String SESSION_USER_ROLE_KEY = "ROLE";

    public static void LoginUser(HttpServletRequest request, String username, Integer role) {
        request.getSession().setAttribute(SESSION_USER_KEY, username);
        request.getSession().setAttribute(SESSION_USER_ROLE_KEY, role);
    }

    public static Boolean IsLogged(HttpServletRequest request) {
        return request.getSession().getAttribute(SESSION_USER_KEY) != null;
    }

    public static void LogoutUser(HttpServletRequest request) {
        request.getSession().removeAttribute(SESSION_USER_KEY);
        request.getSession().removeAttribute(SESSION_USER_ROLE_KEY);
    }

    public static Boolean IsAdmin(HttpServletRequest request) {
        var key = request.getSession().getAttribute(SESSION_USER_ROLE_KEY);
        return key != null && Integer.parseInt(key.toString()) == 1;
    }

    public static String GetUsername(HttpServletRequest request) {
        return request.getSession().getAttribute(SESSION_USER_KEY).toString();
    }
}
