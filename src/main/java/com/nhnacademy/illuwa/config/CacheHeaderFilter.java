package com.nhnacademy.illuwa.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CacheHeaderFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String cacheControl = "public, max-age=" + TimeUnit.MINUTES.toSeconds(5);
        httpServletResponse.setHeader("Cache-Control", cacheControl);

        httpServletResponse.addHeader("Vary", "Accept-Encoding, Cookie");


        chain.doFilter(request, response);
    }
}
