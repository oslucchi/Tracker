package utility.dbTable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import utility.logger.AppLogger;
import utility.Constants;

public class DBInterface 
{
	static final long serialVersionUID = 1;

	protected String tableName = "";
	protected boolean readPage = false;
	protected boolean showNext = false;
	protected boolean showPrevious = false;
	protected int startRecord = 0;

    private static AppLogger log = (AppLogger) AppLogger.getLogger(DBInterface.class.getName());
    private DataSource ds = null;
    private ResultSet rs = null;
    private ResultSetMetaData rsm = null;
    private Statement st = null;
	private Connection conn = null;

    public DBInterface() throws NamingException, SQLException
	{
		GetDataSource();
    	return;
	}
    
    protected void finalize() throws Throwable
    {
    	st.close();
		conn.close();
		super.finalize(); 
    }
    
	private void GetDataSource() throws NamingException, SQLException
	{
    	Context iCxt = new InitialContext();
		Context ctx = (Context) iCxt.lookup( "java:/comp/env" );
	    ds = (DataSource) ctx.lookup("jdbc/tracker");
		conn = ds.getConnection();
		st = conn.createStatement();
	}
	
	private void ExecuteQuery(String sql) throws SQLException
	{
		log.debug("querying DB: '" + sql + "'");
		rs = st.executeQuery(sql);
		rsm = rs.getMetaData();
	}

	public void populateObject(String sql, Object tbObj) throws Exception
	{
    	Field[] clFields = null;
    	ExecuteQuery(sql);
    	if (!rs.next())
    		throw new SQLException("No data found");
    	
    	clFields = tbObj.getClass().getDeclaredFields();
    	try
    	{
			for(int i = 1; i <= rsm.getColumnCount(); i++)
			{
				for(int y = 0; y < clFields.length; y++)
				{
					if (clFields[y].getName().compareTo(rsm.getColumnName(i)) == 0)
					{
						switch(rsm.getColumnType(i))
						{
						case Types.INTEGER:
						case Types.BIGINT:
						case Types.SMALLINT:
						case Types.TINYINT:
						case Types.NUMERIC:
						case Types.BIT:
							//log.debug("setting '" + clFields[y].getName() +"' to " + rs.getInt(clFields[y].getName()));
							clFields[y].setInt(tbObj, rs.getInt(clFields[y].getName()));
							break;
							
						case Types.DATE:
							//log.debug("setting '" + clFields[y].getName() +"' to " + rs.getString(clFields[y].getName()));
							clFields[y].set(tbObj, rs.getDate(clFields[y].getName()));
							break;

						case Types.TIMESTAMP:
							//log.debug("setting '" + clFields[y].getName() +"' to " + rs.getString(clFields[y].getName()));
							clFields[y].set(tbObj, rs.getTimestamp(clFields[y].getName()));
							break;

						case Types.CHAR:
						case Types.VARCHAR:
							clFields[y].set(tbObj, rs.getString(clFields[y].getName()));
							break;
						case Types.LONGVARCHAR:
							//log.debug("setting '" + clFields[y].getName() +"' to " + rs.getString(clFields[y].getName()));
							clFields[y].set(tbObj, rs.getString(clFields[y].getName()));
							break;
						
						case Types.FLOAT:
						case Types.REAL:
						case Types.DOUBLE:
						case Types.DECIMAL:
							//log.debug("setting '" + clFields[y].getName() +"' to " + rs.getDouble(clFields[y].getName()));
							clFields[y].set(tbObj, rs.getDouble(clFields[y].getName()));
							break;
							
						default:
							//log.debug("type " + rsm.getColumnType(i) + "(" + rsm.getColumnTypeName(i)+ ") not found");
							//log.debug("setting '" + clFields[y].getName() +"' to " + rs.getString(clFields[y].getName()));
							clFields[y].set(tbObj, rs.getString(clFields[y].getName()));
						}
					}						
				}
			}
    	}
    	catch(Exception e)
    	{
    		throw e;
    	}   	
    	return;
	}

	
    @SuppressWarnings("unchecked")
	public ArrayList populateCollection(String sql, Object obj) throws Exception
	{
    	Field[] clFields = null;
    	ArrayList aList = new ArrayList();
    	Class objClass = obj.getClass();
    	
    	ExecuteQuery(sql);
    	if (rs == null)
    	{
    		log.debug("WHY???");
    	}
    	
		clFields = objClass.newInstance().getClass().getDeclaredFields();
		while(rs.next())
		{
			Object objInst = objClass.newInstance();
			for(int i = 1; i <= rsm.getColumnCount(); i++)
			{
				for(int y = 0; y < clFields.length; y++)
				{
					if (clFields[y].getName().compareTo(rsm.getColumnName(i)) == 0)
					{
						switch(rsm.getColumnType(i))
						{
						case Types.INTEGER:
						case Types.BIGINT:
						case Types.SMALLINT:
						case Types.TINYINT:
						case Types.NUMERIC:
						case Types.BIT:
							// log.debug("DBInterface.PopulateCollection: type " + rsm.getColumnType(i) + "(" + rsm.getColumnTypeName(i)+ ") setting '" + clFields[y].getName() +"' to " + rs.getInt(clFields[y].getName()));
							clFields[y].setInt(objInst, rs.getInt(clFields[y].getName()));
							break;
							
						case Types.DATE:
							// log.debug("DBInterface.PopulateCollection: type " + rsm.getColumnType(i) + "(" + rsm.getColumnTypeName(i)+ ") setting '" + clFields[y].getName() +"' to " + rs.getString(clFields[y].getName()));
							try
							{
								clFields[y].set(objInst, rs.getDate(clFields[y].getName()));
							}
							catch(SQLException e)
							{
								clFields[y].set(objInst, null);
							}
							break;

						case Types.TIMESTAMP:
							// log.debug("setting '" + clFields[y].getName() +"' to " + rs.getString(clFields[y].getName()));
							try
							{
								clFields[y].set(objInst, rs.getTimestamp(clFields[y].getName()));
							}
							catch(SQLException e)
							{
								clFields[y].set(objInst, null);
							}
							break;

						case Types.CHAR:
						case Types.VARCHAR:
						case Types.LONGVARCHAR:
							// log.debug("type " + rsm.getColumnType(i) + "(" + rsm.getColumnTypeName(i)+ ") setting '" + clFields[y].getName() +"' to " + rs.getString(clFields[y].getName()));
							clFields[y].set(objInst, rs.getString(clFields[y].getName()));
							break;
						
						case Types.FLOAT:
						case Types.REAL:
						case Types.DOUBLE:
						case Types.DECIMAL:
							// log.debug("type " + rsm.getColumnType(i) + "(" + rsm.getColumnTypeName(i)+ ") setting '" + clFields[y].getName() +"' to " + rs.getDouble(clFields[y].getName()));
							clFields[y].set(objInst, rs.getDouble(clFields[y].getName()));
							break;
							
						default:
							log.debug("type " + rsm.getColumnType(i) + "(" + rsm.getColumnTypeName(i)+ ") not found");
							log.debug("setting '" + clFields[y].getName() +"' to " + rs.getString(clFields[y].getName()));
							clFields[y]. set(objInst, rs.getString(clFields[y].getName()));
						}							
					}						
				}
			}
			aList.add(objInst);
		}
		return aList;
	}
	private Field[] getAllFields(Class<?> cType)
	{
		List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = cType; c != null; c = c.getSuperclass()) 
        {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        Field[] fieldArr = new Field[fields.size()];
        return fields.toArray(fieldArr);
	}
	
