package utility.dbTable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.struts2.interceptor.SessionAware;

import tracker.login.UserDetails;
import utility.logger.AppLogger;

public class Issue extends DBInterface
				   implements SessionAware
{
	private static AppLogger log = (AppLogger) AppLogger.getLogger(Menu.class.getName());
    private DataSource ds;
	private Map<String, Object> session;
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public void setSession(Map session)
	{
		this.session = session;
	}

// Class attributes mapped to issue table    
	protected int id;
	protected int appId;
	protected String subject;
	protected String details;
	protected Date submitted;
	protected Date acknowledged;
	protected Date closed;
	protected String submitBy;
	protected String ackedBy;
	protected String closedBy;
	protected String mailSubBy;
	protected String papaId;
	protected String miId;
	protected String objMarshall;

	public Issue() throws NamingException, SQLException
	{
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Date getSubmitted() {
		return submitted;
	}
	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}
	public Date getAcknowledged() {
		return acknowledged;
	}
	public void setAcknowledged(Date acknowledged) {
		this.acknowledged = acknowledged;
	}
	public Date getClosed() {
		return closed;
	}
	public void setClosed(Date closed) {
		this.closed = closed;
	}
	public String getAckedBy() {
		return ackedBy;
	}
	public void setAckedBy(String ackedBy) {
		this.ackedBy = ackedBy;
	}
	public String getSubmitBy() {
		return submitBy;
	}
	public void setSubmitBy(String submitBy) {
		this.submitBy = submitBy;
	}
	public String getMailSubBy() {
		return mailSubBy;
	}
	public void setMailSubBy(String mailSubBy) {
		this.mailSubBy = mailSubBy;
	}
	public String getPapaId() {
		return papaId;
	}
	public void setPapaId(String papaId) {
		this.papaId = papaId;
	}
	public String getMiId() {
		return miId;
	}
	public void setMiId(String miId) {
		this.miId = miId;
	}
	public String getObjMarshall() {
		return objMarshall;
	}
	public void setObjMarshall(String objMarshall) {
		this.objMarshall = objMarshall;
	}

	public boolean insertDB()
	{
    	Statement st = null;
    	UserDetails userDet = (UserDetails) session.get("UserAuth");
    	setSubmitBy(userDet.getUsername());
    	setMailSubBy(userDet.getMail());
		try
		{			
	    	Context iCxt = new InitialContext();
			Context ctx = (Context) iCxt.lookup( "java:/comp/env" );
		    ds = (DataSource) ctx.lookup("jdbc/tracker");
	    	Connection conn = ds.getConnection();
			st = conn.createStatement();
	    	/*
	    	 * Populating a ResultSetMetaData object to obtain table columns to be used in the query.
	    	 */
			String sqlQuery = "SELECT * " +
	    				  "FROM Issue " +
	    				  "HAVING MIN(id)";
	    	
			String sql = "INSERT INTO Issue SET ";
			sql += this.getUpdateStatement(sqlQuery, this, "id");

			log.debug("Executing insert statement '" + sql + "'");
			st.execute(sql);
			ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID() AS id");
			if (rs.next())
			{
				this.id = rs.getInt("id");
			}
			rs.close();
			st.close();
			conn.close();
		}
		catch(Exception e)
		{
			log.debug("Issue.insertDB: Eccezione SQL " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
