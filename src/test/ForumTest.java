package test;

import java.util.HashMap;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.views.JspSupportServlet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;
import static org.junit.Assert.*;

import tracker.login.LoginCheck;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionProxyFactory;

/**
 * updated to JUnit 4. 
 * @author Zarar Siddiqi
 * http://depressedprogrammer.wordpress.com/2007/06/18/unit-testing-struts-2-actions-spring-junit/ 
 */  

public class ForumTest {
	private static final String CONFIG_LOCATIONS="file:WebContent/WEB-INF/applicationContext.xml";
    private static ApplicationContext applicationContext;
    private Dispatcher dispatcher;
    protected ActionProxy proxy;
    protected static MockServletContext servletContext;
    protected static MockServletConfig servletConfig;
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;

    @SuppressWarnings("unchecked")
    protected <T> T createAction(Class<T> clazz,String namespace,String name) throws Exception {
        proxy=dispatcher.getContainer().getInstance(ActionProxyFactory.class).
        	createActionProxy(namespace,name,null,true,false);
        proxy.getInvocation().getInvocationContext().setParameters(new HashMap());
        proxy.setExecuteResult(true);
        ServletActionContext.setContext(proxy.getInvocation().getInvocationContext());
        request=new MockHttpServletRequest();
        response=new MockHttpServletResponse();
        ServletActionContext.setRequest(request);
        ServletActionContext.setResponse(response);
        ServletActionContext.setServletContext(servletContext);
        return (T) proxy.getAction();
    }

    @Before
    public void create() throws Exception {
    	if(applicationContext==null) {
    		servletContext=new MockServletContext();
    		servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM,CONFIG_LOCATIONS);
    		applicationContext=(new ContextLoader()).initWebApplicationContext(servletContext);
    		new JspSupportServlet().init(new MockServletConfig(servletContext));
        }
        HashMap<String,String> params=new HashMap<String,String>();
        params.put("actionPackages","com.onepd.struts2.lesson"); 
        dispatcher=new Dispatcher(servletContext,params);
        dispatcher.init();
        Dispatcher.setInstance(dispatcher);
    }
    
	@Test
	public void InterceptorsBySettingDomainObjects() throws Exception
	{
		LoginCheck forum=createAction(LoginCheck.class,"/","forum");
		/*
		forum.setName("1");
		forum.setMessage("1");
		*/
		String result=proxy.execute();
		assertEquals(result,"success");
	}

	@Test
	public void InterceptorsBySettingRequestParameters() throws Exception
	{
		createAction(LoginCheck.class,"/","forum");
		request.addParameter("name","2");
		request.addParameter("message","2");
		String result=proxy.execute();
		assertEquals(result,"success");
	}

	@Test
	public void ActionAndSkipInterceptors() throws Exception {
		LoginCheck forum=createAction(LoginCheck.class,"/","forum");
		/*
		forum.setName("3");
		forum.setMessage("3");
		*/
		String result=forum.execute();
		assertEquals(result,"success");
	}
}
