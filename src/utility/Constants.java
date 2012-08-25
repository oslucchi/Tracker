package utility;


import utility.logger.AppLogger;

public class Constants {
	static final long serialVersionUID = 1;

	/**
	public static final int SOTTOTIPO_LINEA_0 = 0; 
	public static final int SOTTOTIPO_LINEA_1 = 1; 

	public static final int PARAM_TIPOLOGIA_LINEA_0 									= 1;
	public static final int PARAM_TIPOLOGIA_LINEA_NORMALE 								= 2;
	**/
	
    public static final int DB_ACCESS_PAGE_SIZE = 20;

	public static final String	LOG_PATTERN = "[%5p] %d{dd-MM-yyyy HH:mm:ss,SSS} (%F:%M:%L) %m%n";
	public static final String	DATE_FORMAT = "dd-MM-yyyy";
	
    private static AppLogger log = (AppLogger) AppLogger.getLogger(Constants.class.getName());
	public static int iStabilimento = 0;
	
	
    public Constants()
	{
    	log.debug("Start function");
    	return;
	}
    
    public Constants(String sName)
    {
		return;
    }
}
