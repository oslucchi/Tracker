package tracker.login;

import javax.naming.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.forgerock.opendj.ldap.LDAPConnectionFactory;
import org.forgerock.opendj.ldap.SearchScope;
import org.forgerock.opendj.ldap.responses.BindResult;
import org.forgerock.opendj.ldap.responses.SearchResultEntry;
import org.forgerock.opendj.ldif.ConnectionEntryReader;

import tracker.ApplicationProperties;
import utility.dbTable.Menu;
import utility.logger.AppLogger;

public class LoginCheck extends ActionSupport
						implements SessionAware, ServletResponseAware, ServletRequestAware 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static AppLogger log = (AppLogger) AppLogger.getLogger(Menu.class.getName());
	private String username;
	private String password;
	private	String idType;
	private static int SEARCH_PRI_LDAP = 1;
	private static int SEARCH_ALT_LDAP = 2;
	private UserDetails userDet;
	private ApplicationProperties properties;
	public ArrayList<Menu> userMenu;
	public String XMLMenu;

	public String getXMLMenu() {
		return XMLMenu;
	}

	protected HttpServletResponse servletResponse;   
	protected HttpServletRequest servletRequest;

	private Map<String, Object> session;
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

	public void setServletRequest(HttpServletRequest servletRequest) 
	{
		this.servletRequest = servletRequest;
	}

	@SuppressWarnings("unchecked")
	public String execute() throws Exception 
	{
		properties = (ApplicationProperties) session.get("properties");
		
		if (Authenticate())
		{
			// Save to cookie
			Cookie logonDet = new Cookie("ckUserDet", userDet.serialize());
			logonDet.setMaxAge(60*60*24*7); // Make the cookie last one week
			servletResponse.addCookie(logonDet);
			session.put("UserAuth", userDet);
			userMenu = new Menu().populateCollectionFromDB(userDet);
			session.put("Menu", userMenu);
			XMLMenu = Menu.generateXML(userMenu);
			session.put("XMLMenu", XMLMenu);
			return Action.SUCCESS;
		}
		else
		{
			UserDetails ckUserDet;
			if((ckUserDet = (UserDetails) session.get("UserAuth")) != null) 
			{		
				userMenu = (ArrayList<Menu>) session.get("Menu");
				XMLMenu = (String) session.get("XMLMenu");
				if (XMLMenu != null)
				{
					return Action.SUCCESS;
				}
			}

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
					return Action.SUCCESS;
				}
			}
			return Action.ERROR;
		}
	}

    private UserDetails LDAPAuth(String username, int whichLDAP)
    			throws Exception
    {
    	String ldapURL = "";
    	String ldapDnUser= "";
    	String ldapBase = "";
    	String ldapFilter = ""; 
    	int	ldapPort;
    	UserDetails userDet = null;
		String[] attrList = {
				"mail", 
				"sn", 
				"givenname", 
				"cn",
				"employeeNumber"
		};

		attrList[4] = properties.getEmplpoyeeIdAttribute();
		
    	if (whichLDAP == SEARCH_PRI_LDAP)
    	{
    		ldapURL = properties.getLdapURLPri();
    		ldapDnUser = properties.getLdapDnUserPri().replaceAll("%USERNAME%", username);
    		ldapBase = properties.getLdapBasePri();
    		ldapPort = properties.getLdapPortPri();
    		ldapFilter = properties.getLdapFiltPri().replaceAll("%USERNAME%", username);
    	}
    	else
    	{
    		ldapURL = properties.getLdapURLAlt();
    		ldapDnUser = properties.getLdapDnUserAlt().replaceAll("%USERNAME%", username);
    		ldapBase = properties.getLdapBaseAlt();
    		ldapPort = properties.getLdapPortAlt();
    		ldapFilter = properties.getLdapFiltAlt().replaceAll("%USERNAME%", username);
    	}

    	org.forgerock.opendj.ldap.Connection connection = null;
    	try
    	{
    		// Connect and bind to the server.
    		final LDAPConnectionFactory factory = new LDAPConnectionFactory(ldapURL, ldapPort);
    		connection = factory.getConnection();
    		// DN bindDN = entry.getName();
    		// bindDN.toString()
    		// SearchResultEntry entry =
			//	connection.searchSingleEntry(ldapBase, SearchScope.WHOLE_SUBTREE, 
			//			ldapFilter.replace("%USERNAME%", username), "cn");
    		BindResult result = connection.bind(ldapDnUser, password.toCharArray());
            if (result.isSuccess()) {
	    		final ConnectionEntryReader reader = 
	    				connection.search(ldapBase, SearchScope.WHOLE_SUBTREE, ldapFilter, attrList);
	    				// bindDN.toString()
	    		if (reader.hasNext())
	    		{
	    			SearchResultEntry entry = reader.readEntry();
					
	    			userDet = new UserDetails(
	    					entry.getAttribute(properties.getEmplpoyeeIdAttribute()).firstValueAsString(),
	    					(whichLDAP == SEARCH_PRI_LDAP ? username : ""),
	    					(whichLDAP == SEARCH_ALT_LDAP ? username : ""),
	    					entry.getAttribute("givenname").firstValueAsString(),
	    					entry.getAttribute("sn").firstValueAsString(),
	    					entry.getAttribute("mail").firstValueAsString(),
	    					false, 
	    					username);
	    			userDet.setUsername(username);
	    		}
	    		else
	    		{
	    			log.error("LDAPAuth: User " + username + " not found");
	    			addActionError("Authentication error");
	    			return null;
	    		}
            }
    		else
    		{
    			log.error("LDAPAuth: User " + username + " not found");
    			addActionError("Authentication error");
    			return null;
    		}
    	}
    	catch (final Exception e)
    	{
    		// Handle exceptions...
    		System.err.println(e.getMessage());
    	}
    	finally
    	{
    		if (connection != null)
    		{
    			connection.close();
    		}
    	}
    	session.put("UserAuth", userDet);
    	session.put("username", username);
    	return userDet;
    }

    private boolean Authenticate()
	{
		boolean bRet = false;
		if (username == null)
			return(false);
		
		try
		{
			
			userDet = LDAPAuth(username, (idType.compareTo("PRI") == 0 ? SEARCH_PRI_LDAP : SEARCH_ALT_LDAP));

			if (userDet == null)
			{
				return(false);
			}
			Context iCxt = new InitialContext();
	    	Context ctx = (Context) iCxt.lookup( "java:/comp/env" );
	        DataSource ds = (DataSource)ctx.lookup("jdbc/tracker");

	        Connection dbConn = ds.getConnection();
			Statement st = dbConn.createStatement();
	    	String sSql = "SELECT PRILogin, ALTLogin, isAdmin " +
	    				  "FROM Users " +
	    				  "WHERE employeeId = '" + userDet.getEmployeeID() + "'";
	    	
			log.debug("Authenticate: Executing query '" + sSql + "'");
			ResultSet rs = st.executeQuery(sSql);
			sSql = "";
			if(rs.next())
			{
				log.debug("Authenticate: User's credential already acquired. Updating PRI/SEC id if required");
				if ((idType.compareTo("PRI") == 0) && (rs.getString("PRILogin") != ""))
				{
					sSql = "UPDATE Users " +
							"SET PRILogin = '" + userDet.getPRILogin() + "' " +
							"WHERE employeeId = '" + userDet.getEmployeeID() + "'";
				}
				else if ((idType.compareTo("ALT") == 0) && (userDet.getALTLogin() != ""))
				{
					sSql = "UPDATE Users " +
							"SET ALTLogin = '" + userDet.getALTLogin() + "' " +
							"WHERE employeeId = '" + userDet.getEmployeeID() + "'";
				}
				if (rs.getBoolean("isAdmin"))
					userDet.setAdmin(true);
			}
			else
			{
				log.debug("Authenticate: First time comer. Store details");
				sSql = "INSERT " +
					   " INTO Users (employeeId, PRILogin, ALTLogin, givenName, lastName, mail, isAdmin) " +
					   " VALUES (" +
					   "'" + userDet.getEmployeeID() + "', " +
					   "'" + userDet.getPRILogin() + "', " +
					   "'" + userDet.getALTLogin() + "', " +
					   "'" + userDet.getGivenName() + "', " +
					   "'" + userDet.getLastName() + "', " +
					   "'" + userDet.getMail() + "', " +
					   "0" +")";
			}
			
			if (sSql != "")
			{
				// TODO. check result
				st.execute(sSql);	
			}
			bRet = true;
			dbConn.close();
    	}
    	catch(NamingException e)
    	{
    	    log.error("Authenticate: exception 'NamingException' " + e.getMessage());
    	    System.out.println( "NamingException: " + e.getMessage());
	    	bRet = false;
    	} 
    	catch (SQLException e)
    	{
    	    log.error("Authenticate: exception 'SQLException' " + e.getMessage());
    	    System.out.println( "SQLException: " + e.getMessage());
	    	bRet = false;
		}
    	catch (Exception e)
    	{
    	    log.error("Authenticate: exception 'Exception' " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return(bRet);
 	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername()
	{
		return(username);
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword()
	{
		return(password);
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
}