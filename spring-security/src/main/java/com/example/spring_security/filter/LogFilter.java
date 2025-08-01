package com.example.spring_security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");

        // 기능이 별로 없어서 다운캐스팅
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();

        String uuid = httpServletRequest.getHeader("uuid");
        try {
            log.info("requestURI={}, uuid={}", requestURI, uuid);
            chain.doFilter(request, response); // 다음필터 호출, 다음 필터가 없으면 서블릿을 호출
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            log.info("log filter doFilter end");
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
