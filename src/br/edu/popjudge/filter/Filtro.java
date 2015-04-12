package br.edu.popjudge.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.popjudge.bean.TimerBean;



@WebFilter (servletNames={"Faces Servlet"})
public class Filtro implements Filter{
	
	@Override
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException , ServletException {
		HttpServletRequest req = ( HttpServletRequest ) request ;
		HttpSession session = req.getSession() ;
		String usuario = (String) session.getAttribute("login");		
		
		if (session.getAttribute("login") != null &&
				!usuario.equals("Admin") &&
		    	((TimerBean.validaHorario() 
				&& !req.getRequestURI().endsWith("index.xhtml") 
				&& !req.getRequestURI().endsWith("Judge/")
				&& !req.getRequestURI().contains("admin"))
				||(!TimerBean.validaHorario()
				&& !req.getRequestURI().endsWith("index.xhtml") 
				&& !req.getRequestURI().endsWith("Judge/")
				&& !req.getRequestURI().contains("admin")
				&& !req.getRequestURI().contains("submit") 
				&& !req.getRequestURI().contains("standings")
				&& !req.getRequestURI().contains("clarifications")
				&& !req.getRequestURI().contains("problems"))
				|| req.getRequestURI().contains("javax.faces.resource"))) {
			chain.doFilter( request, response );
		} else {
			
			if(session.getAttribute("login") != null &&
					usuario.equals("Admin") &&
					(!req.getRequestURI().endsWith("index.xhtml") 
					&& !req.getRequestURI().endsWith("Judge/")
					&& req.getRequestURI().contains("admin")
					|| req.getRequestURI().contains("javax.faces.resource"))){
				chain.doFilter( request, response );
			} else {
				
				if(session.getAttribute("login") == null &&
						(req.getRequestURI().endsWith("index.xhtml")
						|| req.getRequestURI().endsWith("Judge/")
						|| req.getRequestURI().contains("javax.faces.resource"))){
					chain.doFilter( request, response );				
				} else {
					HttpServletResponse res = ( HttpServletResponse ) response ;
					
					if(session.getAttribute("login") == null)
						res.sendRedirect("/POP-Judge/");
					if(session.getAttribute("login") != null && !usuario.equals("Admin"))
			    		res.sendRedirect("/POP-Judge/webapp/user/indexUser.xhtml");
					if(session.getAttribute("login") != null && usuario.equals("Admin"))
			    		res.sendRedirect("/POP-Judge/webapp/admin/indexAdmin.xhtml");
				}

			}

		}
	}
		
	@Override
	public void init ( FilterConfig filterConfig ) throws ServletException {
	}
	
	@Override
	public void destroy () {
	}
}

