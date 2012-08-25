package utility.logger;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import utility.Constants;


public class AppRootLogger {
	
	private static Logger appRootLogger = null;
	
	public AppRootLogger()
	{
		if (appRootLogger == null)
		{
			try
			{
				InitialContext iCtx = new InitialContext(); 
				Context ctx = (Context) iCtx.lookup("java:comp/env"); 
				String logBasePath = (String)ctx.lookup("logBasePath");
				String logFileName = (String)ctx.lookup("logFileName");

				Layout layout = new PatternLayout(Constants.LOG_PATTERN);
				RollingFileAppender rootAppender = new RollingFileAppender(layout, logBasePath+logFileName );
				rootAppender.setMaxBackupIndex(3);
				rootAppender.setMaxFileSize("250MB");
				rootAppender.setImmediateFlush(true);
			
				appRootLogger = Logger.getRootLogger();
				appRootLogger.addAppender(rootAppender);
				
			}
			catch (Exception e)
			{
//				System.out.println(e.getMessage());
//				e.printStackTrace();
			}

		}
			
	}

}
