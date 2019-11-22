package bank.run;

import java.io.File;


import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import bank.bean.DataProcess;
import bank.property.SystemBankProperty;
import bank.property.SystemBankValue;
import bank.util.DateUtility;
import bank.util.FileUtility;

public class BankSendReceiveDaemon {
    private static Logger logger = Logger.getLogger(BankSendReceiveDaemon.class);
    
	public static String FTP_BANK1_SERVER_IP;
	public static String FTP_BANK1_SERVER_PORT;
	public static String FTP_BANK1_SERVER_ID;
	public static String FTP_BANK1_SERVER_PWD;
	public static String FTP_BANK1_SEND_DIR;
	public static String FTP_BANK1_RECI_DIR;
	public static String FTP_BANK1_BACK_DIR;

	public static String FTP_BANK2_SERVER_IP;
	public static String FTP_BANK2_SERVER_PORT;
	public static String FTP_BANK2_SERVER_ID;
	public static String FTP_BANK2_SERVER_PWD;
	public static String FTP_BANK2_SEND_DIR;
	public static String FTP_BANK2_RECI_DIR;
	public static String FTP_BANK2_BACK_DIR;
	
	public static String FTP_BANK3_SERVER_IP;
	public static String FTP_BANK3_SERVER_PORT;
	public static String FTP_BANK3_SERVER_ID;
	public static String FTP_BANK3_SERVER_PWD;
	public static String FTP_BANK3_SEND_DIR;
	public static String FTP_BANK3_RECI_DIR;
	public static String FTP_BANK3_BACK_DIR;

	public static String ITAS_RECI_DIR;
	public static String ITAS_SEND_DIR;
	public static String ITAS_BACK_DIR;
	
	public static String DELAY_TIME;
	public static String FTP_FILE_CNT;
	
	public static String FTP_SERVER_IP;
	public static String FTP_SERVER_PORT;
	public static String FTP_SERVER_ID;
	public static String FTP_SERVER_PWD;
	public static String FTP_SERVER_BACK_DIR;
	public static String FTP_SERVER_RECI_DIR;
	
