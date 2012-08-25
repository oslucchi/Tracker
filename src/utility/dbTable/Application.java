package utility.dbTable;

import utility.logger.AppLogger;

public class Application extends DBInterface {
	static final long serialVersionUID = 1;
	protected int id = 0;
	protected String name = "";
	protected String description = "";
	protected String LOB = "";
	protected String XMLDescriptor = "";
	protected String classMarshall = "";
    private static AppLogger log = (AppLogger) AppLogger.getLogger(Application.class.getName());

    public Application() throws Exception
	{
    	tableName = "Application";
	}

    public Application(String appName) throws Exception
	{
    	tableName = "Application";
		String sql = "SELECT * " +
					 "FROM " + tableName + " " +
					 "WHERE name = '" + appName + "'";
		this.populateObject(sql, this);
	}

    public Application(int id) throws Exception
	{
    	tableName = "Application";
		String sql = "SELECT * " +
					 "FROM " + tableName + " " +
					 "WHERE id = " + id;
		this.populateObject(sql, this);
	}

    public boolean updateItem(boolean inTransaction)
	{
		boolean retVal = false;
		String sqlQuery = "SELECT * " +
						  "FROM " + tableName + " " +
						  "WHERE id = " + this.id;
		log.debug("update item using transaction? " + inTransaction);
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
		log.debug("insert item using transaction? " + inTransaction);
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
		log.debug("delete item using transaction? " + inTransaction);
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

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getLOB() 
	{
		return LOB;
	}

	public void setLOB(String lOB) 
	{
		LOB = lOB;
	}

	public String getXMLDescription() 
	{
		return XMLDescriptor;
	}

	public void setXMLDescription(String xMLDescription) 
	{
		XMLDescriptor = xMLDescription;
	}

	public String getClassMarshall() 
	{
		return classMarshall;
	}

	public void setClassMarshall(String classMarshall) 
	{
		this.classMarshall = classMarshall;
	}

}
