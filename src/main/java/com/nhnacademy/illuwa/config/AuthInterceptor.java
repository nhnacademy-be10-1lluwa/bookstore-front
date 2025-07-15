package com.nhnacademy.illuwa.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && modelAndView.getViewName() != null) {

            boolean isLoggedIn = false;
            boolean isAdmin = false;

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("ACCESS_TOKEN".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isEmpty()) {
                        isLoggedIn = true;
                        break;
                    }
                }
            }

            if (isLoggedIn) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication != null && authentication.isAuthenticated() &&
                        !"anonymousUser".equals(authentication.getPrincipal())) {

                    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                    for (GrantedAuthority authority : authorities) {
                        if (authority.getAuthority().equals("ROLE_ADMIN")) {
                            isAdmin = true;
                            break;
                        }
                    }
                }
            }

            if (modelAndView != null
                    && modelAndView.getViewName() != null
                    && !modelAndView.getViewName().startsWith("redirect:")) {
                modelAndView.addObject("isLoggedIn", isLoggedIn);
                modelAndView.addObject("isAdmin", isAdmin);
            }
        }
    }
}