    public static void main(String[] args) {

        if(SystemBankProperty.getSystemBankProperty()) {
        	FTP_BANK1_SERVER_IP		= SystemBankValue.FTP_BANK1_SERVER_IP;
        	FTP_BANK1_SERVER_PORT	= SystemBankValue.FTP_BANK1_SERVER_PORT;
        	FTP_BANK1_SERVER_ID		= SystemBankValue.FTP_BANK1_SERVER_ID;
        	FTP_BANK1_SERVER_PWD	= SystemBankValue.FTP_BANK1_SERVER_PWD;
        	FTP_BANK1_SEND_DIR		= SystemBankValue.FTP_BANK1_SEND_DIR;
        	FTP_BANK1_RECI_DIR		= SystemBankValue.FTP_BANK1_RECI_DIR;
        	FTP_BANK1_BACK_DIR		= SystemBankValue.FTP_BANK1_BACK_DIR;
     
           	FTP_BANK2_SERVER_IP		= SystemBankValue.FTP_BANK2_SERVER_IP;
        	FTP_BANK2_SERVER_PORT	= SystemBankValue.FTP_BANK2_SERVER_PORT;
        	FTP_BANK2_SERVER_ID		= SystemBankValue.FTP_BANK2_SERVER_ID;
        	FTP_BANK2_SERVER_PWD	= SystemBankValue.FTP_BANK2_SERVER_PWD;
        	FTP_BANK2_SEND_DIR		= SystemBankValue.FTP_BANK2_SEND_DIR;
        	FTP_BANK2_RECI_DIR		= SystemBankValue.FTP_BANK2_RECI_DIR;
        	FTP_BANK2_BACK_DIR		= SystemBankValue.FTP_BANK2_BACK_DIR;
        	
           	FTP_BANK3_SERVER_IP		= SystemBankValue.FTP_BANK3_SERVER_IP;
        	FTP_BANK3_SERVER_PORT	= SystemBankValue.FTP_BANK3_SERVER_PORT;
        	FTP_BANK3_SERVER_ID		= SystemBankValue.FTP_BANK3_SERVER_ID;
        	FTP_BANK3_SERVER_PWD	= SystemBankValue.FTP_BANK3_SERVER_PWD;
        	FTP_BANK3_SEND_DIR		= SystemBankValue.FTP_BANK3_SEND_DIR;
        	FTP_BANK3_RECI_DIR		= SystemBankValue.FTP_BANK3_RECI_DIR;
        	FTP_BANK3_BACK_DIR		= SystemBankValue.FTP_BANK3_BACK_DIR;
        	
        	ITAS_RECI_DIR			= SystemBankValue.ITAS_RECI_DIR;
        	ITAS_SEND_DIR			= SystemBankValue.ITAS_SEND_DIR;
        	ITAS_BACK_DIR			= SystemBankValue.ITAS_BACK_DIR;
        	
        	DELAY_TIME				= SystemBankValue.DELAY_TIME;
        	FTP_FILE_CNT			= SystemBankValue.FTP_FILE_CNT;
        }
              
        logger.info("================ ���� ���� Server File Upload & DownLoad Process Start ===================");

        while(true) {
//        	for (int i = 0; i < 3; i++){
        	for (int i = 0; i < 1; i++){
        		if (i == 0 ) {
        		
    			
        			FileDown(FTP_BANK1_SERVER_IP, FTP_BANK1_SERVER_PORT, FTP_BANK1_SERVER_ID, FTP_BANK1_SERVER_PWD, FTP_BANK1_SEND_DIR, ITAS_RECI_DIR, FTP_BANK1_BACK_DIR );
        		} 
        		else if (i == 1){
        		
        			FileDown(FTP_BANK2_SERVER_IP, FTP_BANK2_SERVER_PORT, FTP_BANK2_SERVER_ID, FTP_BANK2_SERVER_PWD, FTP_BANK2_SEND_DIR, ITAS_RECI_DIR, FTP_BANK2_BACK_DIR );
        		} else {
        		
        			FileDown(FTP_BANK3_SERVER_IP, FTP_BANK3_SERVER_PORT, FTP_BANK3_SERVER_ID, FTP_BANK3_SERVER_PWD, FTP_BANK3_SEND_DIR, ITAS_RECI_DIR, FTP_BANK3_BACK_DIR );
        		}
        	}
        	
        	FileUpload(FTP_BANK1_SERVER_IP, FTP_BANK1_SERVER_PORT, FTP_BANK1_SERVER_ID, FTP_BANK1_SERVER_PWD, FTP_BANK1_RECI_DIR , ITAS_SEND_DIR, ITAS_BACK_DIR);
        	     
//        	FileUploadBackup(FTP_BANK1_SERVER_IP, FTP_BANK1_SERVER_PORT, FTP_BANK1_SERVER_ID, FTP_BANK1_SERVER_PWD, FTP_BANK1_BACK_DIR , ITAS_RECI_DIR);

        	
        	logger.info("================ Server FileDownload & Upload Process End !!" +  DELAY_TIME + "�� �� �簡�� �˴ϴ�. ================");
        	
            try {
            	Thread.sleep(1000 * 60 * Integer.parseInt(DELAY_TIME));
            } catch (InterruptedException ie) {
                if(logger.isEnabledFor(Level.ERROR)) {
                    logger.info("InterruptedException :" + ie.toString());
                }
            }
        }
    }
    
