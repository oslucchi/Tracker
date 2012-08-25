package tracker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties 
{
	private String ldapURLAlt = "";
	private String ldapDnUserAlt = "";
	private String ldapBaseAlt = "";
	private String ldapFiltAlt = "";
	private int ldapPortAlt = 0;

	private String ldapURLPri = "";
	private String ldapDnUserPri = "";
	private String ldapBasePri = "";
	private String ldapFiltPri = "";
	private int ldapPortPri = 0;
	
	private boolean showPrimAltLoginRadio = false;
	private String emplpoyeeIdAttribute = "";

	public ApplicationProperties ()
	{
    	Properties properties = new Properties();
    	try 
    	{
        	InputStream in = getClass().getResourceAsStream("/package.properties");
			properties.load(in);
	    	in.close();    	
		}
    	catch (IOException e) 
    	{
			e.printStackTrace();
    		return;
		}
    	ldapURLPri = properties.getProperty("ldapURLPri");
    	ldapURLAlt = properties.getProperty("ldapURLAlt");
    	ldapDnUserPri = properties.getProperty("ldapDnUserPri");
    	ldapDnUserAlt = properties.getProperty("ldapDnUserAlt");
    	ldapFiltPri = properties.getProperty("ldapFiltPri");
    	ldapFiltAlt = properties.getProperty("ldapFiltAlt");
    	ldapBasePri = properties.getProperty("ldapBasePri");
    	ldapBaseAlt = properties.getProperty("ldapBaseAlt");
    	ldapPortPri = Integer.parseInt(properties.getProperty("ldapPortPri"));
    	ldapPortAlt = Integer.parseInt(properties.getProperty("ldapPortAlt"));
    	showPrimAltLoginRadio = Boolean.parseBoolean(properties.getProperty("showPrimAltLoginRadio"));
    	emplpoyeeIdAttribute = properties.getProperty("emplpoyeeIdAttribute");
	}
	
	public String getLdapURLAlt() {
		return ldapURLAlt;
	}

	public void setLdapURLAlt(String ldapURLAlt) {
		this.ldapURLAlt = ldapURLAlt;
	}

	public String getLdapDnUserAlt() {
		return ldapDnUserAlt;
	}

	public void setLdapDnUserAlt(String ldapDnUserAlt) {
		this.ldapDnUserAlt = ldapDnUserAlt;
	}

	public String getLdapBaseAlt() {
		return ldapBaseAlt;
	}

	public void setLdapBaseAlt(String ldapBaseAlt) {
		this.ldapBaseAlt = ldapBaseAlt;
	}

	public int getLdapPortAlt() {
		return ldapPortAlt;
	}

	public void setLdapPortAlt(int ldapPortAlt) {
		this.ldapPortAlt = ldapPortAlt;
	}

	public String getLdapURLPri() {
		return ldapURLPri;
	}

	public void setLdapURLPri(String ldapURLPri) {
		this.ldapURLPri = ldapURLPri;
	}

	public String getLdapDnUserPri() {
		return ldapDnUserPri;
	}

	public void setLdapDnUserPri(String ldapDnUserPri) {
		this.ldapDnUserPri = ldapDnUserPri;
	}

	public String getLdapBasePri() {
		return ldapBasePri;
	}

	public void setLdapBasePri(String ldapBasePri) {
		this.ldapBasePri = ldapBasePri;
	}

	public int getLdapPortPri() {
		return ldapPortPri;
	}

	public void setLdapPortPri(int ldapPortPri) {
		this.ldapPortPri = ldapPortPri;
	}

	public boolean isShowPrimAltLoginRadio() {
		return showPrimAltLoginRadio;
	}

	public void setShowPrimAltLoginRadio(boolean showPrimAltLoginRadio) {
		this.showPrimAltLoginRadio = showPrimAltLoginRadio;
	}

	public String getLdapFiltAlt() {
		return ldapFiltAlt;
	}

	public void setLdapFiltAlt(String ldapFiltAlt) {
		this.ldapFiltAlt = ldapFiltAlt;
	}

	public String getLdapFiltPri() {
		return ldapFiltPri;
	}

	public void setLdapFiltPri(String ldapFiltPri) {
		this.ldapFiltPri = ldapFiltPri;
	}

	public String getEmplpoyeeIdAttribute() {
		return emplpoyeeIdAttribute;
	}

	public void setEmplpoyeeIdAttribute(String emplpoyeeIdAttribute) {
		this.emplpoyeeIdAttribute = emplpoyeeIdAttribute;
	}
	
}
