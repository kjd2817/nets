package bank.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class SystemBankProperty {
	private static Logger logger = Logger.getLogger(SystemBankProperty.class);
	
	//properties 정보 Loading
	public static boolean getSystemBankProperty() {
		
		if(SystemBankValue.alreadyBinding) {
			return true;
		}
		InputStream	is = SystemBankValue.class.getResourceAsStream("/BankSystem.properties");

		Properties	properties = new Properties();
		
		try {
		//	FileInputStream fis = new FileInputStream("C:/University/university.properties");
			properties.load(is);
		} catch (IOException ioe) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("Can not read System property file. ("+ioe.getMessage()+")",ioe);
			}
			
			return false;
		}
		
		
		SystemBankValue.FTP_BANK1_SERVER_IP		= properties.getProperty("FTP_BANK1_SERVER_IP");
		SystemBankValue.FTP_BANK1_SERVER_PORT	= properties.getProperty("FTP_BANK1_SERVER_PORT");
		SystemBankValue.FTP_BANK1_SERVER_ID		= properties.getProperty("FTP_BANK1_SERVER_ID");
		SystemBankValue.FTP_BANK1_SERVER_PWD	= properties.getProperty("FTP_BANK1_SERVER_PWD");
		SystemBankValue.FTP_BANK1_SEND_DIR		= properties.getProperty("FTP_BANK1_SEND_DIR");
		SystemBankValue.FTP_BANK1_RECI_DIR		= properties.getProperty("FTP_BANK1_RECI_DIR");
		SystemBankValue.FTP_BANK1_BACK_DIR		= properties.getProperty("FTP_BANK1_BACK_DIR");
		
		SystemBankValue.FTP_BANK2_SERVER_IP		= properties.getProperty("FTP_BANK2_SERVER_IP");
		SystemBankValue.FTP_BANK2_SERVER_PORT	= properties.getProperty("FTP_BANK2_SERVER_PORT");
		SystemBankValue.FTP_BANK2_SERVER_ID		= properties.getProperty("FTP_BANK2_SERVER_ID");
		SystemBankValue.FTP_BANK2_SERVER_PWD	= properties.getProperty("FTP_BANK2_SERVER_PWD");
		SystemBankValue.FTP_BANK2_SEND_DIR		= properties.getProperty("FTP_BANK2_SEND_DIR");
		SystemBankValue.FTP_BANK2_RECI_DIR		= properties.getProperty("FTP_BANK2_RECI_DIR");
		SystemBankValue.FTP_BANK2_BACK_DIR		= properties.getProperty("FTP_BANK2_BACK_DIR");
		
		SystemBankValue.FTP_BANK3_SERVER_IP		= properties.getProperty("FTP_BANK3_SERVER_IP");
		SystemBankValue.FTP_BANK3_SERVER_PORT	= properties.getProperty("FTP_BANK3_SERVER_PORT");
		SystemBankValue.FTP_BANK3_SERVER_ID		= properties.getProperty("FTP_BANK3_SERVER_ID");
		SystemBankValue.FTP_BANK3_SERVER_PWD	= properties.getProperty("FTP_BANK3_SERVER_PWD");
		SystemBankValue.FTP_BANK3_SEND_DIR		= properties.getProperty("FTP_BANK3_SEND_DIR");
		SystemBankValue.FTP_BANK3_RECI_DIR		= properties.getProperty("FTP_BANK3_RECI_DIR");
		SystemBankValue.FTP_BANK3_BACK_DIR		= properties.getProperty("FTP_BANK3_BACK_DIR");
		
		
		SystemBankValue.ITAS_RECI_DIR			= properties.getProperty("ITAS_RECI_DIR");
		SystemBankValue.ITAS_SEND_DIR			= properties.getProperty("ITAS_SEND_DIR");
		SystemBankValue.ITAS_BACK_DIR			= properties.getProperty("ITAS_BACK_DIR");
		
		SystemBankValue.FTP_FILE_CNT			= properties.getProperty("FTP_FILE_CNT");
		SystemBankValue.DELAY_TIME				= properties.getProperty("DELAY_TIME");
		
		
	
		// property 정보 바인딩여부
		SystemBankValue.alreadyBinding = true;
		
		return true;
	}
}
