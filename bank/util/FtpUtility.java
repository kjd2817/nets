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
	
	// 다운로드
	public synchronized final boolean download(String address, String port, String id, String pwd, String srcDirectory, String srcFileName, String desDirectory) {
		boolean isSuccess = false;
		FTPClient ftpc = null;
		
		try {
			ftpc = new FTPClient();
			ftpc.setControlEncoding("KSC5601");
			
			ftpc.connect(address,Integer.parseInt(port));
			ftpc.login(id, pwd);
			ftpc.changeWorkingDirectory(srcDirectory);
			
			// FTP 작업경로에서 파일을 내부망의 지정 폴더에 넣는다.
			File downloadFile = new File(desDirectory, srcFileName);
			FileOutputStream fos = null;
			try {
				
				fos = new FileOutputStream(downloadFile);
				isSuccess = ftpc.retrieveFile(srcFileName, fos);
				if(isSuccess) {
					if(logger.isEnabledFor(Level.INFO)) {
						logger.info("FTP서버로부터 " + srcFileName + " 다운로드 작업이 성공 했습니다.");
					}	
				} else {
					if(logger.isEnabledFor(Level.WARN)) {
						logger.warn("FTP서버에 " + srcFileName + " 이 존재하지 않습니다.");
					}							
				}
			} catch (IOException ioe) {
				if(logger.isEnabledFor(Level.ERROR)) {
					logger.error("FTP서버로부터 " + srcFileName + " 다운로드 작업이 실패 했습니다. ("+ioe.getMessage()+")",ioe);
				}						
			} finally {
				if(fos != null) {
					try { fos.close(); } catch(IOException ioe) {}
				}
			}
			ftpc.logout();
		} catch (SocketException se) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("FTP서버로부터  " + srcFileName + " 다운로드 중 오류가 발생했습니다. ("+se.getMessage()+")",se);
			}				
		} catch (IOException ioe) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("FTP서버로부터 " + srcFileName + " 다운로드 중 오류가 발생했습니다. ("+ioe.getMessage()+")",ioe);
			}					
		} finally {
			if(ftpc != null && ftpc.isConnected()) {
				try { ftpc.disconnect(); } catch(IOException ioe) {}
			}
		}	
		
		return isSuccess;
	}
	
    /**
     * 	IF server 에 전송된 파일목록 다운로드후 원본파일 삭제.
     *	address 		: IF Server ip
     *	port			: port no
     *	id				: ftp login id
     *	pwd				: ftp login pwd
     *	srcDirectory	: IF server DIR(수신받은 파일폴더)
     *	srcFileName		: 파일명
     *	desDirectory	: 다운로드 폴더 (WAS DIR)
     *	ftpBackDir		: 은행연계Server 백업 폴더.
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
						logger.info("FTP서버로부터 " + desDirectory + ":" +srcFileName + " 다운로드 작업이 성공 했습니다.");
					}
				} else {
				}
			} catch (IOException ioe) {
				if(logger.isEnabledFor(Level.ERROR)) {
					logger.error("FTP서버로부터 " + srcFileName + " 다운로드 작업이 실패 했습니다. ("+ioe.getMessage()+")",ioe);
				}						
			} finally {
				if(fos != null) {
					try { fos.close(); } catch(IOException ioe) {}
				}
			}
			ftpc.logout();	
		} catch (SocketException se) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("1FTP서버로부터  " + srcFileName + " 다운로드 중 오류가 발생했습니다. ("+se.getMessage()+")",se);
			}				
		} catch (IOException ioe) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("2FTP서버로부터 " + srcFileName + " 다운로드 중 오류가 발생했습니다. ("+ioe.getMessage()+")",ioe);
			}					
		} finally {
			if(ftpc != null && ftpc.isConnected()) {
				try { ftpc.disconnect(); } catch(IOException ioe) {}
			}
		}	
		
		return isSuccess;
	}

	/*
	 * IF server 에 전송된 RFID 파일 리스트 객체 조회.
	 * @param address : server ip
	 * @param port    : 포트번호
	 * @param id      : 사용자 id
	 * @param pwd     : 비밀번호
	 * @param srcDirectory : 파일 수신 디렉토리
	 * @return
	 */
	public synchronized final FTPFile[] list(String address, String port, String id, String pwd, String srcDirectory) {
		boolean isSuccess = false;
		FTPClient ftpc = null;
		try {
            //Unix용
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
				logger.error("FTP서버로부터  list 중 오류가 발생했습니다. Socket ("+se.getMessage()+")",se);
			}				
		} catch (IOException ioe) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("FTP서버로부터  list 중 오류가 발생했습니다. ("+ioe.getMessage()+")",ioe);
			}					
		} finally {
			if(ftpc != null && ftpc.isConnected()) {
				try { ftpc.disconnect(); } catch(IOException ioe) {}
			}
		}	
		return null;
	}	
	
	// 업로드
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
						logger.info("FTP서버로 " + srcFileName + " 업로드 작업이 성공 했습니다.");
					}	
				} else {
					if(logger.isEnabledFor(Level.WARN)) {
						logger.warn("FTP서버에 " + srcDirectory  + srcFileName + " 이 존재하지 않습니다.");
					}							
				}
			} catch (IOException ioe) {
				if(logger.isEnabledFor(Level.ERROR)) {
					logger.error("FTP서버로 " + srcFileName + " 업로드 작업이 실패 했습니다. ("+ioe.getMessage()+")",ioe);
				}						
			} finally {
				if(fis != null) {
					try { fis.close(); } catch(IOException ioe) {}
				}
			}
			ftpc.logout();
		} catch (SocketException se) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("FTP서버로  " + srcFileName + " 다운로드 중 오류가 발생했습니다. ("+se.getMessage()+")",se);
			}				
		} catch (IOException ioe) {
			if(logger.isEnabledFor(Level.ERROR)) {
				logger.error("FTP서버로  " + srcFileName + " 다운로드 중 오류가 발생했습니다. ("+ioe.getMessage()+")",ioe);
			}					
		} finally {
			if(ftpc != null && ftpc.isConnected()) {
				try { ftpc.disconnect(); } catch(IOException ioe) {}
			}
		}
		
		return isSuccess;
	}
}
