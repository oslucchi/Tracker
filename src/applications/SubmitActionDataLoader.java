package applications;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tracker.IssueDetails;
import tracker.IssueImpact;
import tracker.IssueDetails.IssueDetailsItem;
import tracker.IssueImpact.IssueItem;
import tracker.IssueImpact.Selection;
import utility.dbTable.Application;
import utility.logger.AppLogger;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class SubmitActionDataLoader extends ActionSupport 
						implements SessionAware
{
    private static AppLogger log = (AppLogger) AppLogger.getLogger(SubmitActionDataLoader.class.getName());

    public HttpServletRequest request;
    
    private String appName;
    private int appId;
    private String XMLMenu;
    private ArrayList<IssueImpact> impactList;
	private ArrayList<IssueDetails> detailsList = new ArrayList<IssueDetails>();
	private int maxDetailsNo = 0;
	private boolean severityRequired = false;
	private String prioValueList = "";
	private int initialPrio = 0;
	private int initialSeve = 0;
	private int severityLow = 0;
	private int severityMed = 0;
	private int severityHig = 0;
	private int priorityLow = 0;
	private int priorityMed = 0;
	private int priorityHig = 0;
	private double severityWA = 0;
	private double priorityWA = 0;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
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

	public ArrayList<IssueImpact> getImpactList() {
		return impactList;
	}

	public void setImpactList(ArrayList<IssueImpact> impactList) {
		this.impactList = impactList;
	}
	
	public ArrayList<IssueDetails> getDetailsList() {
		return detailsList;
	}

	public void setDetailsList(ArrayList<IssueDetails> detailsList) {
		this.detailsList = detailsList;
	}

	public int getMaxDetailsNo() {
		return maxDetailsNo;
	}

	public void setMaxDetailsNo(int maxDetailsNo) {
		this.maxDetailsNo = maxDetailsNo;
	}

	public boolean getSeverityRequired() {
		return severityRequired;
	}

	public void setSeverityRequired(boolean severityRequired) {
		this.severityRequired = severityRequired;
	}

	public String getPrioValueList() {
		return prioValueList;
	}

	public void setPrioValueList(String prioValueList) {
		this.prioValueList = prioValueList;
	}

	public int getSeverityLow() {
		return severityLow;
	}

	public void setSeverityLow(int severityLow) {
		this.severityLow = severityLow;
	}

	public int getSeverityMed() {
		return severityMed;
	}

	public void setSeverityMed(int severityMed) {
		this.severityMed = severityMed;
	}

	public int getSeverityHig() {
		return severityHig;
	}

	public void setSeverityHig(int severityHig) {
		this.severityHig = severityHig;
	}

	public int getPriorityLow() {
		return priorityLow;
	}

	public void setPriorityLow(int priorityLow) {
		this.priorityLow = priorityLow;
	}

	public int getPriorityMed() {
		return priorityMed;
	}

	public void setPriorityMed(int priorityMed) {
		this.priorityMed = priorityMed;
	}

	public int getPriorityHig() {
		return priorityHig;
	}

	public void setPriorityHig(int priorityHig) {
		this.priorityHig = priorityHig;
	}

	private static final long serialVersionUID = 1L;

	private Map<String, Object> session;
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
	
	@SuppressWarnings("unchecked")
	public String execute()
	{
		try 
		{
			Application app = new Application(appName);
			appId = app.getId();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
    	    log.error("Unable to istantiate new object Application for " + appName + " (" + e.getMessage() + ")");
    	    return(Action.ERROR);
		}
		
		String prioSeveSep = "";
		if ((session.get("impactList") == null) ||
			(((String) session.get("currentApp")).compareTo(appName) != 0))
		{
			Document document;
			impactList = new ArrayList<IssueImpact>();
			try
			{
				ServletContext context = ServletActionContext.getServletContext();
				String sc = context.getRealPath("/AppsDesc/" + appName + ".xml");
				DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				document = parser.parse(new File(sc));
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

			IssueImpact impact;
			IssueItem item;
			ArrayList<Selection> selectionList;

			NodeList impactNodes = document.getElementsByTagName("Impact");
			if (impactNodes.getLength() != 0)
			{
				ArrayList<Node> groupNodes = NodesManager.GetNodeList(impactNodes.item(0), "Group");
				for(int i = 0; i < groupNodes.size(); i++)
				{
					impact = new IssueImpact();
					impact.items = new ArrayList<IssueImpact.IssueItem>();

					NamedNodeMap attr = groupNodes.get(i).getAttributes();
					impact.impactName = attr.getNamedItem("name").getNodeValue();
					String groupId = attr.getNamedItem("groupId").getNodeValue();
					
					ArrayList<Node> groupItemList = NodesManager.GetNodeList(groupNodes.get(i), "GroupItem");
					for(int y = 0; y < groupItemList.size(); y++)
					{
						item = impact.new IssueItem();
						attr = groupItemList.get(y).getAttributes();
						item.setName(attr.getNamedItem("name").getNodeValue());
						if (attr.getNamedItem("subDesc") != null)
						{
							item.setSubDesc(attr.getNamedItem("subDesc").getNodeValue());
						}
						ArrayList<Node> checkboxList = NodesManager.GetNodeList(groupItemList.get(y), "GrpItmCheckBox");
						selectionList = new ArrayList<Selection>();
						for(int w = 0; w < checkboxList.size(); w++)
						{
							attr = checkboxList.get(w).getAttributes();
							Selection selection = impact.new Selection();
							selection.setName(attr.getNamedItem("desc").getNodeValue());
							selection.setGroupName(groupId);
							selection.setValue(attr.getNamedItem("value").getNodeValue());
							selection.setSeverity(Integer.parseInt(attr.getNamedItem("severity").getNodeValue()));
							selection.setPriority(Integer.parseInt(attr.getNamedItem("priority").getNodeValue()));
							if (attr.getNamedItem("default") != null)
							{
								selection.setDefaultOpt(Boolean.parseBoolean(attr.getNamedItem("default").getNodeValue()));
								initialPrio += selection.getPriority();
								initialSeve += selection.getSeverity();
							}
							prioValueList += prioSeveSep + attr.getNamedItem("value").getNodeValue() + ";" +
											 attr.getNamedItem("severity").getNodeValue() + ";" + 
											 attr.getNamedItem("priority").getNodeValue();
							prioSeveSep = "|";
							selection.setDefaultOpt(true);
							selectionList.add(selection);
						}
						item.setSelectionList(selectionList);
						impact.items.add(item);
					}
					impactList.add(impact);
				}
			} 
			
			IssueDetails issueDet;
			IssueDetailsItem issueDetItem;

			NodeList issueTypeNodes = document.getElementsByTagName("IssueType");
			if (issueTypeNodes.getLength() != 0)
			{
				ArrayList<Node> groupNodes = NodesManager.GetNodeList(issueTypeNodes.item(0), "Severity");
				NamedNodeMap attr;
				if (groupNodes.size() != 0)
				{
					attr = groupNodes.get(0).getAttributes();
					severityRequired = true;
					priorityLow = Integer.parseInt(attr.getNamedItem("prioLow").getNodeValue());
					priorityMed = Integer.parseInt(attr.getNamedItem("prioMed").getNodeValue());
					priorityHig = Integer.parseInt(attr.getNamedItem("prioHig").getNodeValue());
					severityLow = Integer.parseInt(attr.getNamedItem("sevLow").getNodeValue());
					severityMed = Integer.parseInt(attr.getNamedItem("sevMed").getNodeValue());
					severityHig = Integer.parseInt(attr.getNamedItem("sevHig").getNodeValue());
					if (attr.getNamedItem("severityWA") != null)
					{
						severityWA = Double.parseDouble(attr.getNamedItem("severityWA").getNodeValue());
					}
					else
					{
						severityWA = 1.0;
					}
					if (attr.getNamedItem("priorityWA") != null)
					{
						priorityWA = Double.parseDouble(attr.getNamedItem("priorityWA").getNodeValue());
					}
					else
					{
						priorityWA = 1.0;
					}
					if ((attr.getNamedItem("severityWA") != null) || (attr.getNamedItem("priorityWA") != null))
					{
						impact = new IssueImpact();
						impact.items = new ArrayList<IssueImpact.IssueItem>();
						impact.impactName = "Workaround";
						item = impact.new IssueItem();
						item.setName("Workaround available");
						selectionList = new ArrayList<Selection>();
						Selection selection = impact.new Selection();
						selection.setName("Yes");
						selection.setGroupName("wka");
						selection.setValue("wka-yes");
						selection.setSeverityWA(severityWA);
						selection.setPriorityWA(priorityWA);
						selection.setDefaultOpt(false);
						selectionList.add(selection);
						selection = impact.new Selection();
						selection.setName("No");
						selection.setGroupName("wka");
						selection.setValue("wka-no");
						selection.setSeverityWA(1.0);
						selection.setPriorityWA(1.0);
						selection.setDefaultOpt(false);
						selectionList.add(selection);
						prioValueList += prioSeveSep + "wka-yes" + ";" + severityWA + ";" + priorityWA;
						prioValueList += prioSeveSep + "wka-no" + ";1.0;1.0";

						item.setSelectionList(selectionList);
						impact.items.add(item);
						impactList.add(impact);
					}
				}
				
				groupNodes = NodesManager.GetNodeList(issueTypeNodes.item(0), "IssueDetails");
				for(int i = 0; i < groupNodes.size(); i++)
				{
					issueDet = new IssueDetails();
					issueDet.items = new ArrayList<IssueDetails.IssueDetailsItem>();

					attr = groupNodes.get(i).getAttributes();
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

						ArrayList<Node> mailToList = NodesManager.GetNodeList(groupItemList.get(y), "MailTo");
						for(int w = 0; w < mailToList.size(); w++)
						{
							attr = mailToList.get(w).getAttributes();
							IssueDetails.Mail newMail = 
								issueDet.new Mail(attr.getNamedItem("address").getNodeValue(),
													  attr.getNamedItem("display").getNodeValue());
							issueDetItem.AddMail(newMail);
						}
						issueDet.items.add(issueDetItem);
					}
					groupItemList = NodesManager.GetNodeList(groupNodes.get(i), "IssueAdditionalInfo");
					if (groupItemList.size() != 0)
					{
						boolean clearOnHide = false;
						attr = groupItemList.get(0).getAttributes();
						if (attr.getNamedItem("clearOnHide") != null)
						{
							clearOnHide = Boolean.parseBoolean(attr.getNamedItem("clearOnHide").getNodeValue());
						}
						issueDet.setInfoText(
								issueDet.new AdditionalInfo(attr.getNamedItem("name").getNodeValue(),
															attr.getNamedItem("showIfOneOf").getNodeValue(),
															Integer.parseInt(attr.getNamedItem("length").getNodeValue()),
															clearOnHide));
					}
					detailsList.add(issueDet);
					if (issueDet.items.size() > maxDetailsNo)
					{
						maxDetailsNo = issueDet.items.size();
					}
				}
			} 

			session.put("impactList", impactList);
			session.put("prioValueList", prioValueList);
			session.put("initialPrio", initialPrio);
			session.put("initialSeve", initialSeve);
			session.put("detailsList", detailsList);
			session.put("maxDetailsNo", new Integer(maxDetailsNo));
			session.put("severityRequired", new Boolean(severityRequired));
			session.put("priorityLow", priorityLow);
			session.put("priorityMed", priorityMed);
			session.put("priorityHig", priorityHig);
			session.put("severityLow", severityLow);
			session.put("severityMed", severityMed);
			session.put("severityHig", severityHig);
			session.put("severityWA", severityWA);
			session.put("priorityWA", priorityWA);
			session.put("currentApp", appName);
		}
		else
		{
			impactList = (ArrayList<IssueImpact>) session.get("impactList");
			detailsList = (ArrayList<IssueDetails>) session.get("detailsList");
			prioValueList = (String) session.get("prioValueList");
			maxDetailsNo = ((Integer) session.get("maxDetailsNo")).intValue();
			severityRequired = ((Boolean) session.get("severityRequired")).booleanValue();
			initialPrio = ((Integer) session.get("initialPrio")).intValue();
			initialSeve = ((Integer) session.get("initialSeve")).intValue();
			priorityLow = ((Integer) session.get("priorityLow")).intValue();
			priorityMed = ((Integer) session.get("priorityMed")).intValue();
			priorityHig = ((Integer) session.get("priorityHig")).intValue();
			severityLow = ((Integer) session.get("severityLow")).intValue();
			severityMed = ((Integer) session.get("severityMed")).intValue();
			severityHig = ((Integer) session.get("severityHig")).intValue();
			severityWA = ((Double) session.get("severityWA")).doubleValue();
			priorityWA = ((Double) session.get("priorityWA")).doubleValue();
			appName = (String) session.get("currentApp");
		}
		XMLMenu = (String) session.get("XMLMenu");
		return(Action.SUCCESS);
	}
}
