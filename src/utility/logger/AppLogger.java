package utility.logger;

import org.apache.log4j.*;

public class AppLogger extends Logger 
{

	private String FQCN = AppLogger.class.getName() + ".";
	private static AppLoggerFactory appLoggerFactory = new AppLoggerFactory();
	private String username = "";

	public AppLogger(String name) 
	{
		super(name);
	}

	public void debug(Object message) 
	{
		//super.log(FQCN, Level.DEBUG, username + message, null);    
		super.log(FQCN, Level.DEBUG, alterMessage(message), null);    
	}
	
	public void info(Object message) 
	{
		//super.log(FQCN, Level.DEBUG, username + message, null);    
		super.log(FQCN, Level.INFO, alterMessage(message), null);    
	}

	public void warn(Object message) 
	{
		//super.log(FQCN, Level.DEBUG, username + message, null);    
		super.log(FQCN, Level.WARN, alterMessage(message), null);    
	}

	public void error(Object message) 
	{
		//super.log(FQCN, Level.DEBUG, username + message, null);    
		super.log(FQCN, Level.ERROR, alterMessage(message), null);    
	}

	public void fatal(Object message) 
	{
		//super.log(FQCN, Level.DEBUG, username + message, null);    
		super.log(FQCN, Level.FATAL, alterMessage(message), null);    
	}
	
	private Object alterMessage ( Object message)
	{
		return message;
	}
	
	public static Logger getLogger(String name) 
	{
		
		new AppRootLogger();
		
		return Logger.getLogger(name, appLoggerFactory); 
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String str)
	{
		if ( str != null )
			username = "[" + str.toUpperCase() + "] ";
	}
}