package applications;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts2.interceptor.SessionAware;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tracker.IssueDetails;
import tracker.IssueDetails.IssueDetailsItem;
import utility.dbTable.Application;
import utility.logger.AppLogger;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class SearchActionDataLoader extends ActionSupport 
						implements SessionAware
{
	private static final long serialVersionUID = 1L;
    private static AppLogger log = (AppLogger) AppLogger.getLogger(SearchActionDataLoader.class.getName());

    public HttpServletRequest request;
    
    private String appPrefix;
    private int appId;
    private String XMLDescriptor;

    protected String appName;
    protected String XMLMenu;
	protected int maxDetailsNo = 0;
	protected ArrayList<IssueDetails> detailsList = new ArrayList<IssueDetails>();

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppPrefix() {
		return appPrefix;
	}

	public void setAppPrefix(String appPrefix) {
		this.appPrefix = appPrefix;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getXMLMenu() {
		return XMLMenu;
	}

	public void setXMLMenu(String xMLMenu) {
		XMLMenu = xMLMenu;
	}

	public ArrayList<IssueDetails> getDetailsList() {
		return detailsList;
	}

	public void setDetailsList(ArrayList<IssueDetails> detailsList) {
		this.detailsList = detailsList;
	}

	public String getXMLDescriptor() {
		return XMLDescriptor;
	}

	public void setXMLDescriptor(String xMLDescriptor) {
		XMLDescriptor = xMLDescriptor;
	}

	public int getMaxDetailsNo() {
		return maxDetailsNo;
	}

	public void setMaxDetailsNo(int maxDetailsNo) {
		this.maxDetailsNo = maxDetailsNo;
	}

	protected Map<String, Object> session;
	@SuppressWarnings("unchecked")	
	public void setSession(Map session)
	{
		// session = this.getSession();
		this.session = session;
	}
	
	public void setServletRequest(HttpServletRequest request) 
	{
	    this.request = request;
	}
	
	public String execute()
	{
		Application app = null; 
		try 
		{
			app = new Application(appName);
			appId = app.getId();
		} 
		catch (Exception e) 
		{
    	    log.error("Unable to istantiate new object Application for " + appName + " (" + e.getMessage() + ")");
    	    return(Action.ERROR);
		}
		appPrefix = app.getName().toLowerCase();
		
		Document document;
		try
		{
			DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = parser.parse(new ByteArrayInputStream(app.getXMLDescription().getBytes()));
		}
		catch(ParserConfigurationException e)
		{
    	    log.error("Stealth Submit data loader. XML Parse exception:\n" + e.getMessage());
    	    return(Action.ERROR);
		}
		catch(SAXException e)
		{
    	    log.error("Stealth Submit data loader. SAX exception:\n" + e.getMessage());
    	    return(Action.ERROR);
		}
		catch(IOException e)
		{
    	    log.error("Stealth Submit data loader. IO exception:\n" + e.getMessage());
    	    return(Action.ERROR);
		}

		
		IssueDetails issueDet;
		IssueDetailsItem issueDetItem;
		NodeList issueTypeNodes = document.getElementsByTagName("IssueType");
		if (issueTypeNodes.getLength() != 0)
		{
			ArrayList<Node> groupNodes = null;
			NamedNodeMap attr;
			groupNodes = NodesManager.GetNodeList(issueTypeNodes.item(0), "IssueDetails");
			for(int i = 0; i < groupNodes.size(); i++)
			{
				issueDet = new IssueDetails();
				issueDet.items = new ArrayList<IssueDetails.IssueDetailsItem>();

				attr = groupNodes.get(i).getAttributes();
				
				if ((attr.getNamedItem("searchable") == null) ||
					(attr.getNamedItem("searchable").getNodeValue().compareTo("yes") != 0))
					continue;
				
				issueDet.setName(attr.getNamedItem("name").getNodeValue());
				issueDet.setControlType(attr.getNamedItem("ctrltype").getNodeValue());
				issueDet.setGroupName(attr.getNamedItem("groupId").getNodeValue());
				
				ArrayList<Node> groupItemList = NodesManager.GetNodeList(groupNodes.get(i), "IssueDesc");
				for(int y = 0; y < groupItemList.size(); y++)
				{
					attr = groupItemList.get(y).getAttributes();
					issueDetItem = issueDet.new IssueDetailsItem();
					issueDetItem.setName(attr.getNamedItem("name").getNodeValue());
					issueDetItem.setId(attr.getNamedItem("id").getNodeValue());
					issueDet.items.add(issueDetItem);
				}
				detailsList.add(issueDet);
				if (issueDet.items.size() > maxDetailsNo)
				{
					maxDetailsNo = issueDet.items.size();
				}
			}
		} 
		session.put("detailsList", detailsList);
		session.put("maxDetailsNo", new Integer(maxDetailsNo));
		session.put("currentApp", appName);
//		}
//		else
//		{
//			detailsList = (ArrayList<IssueDetails>) session.get("detailsList");
//			maxDetailsNo = ((Integer) session.get("maxDetailsNo")).intValue();
//			appName = (String) session.get("currentApp");
//		}
		XMLMenu = (String) session.get("XMLMenu");
		return(Action.SUCCESS);
	}
}
