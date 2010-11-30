package eu.ist.fears.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.ist.fears.common.FearsConfigClient;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = -5995338140779095898L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String url;
	if (FearsConfigServer.isRunningInProduction()) {
	    url = PropertiesManager.getProperty("production.login.url") + "?";
	} else {
	    url = "Login.html?";
	}
	response.sendRedirect(url + "service=" + request.getParameter("service"));
    }
}
