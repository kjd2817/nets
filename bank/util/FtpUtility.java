package bank.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class FtpUtility {
	private static Logger logger = Logger.getLogger(FtpUtility.class);
	
	// �ٿ�ε�
	public synchronized final boolean download(String address, String port, String id, String pwd, String srcDirectory, String srcFileName, String desDirectory) {
		boolean isSuccess = false;
		FTPClient ftpc = null;
		
		try {
			ftpc = new FTPClient();
			ftpc.setControlEncoding("KSC5601");
			
			ftpc.connect(address,Integer.parseInt(port));
			ftpc.login(id, pwd);
			ftpc.changeWorkingDirectory(srcDirectory);
			
			// FTP �۾���ο��� ������ ���θ��� ���� ������ �ִ´�.
			File downloadFile = new File(desDirectory, srcFileName);
			FileOutputStream fos = null;
			try {
				
				fos = new FileOutputStream(downloadFile);
				isSuccess = ftpc.retrieveFile(srcFileName, fos);
				if(isSuccess) {
					if(logger.isEnabledFor(Level.INFO)) {
						logger.info("FTP�����κ��� " + srcFileName + " �ٿ�ε� �۾��� ���� �߽��ϴ�.");
					}	
				} else {
					if(logger.isEnabledFor(Level.WARN)) {
						logger.warn("FTP������ " + srcFileName + " �� �������� �ʽ��ϴ�.");
					}							
				}
			} catch (IOException ioe) {
				if(logger.isEnabledFor(Level.ERROR)) {
					logger.error("FTP�����κ��� " + srcFileName + " �ٿ�ε� �۾��� ���� �߽��ϴ�. ("+ioe.getMessage()+")",ioe);
				}						
			} finally {
				if(fos != null) {
					try { fos.close(); } catch(IOException ioe) {}
				}
			}
			ftpc.logout();
		} catch (SocketException se) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("FTP�����κ���  " + srcFileName + " �ٿ�ε� �� ������ �߻��߽��ϴ�. ("+se.getMessage()+")",se);
			}				
		} catch (IOException ioe) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("FTP�����κ��� " + srcFileName + " �ٿ�ε� �� ������ �߻��߽��ϴ�. ("+ioe.getMessage()+")",ioe);
			}					
		} finally {
			if(ftpc != null && ftpc.isConnected()) {
				try { ftpc.disconnect(); } catch(IOException ioe) {}
			}
		}	
		
		return isSuccess;
	}
	
    /**
     * 	IF server �� ���۵� ���ϸ�� �ٿ�ε��� �������� ����.
     *	address 		: IF Server ip
     *	port			: port no
     *	id				: ftp login id
     *	pwd				: ftp login pwd
     *	srcDirectory	: IF server DIR(���Ź��� ��������)
     *	srcFileName		: ���ϸ�
     *	desDirectory	: �ٿ�ε� ���� (WAS DIR)
     *	ftpBackDir		: ���࿬��Server ��� ����.
     */
	public synchronized final boolean downloadWithDelete(
														String address		, 
														String port			, 
														String id			, 
														String pwd			, 
														String srcDirectory	, 
														String srcFileName	, 
														String desDirectory	,
														String ftpBackDir	) {
			DateUtility dateUtil = new DateUtility();
			boolean isSuccess = false;
			FTPClient ftpc = null;
			try {
		
			ftpc = new FTPClient();
			ftpc.setControlEncoding("KSC5601");
			
			ftpc.connect(address,Integer.parseInt(port));
			ftpc.login(id, pwd);
			ftpc.changeWorkingDirectory(srcDirectory);
			File downloadFile = new File(desDirectory, srcFileName);
		
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(downloadFile);
				isSuccess = ftpc.retrieveFile(srcFileName, fos);
				if(isSuccess) {
					File upFile = new File(desDirectory + srcFileName);
					InputStream in = new FileInputStream(upFile);
//					ftpc.makeDirectory("/cdk/data/itas_end/" + dateUtil.getyyyyMMdd());
//					ftpc.storeFile("/cdk/data/itas_end/" + dateUtil.getyyyyMMdd() + "/" + srcFileName, in);
					ftpc.makeDirectory(ftpBackDir + dateUtil.getyyyyMMdd());
					ftpc.storeFile(ftpBackDir + dateUtil.getyyyyMMdd() + "/" + srcFileName, in);
					ftpc.deleteFile(srcFileName);
					if(logger.isEnabledFor(Level.INFO)) {
						logger.info("FTP�����κ��� " + desDirectory + ":" +srcFileName + " �ٿ�ε� �۾��� ���� �߽��ϴ�.");
					}
				} else {
				}
			} catch (IOException ioe) {
				if(logger.isEnabledFor(Level.ERROR)) {
					logger.error("FTP�����κ��� " + srcFileName + " �ٿ�ε� �۾��� ���� �߽��ϴ�. ("+ioe.getMessage()+")",ioe);
				}						
			} finally {
				if(fos != null) {
					try { fos.close(); } catch(IOException ioe) {}
				}
			}
			ftpc.logout();	
		} catch (SocketException se) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("1FTP�����κ���  " + srcFileName + " �ٿ�ε� �� ������ �߻��߽��ϴ�. ("+se.getMessage()+")",se);
			}				
		} catch (IOException ioe) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("2FTP�����κ��� " + srcFileName + " �ٿ�ε� �� ������ �߻��߽��ϴ�. ("+ioe.getMessage()+")",ioe);
			}					
		} finally {
			if(ftpc != null && ftpc.isConnected()) {
				try { ftpc.disconnect(); } catch(IOException ioe) {}
			}
		}	
		
		return isSuccess;
	}

	/*
	 * IF server �� ���۵� RFID ���� ����Ʈ ��ü ��ȸ.
	 * @param address : server ip
	 * @param port    : ��Ʈ��ȣ
	 * @param id      : ����� id
	 * @param pwd     : ��й�ȣ
	 * @param srcDirectory : ���� ���� ���丮
	 * @return
	 */
	public synchronized final FTPFile[] list(String address, String port, String id, String pwd, String srcDirectory) {
		boolean isSuccess = false;
		FTPClient ftpc = null;
		try {
            //Unix��
            FTPClientConfig config = new FTPClientConfig(FTPClientConfig.SYST_MVS);  
            config.setServerLanguageCode("ko");
            config.setDefaultDateFormatStr("dd MMM yyyy");
            config.setRecentDateFormatStr("M dd hh:mm");
            
			ftpc = new FTPClient();
			ftpc.configure(config);
			ftpc.setControlEncoding("KSC5601");
			ftpc.connect(address,Integer.parseInt(port));
			ftpc.login(id, pwd);
            ftpc.changeWorkingDirectory(srcDirectory);
            
			FTPFile[] arrFTPFile = null;
			arrFTPFile = ftpc.listFiles();

			return arrFTPFile;
			
		} catch (SocketException se) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("FTP�����κ���  list �� ������ �߻��߽��ϴ�. Socket ("+se.getMessage()+")",se);
			}				
		} catch (IOException ioe) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("FTP�����κ���  list �� ������ �߻��߽��ϴ�. ("+ioe.getMessage()+")",ioe);
			}					
		} finally {
			if(ftpc != null && ftpc.isConnected()) {
				try { ftpc.disconnect(); } catch(IOException ioe) {}
			}
		}	
		return null;
	}	
	
	// ���ε�
	public synchronized final boolean upload(String address, String port, String id, String pwd, String srcDirectory, String srcFileName, String desDirectory) {
		boolean isSuccess = false;
		FTPClient ftpc = null;
		try {
			ftpc = new FTPClient();
			ftpc.setControlEncoding("KSC5601");	
			
			ftpc.connect(address, Integer.parseInt(port));
			ftpc.login(id, pwd);
			ftpc.changeWorkingDirectory(desDirectory);
			
			File uploadFile = new File(srcDirectory, srcFileName);
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(uploadFile);
				isSuccess = ftpc.storeFile(srcFileName, fis);
				if(isSuccess) {
					if(logger.isEnabledFor(Level.INFO)) {
						logger.info("FTP������ " + srcFileName + " ���ε� �۾��� ���� �߽��ϴ�.");
					}	
				} else {
					if(logger.isEnabledFor(Level.WARN)) {
						logger.warn("FTP������ " + srcDirectory  + srcFileName + " �� �������� �ʽ��ϴ�.");
					}							
				}
			} catch (IOException ioe) {
				if(logger.isEnabledFor(Level.ERROR)) {
					logger.error("FTP������ " + srcFileName + " ���ε� �۾��� ���� �߽��ϴ�. ("+ioe.getMessage()+")",ioe);
				}						
			} finally {
				if(fis != null) {
					try { fis.close(); } catch(IOException ioe) {}
				}
			}
			ftpc.logout();
		} catch (SocketException se) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("FTP������  " + srcFileName + " �ٿ�ε� �� ������ �߻��߽��ϴ�. ("+se.getMessage()+")",se);
			}				
		} catch (IOException ioe) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("FTP������  " + srcFileName + " �ٿ�ε� �� ������ �߻��߽��ϴ�. ("+ioe.getMessage()+")",ioe);
			}					
		} finally {
			if(ftpc != null && ftpc.isConnected()) {
				try { ftpc.disconnect(); } catch(IOException ioe) {}
			}
		}
		
		return isSuccess;
	}
}
