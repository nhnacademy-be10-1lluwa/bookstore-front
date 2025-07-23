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

        if (modelAndView != null && modelAndView.getViewName() != null && !modelAndView.getViewName().startsWith("redirect:")) {

            boolean isLoggedIn = false;
            boolean isAdmin = false;

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {

                isLoggedIn = true;

                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                for (GrantedAuthority authority : authorities) {
                    if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                        isAdmin = true;
                        break;
                    }
                }
            }

            modelAndView.addObject("isLoggedIn", isLoggedIn);
            modelAndView.addObject("isAdmin", isAdmin);
        }
    }
}
