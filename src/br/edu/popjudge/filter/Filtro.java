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
import br.edu.popjudge.domain.User;



@WebFilter (servletNames={"Faces Servlet"})
public class Filtro implements Filter{
	
	@Override
	public void doFilter (ServletRequest request, ServletResponse response, 
			FilterChain chain ) throws IOException , ServletException {
		
		HttpServletRequest req = ( HttpServletRequest ) request ;
		HttpSession session = req.getSession() ;
		
		String username = null;
		
		if ((User) session.getAttribute("user") != null)
			username = ((User) session.getAttribute("user")).getUsername();
			
		
		if (username != null &&
				!username.equals("Admin") &&
		    	((TimerBean.validaHorario() 
		    			&& !req.getRequestURI().contains("signUp")
		    			&& !req.getRequestURI().endsWith("index.xhtml") 
		    			&& !req.getRequestURI().endsWith("Judge/")
		    			&& !req.getRequestURI().contains("admin"))
		    	||(!TimerBean.validaHorario()
		    			&& !req.getRequestURI().contains("signUp")
		    			&& !req.getRequestURI().endsWith("index.xhtml") 
		    			&& !req.getRequestURI().endsWith("Judge/")
		    			&& !req.getRequestURI().contains("admin")
		    			&& !req.getRequestURI().contains("submit") 
		    			&& !req.getRequestURI().contains("standings")
		    			&& !req.getRequestURI().contains("clarifications")
		    			&& !req.getRequestURI().contains("problems"))
				|| req.getRequestURI().contains("resource"))) {
			
			chain.doFilter( request, response );
			
		} else {
			
			if(username != null &&
					username.equals("Admin") &&
					(!req.getRequestURI().endsWith("index.xhtml") 
					&& !req.getRequestURI().endsWith("Judge/")
					&& !req.getRequestURI().contains("signUp")
					&& req.getRequestURI().contains("admin")
					|| req.getRequestURI().contains("javax.faces.resource"))){
				
				chain.doFilter( request, response );
			} else {
				
				if(username == null &&
						(req.getRequestURI().endsWith("index.xhtml")
						|| req.getRequestURI().endsWith("Judge/")
						|| req.getRequestURI().contains("signUp")
						|| req.getRequestURI().contains("javax.faces.resource"))){
					
					chain.doFilter( request, response );				
				} else {
					
					HttpServletResponse res = ( HttpServletResponse ) response ;
					
					if(username == null)
						res.sendRedirect("/POP-Judge/");
					if(username != null && !username.equals("Admin"))
			    		res.sendRedirect("/POP-Judge/webapp/user/indexUser.xhtml");
					if(username != null && username.equals("Admin"))
			    		res.sendRedirect("/POP-Judge/webapp/admin/indexAdmin.xhtml");
				}

			}

		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}