  /**
   * ���� ���� ������ ������ ������ �����ϴ°�� ������ �ٿ�ε� �Ѵ�
   * ���� ���輭�� -> ITAS Server
   * @param ftpServerIP		: ftp server ip
   * @param ftpServerPort	: ftp server port
   * @param ftpServerID		: ftp server login id
   * @param ftpServerPwd	: ftp server login pwd
   * @param ftpServerDir    : ���� File ���丮 ����
   * @param downloadDir		: �ٿ�ε� ���� ���丮 ����
   * @param ftpBackDir		: ����Server ��� ����
   */
    public static void FileDown(String ftpServerIP	, 
    							String ftpServerPort, 
    							String ftpServerID	, 
    							String ftpServerPwd	, 
    							String ftpServerDir	, 
    							String downloadDir 	,
    							String ftpBackDir
    							 ) {
    	
    	DataProcess dp 			= new DataProcess();
        String strFileName 		= "";
        FTPFile[] arrFTPFile 	= dp.list(ftpServerIP, ftpServerPort, ftpServerID, ftpServerPwd, ftpServerDir);
         
        if (arrFTPFile.length == 0) {
        	logger.info("************** FTP Server IP :" + ftpServerIP + " �� ������ ������ �����ϴ�. **************" );
        }
        
        for(int i = 1; i < arrFTPFile.length; i++){
        	if ( arrFTPFile[i].isFile() ) {
        		if (!arrFTPFile[i].isDirectory()) {
        			strFileName = arrFTPFile[i].getName();
        			if( dp.hostGet(strFileName, ftpServerIP, ftpServerPort, ftpServerID, ftpServerPwd, ftpServerDir, downloadDir, ftpBackDir) ){

            		} else {
            			logger.debug("************ FTP Server IP : " + ftpServerIP + " DownLoad ��  Exception �� �߻� �Ͽ����ϴ�. ************");
            		}
        		}
        	}
        }
    }

    
    /**
     * FTP Server ���� ���� ITAS -> IF ����Server 
     * @param ftpServerIP		: ftp server ip
     * @param ftpServerPort		: ftp server port
     * @param ftpServerID		: ftp server id
     * @param ftpServerPwd		: ftp server pwd
     * @param ftpServerDir		: ftp server directory (upload ���丮) 
     * @param itasSendDir		: ���۴�� ���� directory
     * @param itasBackDir		: ������ ��� ���� ���� directory
     */
    public static void FileUpload(	String ftpServerIP	, 
    								String ftpServerPort, 
    								String ftpServerID	, 
    								String ftpServerPwd	, 
    								String ftpServerDir	, 
    								String itasSendDir	, 
    								String itasBackDir) {
    	DataProcess dp 	= new DataProcess();
    	File sendFile = new File(itasSendDir);
    	
    	File[] listFile = sendFile.listFiles();
    	
    	String strFileName = "";
   
    	if (listFile.length == 0) {
    		logger.debug("****************** IP : " + ftpServerIP + " => " + itasSendDir +" ������ ���۴�� ������ ���� ���� �ʽ��ϴ�. ******************");
    	}
    	
    	for(int i = 0; i < listFile.length; i++){
    		if (listFile[i].isFile()) {
    			if (!listFile[i].isDirectory()) {
    				strFileName = listFile[i].getName();
    				if(dp.hostPut(strFileName, ftpServerIP, ftpServerPort, ftpServerID, ftpServerPwd, itasSendDir, ftpServerDir, itasBackDir)){
    					
    				}
    			}
    		}
    	}
    }
    

    /**
     * FTP Server ���� ���� ITAS -> IF ����Server 
     * @param ftpServerIP		: ftp server ip
     * @param ftpServerPort		: ftp server port
     * @param ftpServerID		: ftp server id
     * @param ftpServerPwd		: ftp server pwd
     * @param ftpServerDir		: ftp server directory (upload ���丮) 
     * @param itasSendDir		: ���۴�� ���� directory
     * @param itasBackDir		: ������ ��� ���� ���� directory
     */
    public static void FileUploadBackup(	String ftpServerIP	, 
    										String ftpServerPort, 
    										String ftpServerID	, 
    										String ftpServerPwd	, 
    										String ftpServerDir	, 
    										String itasSendDir) {
    	DataProcess dp 	= new DataProcess();
    	File sendFile = new File(itasSendDir);
    	
    	File[] listFile = sendFile.listFiles();
    	
    	String strFileName = "";
   
    	if (listFile.length == 0) {
    		logger.debug("****************** ���۴�� ������ ���� ���� �ʽ��ϴ�. ******************");
    	}
    	
    	for(int i = 0; i < listFile.length; i++){
    		if (listFile[i].isFile()) {
    			if (!listFile[i].isDirectory()) {
    				strFileName = listFile[i].getName();
    				if(dp.hostPut(strFileName, ftpServerIP, ftpServerPort, ftpServerID, ftpServerPwd, itasSendDir, ftpServerDir)){
    					
    				}
    			}
    		}
    	}
    }
}
