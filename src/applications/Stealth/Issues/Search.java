package applications.Stealth.Issues;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.naming.NamingException;

import org.apache.struts2.interceptor.SessionAware;

import tracker.IssueDetails;
import tracker.SerializeToString;
import utility.dbTable.Application;
import utility.dbTable.Issue;

import applications.SearchActionDataLoader;

import com.opensymphony.xwork2.Action;

public class Search extends SearchActionDataLoader
					implements Serializable, SessionAware
{
	private static final long serialVersionUID = 1L;

	protected String appId;
	protected String calFrom;
	protected String calTo;
	protected String keyword;
	protected String status;
	protected String issueId;
	protected String req;
	protected String flow;
	protected String loc;
	protected ArrayList<Issue> issueList;
	protected int issuesRetrieved = 0;
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status) 
	{
		this.status = status;
	}
	
	public String getIssueId() 
	{
		return issueId;
	}
	
	public void setIssueId(String issueId) 
	{
		this.issueId = issueId;
	}
	
	public String getReq() 
	{
		return req;
	}
	
	public void setReq(String req) 
	{
		this.req = req;
	}
	
	public String getFlow() 
	{
		return flow;
	}
	
	public void setFlow(String flow) 
	{
		this.flow = flow;
	}
	
	public String getLoc() 
	{
		return loc;
	}
	
	public void setLoc(String loc) 
	{
		this.loc = loc;
	}

	public String getCalFrom() 
	{
		return calFrom;
	}

	public void setCalFrom(String calFrom) 
	{
		this.calFrom = calFrom;
	}

	public String getCalTo() 
	{
		return calTo;
	}

	public void setCalTo(String calTo) 
	{
		this.calTo = calTo;
	}

	public String getKeyword() 
	{
		return keyword;
	}

	public void setKeyword(String keyword) 
	{
		this.keyword = keyword;
	}

//	public String getAppId() 
//	{
//		return appId;
//	}

	public void setAppId(String appId) 
	{
		this.appId = appId;
	}
	
	public ArrayList<Issue> getIssueList() 
	{
		return issueList;
	}

	public void setIssueList(ArrayList<Issue> issueList)
	{
		this.issueList = issueList;
	}

	public int getIssuesRetrieved()
	{
		return issuesRetrieved;
	}

	public void setIssuesRetrieved(int issuesRetrieved)
	{
		this.issuesRetrieved = issuesRetrieved;
	}

	private boolean ValidateClassAttribute(String attrName, Object lookIn, String lookFor)
	{
		if (lookFor == null)
			return(true);
		Class<?> cls = lookIn.getClass();
		String attrValue = "";
		try 
		{
			String methodName = "get".concat(attrName.substring(0,1).toUpperCase())
									 .concat(attrName.substring(1));
			Method m = cls.getMethod(methodName, (Class[]) null);
			
			attrValue = (String) m.invoke(lookIn, (Object[]) null);
		}
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		}
		catch (SecurityException e) 
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) 
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e) 
		{
			e.printStackTrace();
		}

		StringTokenizer st = new StringTokenizer(lookFor);
		String stValue = "";
		while((stValue = st.nextToken(",")) != null)
		{
			if (attrValue.contains(stValue))
			{
				return(true);
			}
		}
		return(false);
	}

	@SuppressWarnings("unchecked")
	public String execute()
	{
		detailsList = (ArrayList<IssueDetails>) session.get("detailsList");
		maxDetailsNo = ((Integer) session.get("maxDetailsNo")).intValue();
		XMLMenu = (String) session.get("XMLMenu");
		appName = (String) session.get("currentApp");

		Application app = null;
		try
		{
			app = new Application(appName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		String sql = "SELECT * FROM Issue " +
					 "WHERE appId = " + app.getId() + " ";
		if (calFrom.compareTo("") != 0)
			sql += " AND submitted >= STR_TO_DATE('" + calFrom + "', '%m/%d/%Y') ";
		if (calTo.compareTo("") != 0)
			sql += " AND submitted <= STR_TO_DATE('" + calTo + "', '%m/%d/%Y') ";
		if (status.compareTo("opened") == 0)
			sql += " AND closed IS NULL ";
		if (status.compareTo("closed") == 0)
			sql += " AND closed IS NOT NULL ";
		sql += "ORDER BY id";
		Issue issue = null;
		try
		{
			issuesRetrieved = 0;
			issue = new Issue();
			issueList = issue.populateCollection(sql, issue);
		}
		catch (NamingException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		for(int i = issueList.size() - 1; i >= 0 ; i--)
		{
			try 
			{
				Object issueItem = SerializeToString.fromString(issueList.get(i).getObjMarshall());
				if (!ValidateClassAttribute("loc", issueItem, loc))
				{
					issueList.remove(i);
				}
				if (!ValidateClassAttribute("req", issueItem, req))
				{
					issueList.remove(i);
				}
				if (!ValidateClassAttribute("flow", issueItem, flow))
				{
					issueList.remove(i);
				}
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
		}

		issuesRetrieved = issueList.size();
		return(Action.SUCCESS);
	}
}
