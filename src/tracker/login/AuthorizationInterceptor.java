package tracker.login;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tracker.ApplicationProperties;
import utility.dbTable.Menu;
import utility.logger.AppLogger;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorizationInterceptor 
			 extends AbstractInterceptor
{
	private static final long serialVersionUID = 1L;
	private UserDetails ckUserDet;
    private static AppLogger log = (AppLogger) AppLogger.getLogger(AuthorizationInterceptor.class.getName());
    protected HttpServletRequest servletRequest;
    protected HttpServletResponse servletResponse;
    private ApplicationProperties properties;
	public ArrayList<Menu> userMenu;
	public String XMLMenu;

    private Map<String, Object> session;

    @SuppressWarnings("unchecked")
    public String intercept(ActionInvocation invocation) 
				  throws Exception
	{
		session = invocation.getInvocationContext().getSession();

		if((properties = (ApplicationProperties) session.get("properties")) == null)
    	{
    		properties = new ApplicationProperties();
    		session.put("properties", properties);
    	}
    	
		// Same checks are performed in LoginCheck action.
    	// proceed invoking if that is the action triggering
    	if (invocation.getAction().getClass().getName().compareTo("tracker.login.LoginCheck") == 0)
		{
			return invocation.invoke();
		}
		
		if((ckUserDet = (UserDetails) session.get("UserAuth")) != null) 
		{		
			userMenu = (ArrayList<Menu>) session.get("Menu");
			XMLMenu = (String) session.get("XMLMenu");
			if (XMLMenu != null)
			{
				return invocation.invoke();
			}
		}

		final ActionContext context = invocation.getInvocationContext();
		servletRequest = (HttpServletRequest) context.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
		servletResponse = (HttpServletResponse) context.get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);

		ckUserDet = null;
		log.debug("Credentials not available in session. Check if cookie is set");
		if (servletRequest.getCookies() != null)
		{
			for(Cookie c : servletRequest.getCookies())
			{
				log.debug("Cookie '" + c.getName() + "' found. Value '" + c.getValue() + "'");
				if (c.getName().equals("ckUserDet"))
				{
					log.debug("Cookie set, refreshing its expiry");
					c.setMaxAge(60*60*24*7); // Refresh the cookie for one more week
					servletResponse.addCookie(c);
					ckUserDet = UserDetails.deSerialize(c.getValue());
					break;
				}
			}
			if (ckUserDet != null)
			{
				log.debug("Valid cookie owned by client. Deserialize and get User Credentials");
				userMenu = new Menu().populateCollectionFromDB(ckUserDet);
				XMLMenu = Menu.generateXML(userMenu);
				session.put("UserAuth", ckUserDet);
				session.put("Menu", userMenu);
				session.put("XMLMenu", XMLMenu);			
				return invocation.invoke();
			}
		}
		if (invocation.getAction().getClass().getName().compareTo("tracker.login.LoginCheck") == 0)
		{
			return invocation.invoke();
		}
		else
		{			
			log.debug("No cookies available. Returning error to struts");
			addActionError(invocation, "You must be authenticated to access this page");
			return Action.LOGIN;
		}
	}

	private void addActionError(ActionInvocation invocation, String message) {
		Object action = invocation.getAction();
		if(action instanceof ValidationAware) {
			((ValidationAware) action).addActionError(message);
		}
	}

	public String getXMLMenu() 
	{
		return XMLMenu;
	}

	public ApplicationProperties properties() 
	{
		return properties;
	}

}
