package tracker.login;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;


import com.opensymphony.xwork2.ActionSupport;

public class Logout extends ActionSupport
			 implements SessionAware, ServletResponseAware
{
	private static final long serialVersionUID = 1L;
	protected HttpServletResponse servletResponse;   

	private Map<String, Object>  session;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setSession(Map session)
	{
		// session = this.getSession();
		this.session = session;
	}
	// For access to the raw servlet request / response, eg for cookies   
	public void setServletResponse(HttpServletResponse servletResponse) 
	{
		this.servletResponse = servletResponse;
	}

	public String execute() throws Exception 
	{
		Cookie logonDet = new Cookie("ckUserDet", "");
		logonDet.setMaxAge(0); // Make the cookie last one week
		servletResponse.addCookie(logonDet);
		session.remove("UserAuth");
		session.remove("Menu");
		session.remove("XMLMenu");
		return "success";
	}

}
