package utility.dbTable;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import tracker.login.UserDetails;
import utility.logger.AppLogger;

public class Menu extends DBInterface {
	static final long serialVersionUID = 1;
	protected int id = 0;
	protected String name = "";
	protected String tooltip = "";
	protected int level = 0;
	protected int parentId = 0;
	protected String action = "";
	protected String iconName = "";
	protected boolean bVisible;
    private static AppLogger log = (AppLogger) AppLogger.getLogger(Menu.class.getName());
	private ArrayList<Menu> resSorted;

    public Menu() throws Exception
	{
    	tableName = "Menu";
	}
    
    private void FillMenuLevels(ArrayList<Menu> result, int iCurrRoot)
    {
    	int	y;
    	Menu menuCurr, menuKid;
		menuCurr = result.get(iCurrRoot);
		resSorted.add(menuCurr);
		result.remove(iCurrRoot);
		y = iCurrRoot; 
		while(y < result.size())
		{
			menuKid = result.get(y);
			if (menuKid.parentId != menuCurr.id)
			{
				y++;
				continue;
			}
			FillMenuLevels(result, y);
		}
    }
    
	@SuppressWarnings("unchecked")
	public ArrayList<Menu> populateCollectionFromDB(UserDetails userDet) throws Exception
	{
    	ArrayList<Menu> result = new ArrayList<Menu>();
    	resSorted = new ArrayList<Menu>();
		try {
	    	String sql = "SELECT * " +
	    				  "FROM " + tableName + " " +
	    				  (!userDet.isAdmin() ? "WHERE isAdmin = '0' " : " ") + 
	    				  "ORDER BY level, id";
		    	
			log.debug("querying DB for menu '" + sql + "'");
			result = (ArrayList<Menu>) this.populateCollection(sql, this);
			log.debug("going to fill menu arraylist. Fetched " +
					  result.size() + " items");
			while(result.size() > 1)
			{
				FillMenuLevels(result, 1);
			}
			result = resSorted;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		log.debug("Users.Filled " + result.size() + " menu items");
		return(result);
	}
	
	public static String generateXML(ArrayList<Menu> menuOptList)
	{
		ResourceBundle prop = ResourceBundle.getBundle("global-messages", Locale.US);
    	String actionSuf = "action";
		actionSuf = prop.getString("menu.struts.action");
		String sXML = "<?xml version='1.0'?> <menu>";
		int i = 0, iPrevLvl = 0;
		
		while(i < menuOptList.size())
		{
			for(int y = iPrevLvl - menuOptList.get(i).getLevel(); y >= 0; y--)
			{
				sXML += "</item>";
			}
			sXML += "<item id='mnu-" + menuOptList.get(i).getId() + "'" + 
					" text='" + menuOptList.get(i).getName() + "'";
			if (menuOptList.get(i).getIconName().compareTo("") != 0)
			{
				sXML += " img='" + menuOptList.get(i).getIconName() + "'";
			}
			sXML += ">";
			
			if (menuOptList.get(i).getAction().compareTo("") != 0)
			{
				sXML += "<href><![CDATA[" + menuOptList.get(i).getAction() + "." + actionSuf + "]]></href>";
			}
			if (menuOptList.get(i).getTooltip().compareTo("") != 0)
			{
				sXML += "<tooltip><![CDATA[" + menuOptList.get(i).getTooltip() + "]]></tooltip>";
			}
			iPrevLvl = menuOptList.get(i).getLevel();
			i++;
		}
		sXML += "</item></menu>";
		return(sXML);
	}
	
	public boolean updateItem(boolean inTransaction)
	{
		boolean retVal = false;
		String sqlQuery = "SELECT * " +
						  "FROM " + tableName + " " +
						  "WHERE id = " + this.id;
		if (inTransaction)
		{
			if (!TransactionStart())
				retVal = update(sqlQuery, "id", " WHERE id = " + this.id);
			else
				return(false);
			if (!TransactionCommit())
				return(false);
		}
		return(retVal);
	}

	public boolean insertItem(boolean inTransaction)
	{
		boolean retVal = false;
		String sqlQuery = "SELECT * " +
						  "FROM " + tableName + " " +
						  "HAVING MIN(id)";
		if (inTransaction)
		{
			if (!TransactionStart())
				retVal = insert(sqlQuery, "id", " WHERE id = " + this.id, this.id);
			else
				return(false);
			if (!TransactionCommit())
				return(false);
		}
		return(retVal);
	}

	public boolean deleteItem(boolean inTransaction)
	{
		boolean retVal = false;
		String sql = "DELETE FROM " + tableName + " " +
    				  "WHERE id = " + this.id;    	
		if (inTransaction)
		{
			if (!TransactionStart())
				retVal = delete(sql);
			else
				return(false);
			if (!TransactionCommit())
				return(false);
		}
		return(retVal);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

}
