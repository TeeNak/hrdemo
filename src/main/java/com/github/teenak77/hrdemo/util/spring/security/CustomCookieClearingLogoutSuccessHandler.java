package com.github.teenak77.hrdemo.util.spring.security;

import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import java.io.IOException;

/**
 * Created by teenak77 on 2015/05/01.
 * Workaround of https://jira.spring.io/browse/SEC-2091
 * See more details here.
 * http://stackoverflow.com/questions/3112972/spring-security-customize-logout-handler
 */
public class CustomCookieClearingLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    public static final String JSESSIONID = "JSESSIONID";

    public CustomCookieClearingLogoutSuccessHandler(String defaultTargetURL) {
        this.setDefaultTargetUrl(defaultTargetURL);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication)
            throws IOException, ServletException {

        clearCookies(request, response);

        super.onLogoutSuccess(request, response, authentication);

    }

    private void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookieWithSlash = new Cookie(JSESSIONID, null);
        //Tomcat adds extra slash at the end of context path (e.g. "/foo/")
        cookieWithSlash.setPath(request.getContextPath() + "/");
        cookieWithSlash.setMaxAge(0);

        Cookie cookieWithoutSlash = new Cookie(JSESSIONID, null);
        //JBoss doesn't add extra slash at the end of context path (e.g. "/foo")
        cookieWithoutSlash.setPath(request.getContextPath());
        cookieWithoutSlash.setMaxAge(0);

        //Remove cookies on logout so that invalidSessionURL (session timeout) is not displayed on proper logout event
        response.addCookie(cookieWithSlash); //For cookie added by Tomcat
        response.addCookie(cookieWithoutSlash); //For cookie added by JBoss
    }
}