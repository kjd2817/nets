package bank.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

public class FileUtility {
	private static Logger logger = Logger.getLogger(FileUtility.class);
	// ���� �б�
	public synchronized final String fileRead(String fileInDirectory, String fileInName, int capacity) throws FileNotFoundException, IOException {
		File inFile = new File(fileInDirectory, fileInName);
		FileChannel inFileChannel = (new FileInputStream(inFile)).getChannel();
		ByteBuffer inBB = ByteBuffer.allocateDirect(capacity);
		Charset charset = Charset.forName("KSC5601");
		
		if(inFile.exists()==false) {
			throw new FileNotFoundException();
		}
		if(inFile.canRead()==false) {
			throw new IOException(inFile.getName() + "������ ���� �� �����ϴ�.");
		}
		
		String data = "";
		if(inFileChannel.read(inBB)>0) {
			inBB.flip();
			data = String.valueOf(charset.decode(inBB));
			inBB.clear();
		}
		inFileChannel.close();
		return data;
	}
	
	// ���� �б�
	public synchronized final String fileRead(String fileInDirectory, String fileInName, int capacity, int limit) throws FileNotFoundException, IOException {
		File inFile = new File(fileInDirectory, fileInName);
		FileChannel inFileChannel = (new FileInputStream(inFile)).getChannel();
		ByteBuffer inBB = ByteBuffer.allocateDirect(capacity);
		Charset charset = Charset.forName("KSC5601");

		if(inFile.exists()==false) {
			throw new FileNotFoundException();
		}
		if(inFile.canRead()==false) {
			throw new IOException(inFile.getName() + "������ ���� �� �����ϴ�.");
		}
		
		String data = "";
		if(inFileChannel.read(inBB)>0) {
			inBB.flip();
			data = String.valueOf(charset.decode(inBB)).substring(0,limit);
			inBB.clear();
		}
		inFileChannel.close();
		return data;
	}

	// ���� Ư�� ��ġ �б�
	public synchronized final String fileReadWithPosition(String fileInDirectory, String fileInName, int capacity, long position) throws FileNotFoundException, IOException {
		File inFile = new File(fileInDirectory, fileInName);
		FileChannel inFileChannel = (new FileInputStream(inFile)).getChannel();
		ByteBuffer inBB = ByteBuffer.allocateDirect(capacity);
		Charset charset = Charset.forName("KSC5601");
		
		if(inFile.exists()==false) {
			throw new FileNotFoundException();
		}
		if(inFile.canRead()==false) {
			throw new IOException(inFile.getName() + "������ ���� �� �����ϴ�.");
		}
		
		String data = "";
		if(inFileChannel.read(inBB, position)>0) {
			inBB.flip();
			data = String.valueOf(charset.decode(inBB));
			inBB.clear();
		}
		inFileChannel.close();
		
		return data;
	}
	
	// ���� ����
	public synchronized final void fileWrite(String fileInDirectory, String fileInName, String data) throws FileNotFoundException, IOException {
		File inFile = new File(fileInDirectory, fileInName);
		
		if(inFile.exists()==false) {
			inFile.createNewFile();
		}
		
		if(inFile.canWrite()==false) {
			throw new IOException(inFile.getName() + "���Ͽ� �� �� �����ϴ�.");
		}
		
		ByteBuffer bb = StringUtility.getByteBuffer(data);
		bb.flip();
		FileChannel inFileChannel = (new FileOutputStream(inFile)).getChannel();
		inFileChannel.write(bb);
		inFileChannel.close();
	}
	
	// ���Ͽ� �߰��Ͽ� ����
	public synchronized final void fileWriteAppend(String fileInDirectory, String fileInName, String data) throws FileNotFoundException, IOException {
		File inFile= new File(fileInDirectory, fileInName);
		
		if(inFile.exists()==false) {
			inFile.createNewFile();
		} 
		
		if(inFile.canWrite()==false) {
			throw new IOException(inFile.getName() + "���Ͽ� �� �� �����ϴ�.");
		}
		
		ByteBuffer bb = StringUtility.getByteBuffer(data);
		bb.flip();
		FileChannel inFileChannel = (new FileOutputStream(inFile,true)).getChannel();
		inFileChannel.write(bb);
		inFileChannel.close();
	}
	
	// ���� �����ϱ�
	public synchronized final void fileCopy(String fileInDirectory, String fileInName, String fileOutDirectory, String fileOutName) throws FileNotFoundException, IOException {
		File inFile = new File(fileInDirectory, fileInName);
		FileChannel inFileChannel = (new FileInputStream(inFile)).getChannel();
		
		if(inFile.exists() == false) {
			throw new FileNotFoundException();
		}
		if(inFile.canRead()==false) {
			throw new IOException(inFile.getName() + "������ ���� �� �����ϴ�.");
		}
		
		File outFile = new File(fileOutDirectory, fileOutName);
        
		FileChannel outFileChannel = (new FileOutputStream(outFile)).getChannel();
		
		inFileChannel.transferTo(0,inFileChannel.size(),outFileChannel);
		
		inFileChannel.close();
		outFileChannel.close();
	}

	// ���� �̵��ϱ�
	public synchronized final void fileMove(String fileInDirectory, String fileInName, String fileOutDirectory, String fileOutName) throws FileNotFoundException, IOException {
		File inFile = new File(fileInDirectory, fileInName);
		FileChannel inFileChannel = (new FileInputStream(inFile)).getChannel();
		
		if(inFile.exists()==false) {
			throw new FileNotFoundException();
		}
		if(inFile.canRead()==false) {
			throw new IOException(inFile.getName() + "������ ���� �� �����ϴ�.");
		}
		
		File outFile = new File(fileOutDirectory, fileOutName);
		FileChannel outFileChannel = (new FileOutputStream(outFile)).getChannel();
		
		inFileChannel.transferTo(0,inFileChannel.size(),outFileChannel);
		
		inFileChannel.close();
		outFileChannel.close();
		
		if(inFile.exists()) {
            inFile.delete();
		}
	}
	
	
	// ���� �����ϱ�
	public synchronized final void fileDeleteIfExist(String fileInDirectory, String fileInName) throws IOException {
		File inFile = new File(fileInDirectory, fileInName);
		
		if(inFile.exists()) {
			inFile.delete();
		}
	}	
	
}
