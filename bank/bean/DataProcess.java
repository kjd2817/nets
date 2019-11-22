package bank.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import bank.util.FileUtility;
import bank.util.FtpUtility;

//import Aria32bit.ARIAEngine;


public class DataProcess {
	private static Logger logger = Logger.getLogger(DataProcess.class);
	
    /**
     * I/F Server 에 전송된 RFID 파일 리스트 객체 조회.
     * @param ftp_Server_ip 	: ftp server ip
     * @param ftp_server_port	: ftp server port no
     * @param ftp_login_id 		: ftp server login id
     * @param ftp_login_pwd		: ftp server login pwd
     * @param ftp_server_file	: ftp server direcoty
     * @return FTPFile[] 		: ftp server 폴더에 존재하는 FTPFile[] Object
     */
	public synchronized FTPFile[] list( String ftp_Server_ip	, 
										String ftp_server_port	,
										String ftp_login_id		, 
										String ftp_login_pwd	,
										String ftp_server_file) {
		FtpUtility ftpUtility = new FtpUtility();
		
		//	I/F Server 파일 List 객체 얻어오기.
		return ftpUtility.list(	ftp_Server_ip		, 
								ftp_server_port		,
								ftp_login_id		, 
								ftp_login_pwd		, 
								ftp_server_file);
	}
	
	/**
	 * 연계 Server 에 생성되 있는 파일을 DownLoad 한다.
	 * @param strFileName		: DownLoad File명
	 * @param ftpServerIP		: ftp server ip
	 * @param ftpServerPort		: ftp server port
	 * @param ftpServerID		: ftp server login id
	 * @param ftpServerPwd		: ftp server login pwd
	 * @param ftpServerDir		: ftp server 파일 디렉토리 정보
	 * @param downloadDir		: DownLoad 받을 파일 디렉토리
	 * @param ftpBackDir		: 은행연계Server 백업폴더
	 */
   
	public synchronized boolean hostGet(String strFileName		, 
										String ftpServerIP		, 
										String ftpServerPort	, 
										String ftpServerID		, 
										String ftpServerPwd		, 
										String ftpServerDir		,
										String downloadDir		,
										String ftpBackDir) {
		boolean isSuccess = false;
		
		// 소스파일 다운받은후 원본파일 삭제하기 
		FtpUtility ftpUtility = new FtpUtility();

        isSuccess = ftpUtility.downloadWithDelete(	ftpServerIP		,
        											ftpServerPort	,
        											ftpServerID		,
        											ftpServerPwd	,
        											ftpServerDir	, 
        											strFileName		, 
        											downloadDir		,
        											ftpBackDir);
		return isSuccess;
	}
	
	/**
	 * 
	 * @param strFileName			: 파일명
	 * @param ftp_Server_ip			: ftp server ip
	 * @param ftp_server_port		: ftp server port
	 * @param ftp_login_id			: ftp server id
	 * @param ftp_login_pwd			: ftp server pwd
	 * @param itas_reci_dir			: itas 수신 폴더
	 * @param itas_send_back		: itas 전송후 백업 폴더
	 * @return
	 */
	public synchronized boolean hostPut(String strFileName		, 
										String ftp_Server_ip	, 
										String ftp_server_port	, 
										String ftp_login_id		, 
										String ftp_login_pwd	, 
										String itas_reci_dir	,
										String ftp_bank1_back_dir,
										String itasBackDir) {
		boolean isSuccess = false;

		FtpUtility ftpUtility = new FtpUtility();
		isSuccess = ftpUtility.upload(	ftp_Server_ip, 
										ftp_server_port,
										ftp_login_id, 
										ftp_login_pwd, 
										itas_reci_dir, 
										strFileName, 
										ftp_bank1_back_dir);
		
		FileUtility fileUtility = new FileUtility();
		
		/**
		 * 파일 이동하기. 파일 이동후 원본파일은 삭제한다.
		 * @param fileInDirectory		: 원본파일 디렉토리 정보
		 * @param fileInName			: 원본 파일명
		 * @param fileOutDirectory		: 복사될 디렉토리 정보
		 * @param fileOutName			: 복사후 파일명
		 * @throws FileNotFoundException
		 * @throws IOException
		 */
		
		try {

			fileUtility.fileMove(itas_reci_dir, strFileName, itasBackDir, strFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return false;
	}
	
	/**
	 * 
	 * @param strFileName			: 파일명
	 * @param ftp_Server_ip			: ftp server ip
	 * @param ftp_server_port		: ftp server port
	 * @param ftp_login_id			: ftp server id
	 * @param ftp_login_pwd			: ftp server pwd
	 * @param itas_reci_dir			: itas 수신 폴더
	 * @return
	 */
	public synchronized boolean hostPut(String strFileName		, 
										String ftp_Server_ip	, 
										String ftp_server_port	, 
										String ftp_login_id		, 
										String ftp_login_pwd	, 
										String itas_reci_dir	,
										String ftp_bank1_back_dir) {
		boolean isSuccess = false;

		FtpUtility ftpUtility = new FtpUtility();
		isSuccess = ftpUtility.upload(	ftp_Server_ip, 
										ftp_server_port,
										ftp_login_id, 
										ftp_login_pwd, 
										itas_reci_dir, 
										strFileName, 
										ftp_bank1_back_dir);
		

//		try {
//
//			fileUtility.fileMove(itas_reci_dir, strFileName, itasBackDir, strFileName);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 
		return false;
	}
		
}