	@SuppressWarnings("unchecked")
	public String getUpdateStatement(String sql, Object tbObj, String avoidColumn) throws Exception
	{
    	Field[] clFields = null;
    	String retSql = "";
    	String sSep = "";
    	boolean bAvoidColumn = false;
    	boolean bKeyMyultiColumn = false;
    	String[] avoidCols = new String[0];

    	if (avoidColumn.indexOf(";") > 0)
    	{
			log.debug("a ';' is present: avoidColumn is a List");

    		bKeyMyultiColumn = true;
			avoidCols = avoidColumn.split(";");
			for( int y = 0; y < avoidCols.length; y++)
			{
				avoidCols[y] = avoidCols[y].trim();
				log.debug("index " + y + "column '" + avoidCols[y] + "'");
			}
    	}
		log.debug("Table has a multiColKey? " + bKeyMyultiColumn);
		
		ExecuteQuery(sql);
		
    	clFields = getAllFields(tbObj.getClass()); //tbObj.getClass().getDeclaredFields();
		for(int i = 1; i <= rsm.getColumnCount(); i++)
		{
			bAvoidColumn = false;
			if (bKeyMyultiColumn)
			{
				for( int y = 0; y < avoidCols.length; y++)
				{
					if(rsm.getColumnName(i).compareTo(avoidCols[y]) == 0)
					{
						bAvoidColumn = true;
						break;
					}
				}
			}
			else if (rsm.getColumnName(i).compareTo(avoidColumn) == 0)
			{
				bAvoidColumn = true;
			}

			if (! bAvoidColumn )
			{
				for(int y = 0; y < clFields.length; y++)
				{
					if (clFields[y].getName().compareTo(rsm.getColumnName(i)) == 0)
					{
						// log.debug("form field found");
						
						switch(rsm.getColumnType(i))
						{
						case Types.INTEGER:
						case Types.BIGINT:
						case Types.SMALLINT:
						case Types.TINYINT:
						case Types.NUMERIC:
						case Types.BIT:
							// log.debug("setting '" + clFields[y].getName() +"' to " + clFields[y].getInt(tbObj));
							if (clFields[y].getType().getName().compareTo("long") == 0)
								retSql += sSep + rsm.getColumnName(i) + " = " + clFields[y].getLong(tbObj);
							else
								retSql += sSep + rsm.getColumnName(i) + " = " + clFields[y].getInt(tbObj);
							sSep = ", ";
							break;
							
						case Types.DATE:
						case Types.TIMESTAMP:
							String dataFmt = "";
							try {
								Class[] signature = new Class[1];
								Class cObj = Class.forName(tbObj.getClass().getName());
								signature[0] = Class.forName(clFields[y].getType().getName());
								Method mtGet = cObj.getMethod("get" + clFields[y].getName().substring(0, 1).toUpperCase() + 
															  clFields[y].getName().substring(1) + "_fmt",(Class[]) null);
								dataFmt = (String)mtGet.invoke(tbObj, (Object[]) null);
							}
							catch(Exception E)
							{
								dataFmt = "yyyy-MM-dd HH:mm:ss";
							}

							DateFormat df = new SimpleDateFormat(dataFmt);
							
							if (clFields[y].get(tbObj) == null)
							{
								log.debug("setting '" + clFields[y].getName() +"' to NULL");
								retSql += sSep + rsm.getColumnName(i) + " = NULL ";
							}
							else
							{
								// log.debug("setting '" + clFields[y].getName() +"' to '" + df.format(clFields[y].get(tbObj)) + "'");
								retSql += sSep + rsm.getColumnName(i) + " = '" + df.format(clFields[y].get(tbObj)) + "'";
							}
							sSep = ", ";
							break;

						case Types.CHAR:
						case Types.VARCHAR:
						case Types.LONGVARCHAR:
							// log.debug("setting '" + clFields[y].getName() +"' to '" + clFields[y].get(tbObj) + "'");
							if (clFields[y].get(tbObj) == null)
							{
								log.debug("setting '" + clFields[y].getName() +"' to NULL");
								retSql += sSep + rsm.getColumnName(i) + " = NULL ";
							}
							else
							{
								retSql += sSep + rsm.getColumnName(i) + " = '" + quoteString(String.valueOf(clFields[y].get(tbObj))) + "'";
							}
							sSep = ", ";
							break;
						
						case Types.FLOAT:
						case Types.REAL:
						case Types.DOUBLE:
						case Types.DECIMAL:
							try
							{
								// log.debug("setting '" + clFields[y].getName() +"' to " + clFields[y].get(tbObj));
								retSql += sSep + rsm.getColumnName(i) + " = " + clFields[y].getDouble(tbObj);
								sSep = ", ";
							}
							catch(Exception e)
							{
								log.debug("Exception " + e.getMessage() + " raised on a DECIMAL field");
								e.printStackTrace();
							}
							break;
							
						default:
							log.debug("type " + rsm.getColumnType(i) + "(" + rsm.getColumnTypeName(i)+ ") not found");
							log.debug("setting '" + clFields[y].getName() +"' to '" + clFields[y].get(tbObj) + "'");
							if (clFields[y].get(tbObj) == null)
							{
								log.debug("setting '" + clFields[y].getName() +"' to NULL");
								retSql += sSep + rsm.getColumnName(i) + " = NULL ";
							}
							else
							{
								retSql += sSep + rsm.getColumnName(i) + " = '" + quoteString(String.valueOf(clFields[y].get(tbObj))) + "'";
							}
							sSep = ", ";
						}
					}						
				}
			}
		}
		return retSql;
	}

