package applications.Stealth.Issues;

import java.io.Serializable;
import java.sql.SQLException;

import javax.naming.NamingException;

import tracker.SerializeToString;
import utility.dbTable.Issue;


import com.opensymphony.xwork2.Action;

public class Submit extends Issue 
					implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String cli;
	protected String mkt;
	protected String loss;
	protected String out;
	protected String inc;
	protected String wha;
	protected String type;
	protected String newIssue;
	protected String jira;
	protected String req;
	protected String origin;
	protected String res;
	protected String flow;
	protected String loc;

	public Submit() throws NamingException, SQLException
	{
	}
	
	public String getCli() {
		return cli;
	}
	public void setCli(String cli) {
		this.cli = cli;
	}
	public String getMkt() {
		return mkt;
	}
	public void setMkt(String mkt) {
		this.mkt = mkt;
	}
	public String getLoss() {
		return loss;
	}
	public void setLoss(String loss) {
		this.loss = loss;
	}
	public String getOut() {
		return out;
	}
	public void setOut(String out) {
		this.out = out;
	}
	public String getInc() {
		return inc;
	}
	public void setInc(String inc) {
		this.inc = inc;
	}
	public String getWha() {
		return wha;
	}
	public void setWha(String wha) {
		this.wha = wha;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNewIssue() {
		return newIssue;
	}
	public void setNewIssue(String newIssue) {
		this.newIssue = newIssue;
	}
	public String getJira() {
		return jira;
	}
	public void setJira(String jira) {
		this.jira = jira;
	}
	public String getReq() {
		return req;
	}
	public void setReq(String req) {
		this.req = req;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String execute()
	{
		try
		{
			setObjMarshall(SerializeToString.toString(this));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			return(Action.ERROR);
		}
		this.insertDB();
		return(Action.SUCCESS);
	}
}
