package org.jsondoc.core.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alessio on 05/06/15.
 */

@Component
public class CorsFilter implements Filter {

    private boolean corsEnabled;

    public boolean isCorsEnabled() {
        return corsEnabled;
    }

    public void setCorsEnabled(boolean corsEnabled) {
        this.corsEnabled = corsEnabled;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String corsValue = filterConfig.getInitParameter("corsEnabled");

        if (!corsValue.isEmpty()) {
            setCorsEnabled(corsValue.equals("true") ? true : false);
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        if (corsEnabled) {
            HttpServletResponse response = (HttpServletResponse) res;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "*");

        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() { }
}