	public boolean update(String sqlQuery, String avoidColumns, String whereClause)
	{
		try
		{			
	    	/*
	    	 * Populating a ResultSetMetaData object to obtain table columns to be used in the query.
	    	 */
			String sql = "UPDATE " + tableName + " SET ";
			sql += this.getUpdateStatement(sqlQuery, this, avoidColumns);
			sql += " " + whereClause;
			log.debug("Executing update statement '" + sql + "'");
			st.execute(sql);
		}
		catch(Exception e)
		{
			log.debug("Menu.updateDB: Eccezione SQL " + e.getMessage());
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean insert(String sqlQuery, String avoidColumns, String idName, Object id)
	{
		try
		{			
	    	/*
	    	 * Populating a ResultSetMetaData object to obtain table columns to be used in the query.
	    	 */
			String sql = "INSERT INTO " + tableName + " SET ";
			sql += this.getUpdateStatement(sqlQuery, this, "id");

			log.debug("Executing insert statement '" + sql + "'");
			st.execute(sql);
			ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID() AS " + idName);
			if (rs.next())
			{
				id = rs.getInt("id");
			}
			rs.close();
		}
		catch(Exception e)
		{
			log.debug("Eccezione SQL " + e.getMessage());
			return false;
		}
		return true;
	}

	public boolean delete(String sql)
	{
		try
		{			
			log.debug("Executing delete statement '" + sql + "'");
			st.execute(sql);
		}
		catch(Exception e)
		{
			log.debug("Eccezione SQL " + e.getMessage());
			// e.printStackTrace();
			return false;
		}
		return true;
	}
		
	public boolean TransactionStart()
	{
		boolean retVal = false;
		try
		{			
			retVal = st.execute("START TRANSACTION");
		}
		catch (Exception e) 
		{
			log.debug("Eccezione SQL " + e.getMessage());
		}
		return(retVal);
	}

	public boolean TransactionCommit()
	{
		boolean retVal = false;
		try
		{			
			retVal = st.execute("COMMIT");
		}
		catch (Exception e) 
		{
			log.debug("Eccezione SQL " + e.getMessage());
		}
		return(retVal);
	}

	public boolean TransactionRollback()
	{
		boolean retVal = false;
		try
		{			
			retVal = st.execute("ROLLBACK");
		}
		catch (Exception e) 
		{
			log.debug("Eccezione SQL " + e.getMessage());
		}
		return(retVal);
	}
	
	@SuppressWarnings("unchecked")
	public void SetPageingInfo(Object objInst, int startFrom, String sqlSelect, ArrayList result)
	{	
		log.debug("setting boolean for next and previous for " + objInst.getClass().getName());
		Class c = null;
		Class[] signature = new Class[1];
		
		Object[] argsTrue = {new Boolean(true)};
		Object[] argsFalse = {new Boolean(false)};

		Method mtN = null;
		Method mtP = null;
		
		try
		{
			signature[0] = Class.forName(Boolean.class.getName());
			c = Class.forName(objInst.getClass().getName());				
			mtN = c.getMethod("setShowNext", signature);
			mtP = c.getMethod("setShowPrev", signature);
			
			if (result.size() == Constants.DB_ACCESS_PAGE_SIZE)
			{
				log.debug("invoking setNext true");
				mtN.invoke(objInst, argsTrue);
			}
			else
			{
				log.debug("invoking setNext false");
				mtN.invoke(objInst, argsFalse);
			}
			
			if (startFrom > 0)
			{
				log.debug("invoking setPrev true");
				mtP.invoke(objInst, argsTrue);
			}
			else
			{
				log.debug("invoking setPrev false");
				mtP.invoke(objInst, argsFalse);
			}
	
			Method mtS = null;
			signature[0] = Class.forName(Integer.class.getName());
			mtS = c.getMethod("setStartFrom", signature);
			Object[] args = new Object[1];
			args[0] = new Integer(startFrom);
			log.debug("invoking setStartFrom " + ((Integer) args[0]).intValue());
			mtS.invoke(objInst, args);
	
			signature[0] = Class.forName(String.class.getName());
			mtS = c.getMethod("setSelectStatement", signature);
			args[0] = sqlSelect;
			log.debug("invoking setSelectStatement " + sqlSelect);
			mtS.invoke(objInst, args);
		}
		catch(Exception e)
		{
			log.debug("Eccezione " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	public boolean isReadPage() {
		return readPage;
	}

	public void setReadPage(boolean readPage) {
		this.readPage = readPage;
	}

	public boolean isShowNext() {
		return showNext;
	}

	public void setShowNext(boolean showNext) {
		this.showNext = showNext;
	}

	public boolean isShowPrevious() {
		return showPrevious;
	}

	public void setShowPrevious(boolean showPrevious) {
		this.showPrevious = showPrevious;
	}

	public int getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}

	private String quoteString(String strToQuote)
	{
		String strQuoted = strToQuote;
		int	offset = -1;
		int i = 0; 
		while(i >= 0)
		{
			if ((offset = strQuoted.indexOf("'", i)) >= 0)
			{
				strQuoted = strQuoted.substring(0, offset) + "'" + strQuoted.substring(offset);
				i = offset + 2;
			}
			else
			{
				i = -1;
			}
		}
		return strQuoted;
	}
}
