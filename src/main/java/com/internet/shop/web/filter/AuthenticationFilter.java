package com.internet.shop.web.filter;

import com.internet.shop.lib.Injector;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {
    private static final String DRIVER_ID = "id";
    private static final Injector injector = Injector.getInstance("com.internet.shop");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String url = req.getServletPath();
        if (url.equals("/drivers/login") || url.equals("/drivers/create")) {
            chain.doFilter(req, resp);
            return;
        }
        Long driverId = (Long) req.getSession().getAttribute(DRIVER_ID);
        if (driverId == null) {
            resp.sendRedirect(req.getContextPath() + "/drivers/login");
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}
