package org.xman;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xman
 */
public class UeditorServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String data = request.getParameter("data");

        String realPath = request.getRealPath("/");
        FreemarkerHelper helper = new FreemarkerHelper();

        System.out.println(data);
    }


    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
