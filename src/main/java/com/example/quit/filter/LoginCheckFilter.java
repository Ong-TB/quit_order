package com.example.quit.filter;

import com.alibaba.fastjson.JSON;
import com.example.quit.common.BaseContext;
import com.example.quit.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        log.info("Filter: {}", request.getRequestURI());

        String uri = request.getRequestURI();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/login",
                "/user/sendMsg"
        };

        if(check(urls, uri)) {
            filterChain.doFilter(request, resp);
            return;
        }

        if (request.getSession().getAttribute("employee") != null) {
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));
            filterChain.doFilter(request, resp);
            return;
        }

        if(request.getSession().getAttribute("user") != null){
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("user"));
            filterChain.doFilter(request, resp);
            return;
        }

        resp.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    public boolean check(String[] urls, String uri) {
        for (String url : urls) {
            if(PATH_MATCHER.match(url, uri)){
                return true;
            }
        }
        return false;
    }
}
