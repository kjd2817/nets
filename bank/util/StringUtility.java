package bank.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class StringUtility {

	// null �� �������� �ٲ۴�.
	public synchronized final static String convertNull(String str) {
		if(str == null) str = "";
		
		return str;
	}
	
	// ���ڿ��� ByteBuffer �� �ִ´�.
	public synchronized final static ByteBuffer getByteBuffer(String str) {
		String tmpStr = str;
		byte[] bytes = tmpStr.getBytes();
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);		
		bb.put(bytes,0,bytes.length);		
		return bb;
	}
	
	// �������� ���������� ���ڿ��� ��´�. �����ڸ��� '0'
	public synchronized final static String getFixedNumber(String number, int len) {
		String str = number;
		
		if(str == null) {
			str = "";
		} else if(str.length() > len) {
			str = str.substring(0, len);
		}
		
		StringBuffer sbString = new StringBuffer();
		
		for(int i=str.length(); i<len; i++) {
			sbString.append("0");
		}
		
		sbString.append(str);
		
		return sbString.toString();
	}

	public synchronized final static String getFixedOneStr(String one_str, int len) {
		String str = one_str;
		
		if(str == null) {
			str = "";
		} else if(str.length() > len) {
			str = str.substring(0, len);
		}
		
		StringBuffer sbString = new StringBuffer();
		
		for(int i=str.length(); i<len; i++) {
			sbString.append(one_str);
		}
		
		sbString.append(str);
		
		return sbString.toString();
	}
	
	public synchronized final static String getFixedNumber(int number, int len) {
		String str = String.valueOf(number);
		
		if(str == null) {
			str = "";
		} else if(str.length() > len) {
			str = str.substring(0, len);
		}
		
		StringBuffer sbString = new StringBuffer();
		
		for(int i=str.length(); i<len; i++) {
			sbString.append("0");
		}
		
		sbString.append(str);
		
		return sbString.toString();
	}
	
	// �������� byte �� �ڸ� ���ڿ� �迭�� ��´�.

	public synchronized final static String[] getFixedByteArray(String str, int byteLength) {
		
		List aList = new ArrayList();
		String tmpStr = str;
		if(tmpStr == null) {
			tmpStr = "";
		}
		if(tmpStr.getBytes().length < byteLength) {
			aList.add(getFixedByte(tmpStr, byteLength));
		} else {
			byte[] byteString = tmpStr.getBytes();
			int startPos = 0;
			int endPos = 0+byteLength;
			
			while(byteString.length>startPos) {
				String tmp = "";
				if(byteString.length >= (startPos+byteLength)) {
					tmp = new String(byteString,startPos,byteLength);
				} else {
					tmp = new String(byteString,startPos,byteString.length-startPos);
				}
				
				aList.add(getFixedByte(tmp,byteLength));
				startPos = startPos + byteLength;
				endPos = endPos + byteLength;
			}
		}
				
		String[] strArray = new String[aList.size()];
		aList.toArray(strArray);
		aList.clear();
		return strArray;
	}
	
	// �������� byte �� �ڸ� ���ڿ��� ��´�.
	public synchronized final static String getFullFixedByte(String str, int byteLength) {
		
		str = getFullString(str);
		str = getFixedByte(str, byteLength-2);
		str = str + "  ";
		return str;
	}
	
	// �������� byte �� �ڸ� ���ڿ��� ��´�.
	public synchronized final static String getFixedByte(String str, int byteLength) {
		StringBuffer sbString = new StringBuffer();
		String tmpStr = str;
		if(tmpStr == null) {
			tmpStr = "";
		}
		
		sbString.append(tmpStr);
		
		for(int i=tmpStr.getBytes().length; i<byteLength; i++) {
			sbString.append(" ");
		}
		tmpStr = sbString.toString();
		byte[] byteString = tmpStr.getBytes();
		
		return new String(byteString,0,byteLength);
	}

	// �������� byte �� �ڸ� ��ȣȭ�� ���ڿ��� ��´�.
	public synchronized final static String X_getEncryptedFixedByte(String str, int byteLength) throws 	InvalidKeyException, 
																										NoSuchAlgorithmException, 
																										NoSuchPaddingException, 
																										InvalidKeySpecException, 
																										UnsupportedEncodingException, 
																										IllegalBlockSizeException, 
																										BadPaddingException {
		StringBuffer sbString = new StringBuffer();
		String tmpStr = str;
		if(tmpStr == null) {
			tmpStr = "";
		}
		
		//sbString.append(EncryptionUtility.setEncrypt(tmpStr));
		
		for(int i=sbString.toString().getBytes().length; i<byteLength; i++) {
			sbString.append(" ");
		}
		tmpStr = sbString.toString();
		byte[] byteString = tmpStr.getBytes();
		
		return new String(byteString,0,byteLength);
	}
	
	// ������ġ�� ���� Ư�� ������  �������� ���ڿ��� ��´�.
	public synchronized final static String getFixedByte(String str, int startPos, int endPos) {
		String tmpStr = str;
		tmpStr = getFixedByte(tmpStr, endPos);
		return new String(tmpStr.getBytes(), startPos, endPos-startPos);
	}
	
	// ���ڸ� ���ڷ� ��ȯ�Ѵ�.
	public synchronized final static String getFullString(String strHalf) {
		
		if(strHalf == null) {
			return strHalf;
		}
				
		char[] charArray = strHalf.toCharArray();
		
		for(int i=0; i<charArray.length; i++) {			
			if(charArray[i] >= 0x21 && charArray[i] <= 0x7E) {
				// ���� ���� ������/���ں�ȣ�� ���, 0xFEE0 ��ŭ ���Ѵ�.
				charArray[i] += 0xFEE0;
			} else if(charArray[i] == 0x20) {
				// ���� ������ ���� �������� �ٲ۴�.
				charArray[i] = 0x3000;
			}
		}
		
		String strFull = new String(charArray);
		
		return strFull;
	}
	
	// ���ڸ� ���ڷ� ��ȯ�Ѵ�.
	public synchronized final static String getHalfString(String strFull) {
		
		if(strFull == null) {
			return strFull;
		}
						
		char[] charArray = strFull.toCharArray();
		
		for(int i=0; i<charArray.length; i++) {			
			if(charArray[i] >= 0xFF01 && charArray[i] <= 0xFF5E) {
				// ���� ���� ������/���ں�ȣ�� ���, 0xFEE0 ��ŭ ����.
				charArray[i] -= 0xFEE0;
			} else if(charArray[i] == 0x3000) {
				// ���� ������ ���� �������� �ٲ۴�.
				charArray[i] = 0x20;
			}
		}
		
		String strHalf = new String(charArray);
		return strHalf;
	}	
	
	// �ѱ��� �������� ��ȯ�Ѵ�.
	public synchronized final static String convertHangul_English(String str) {
		
		String tmpStr = str;
		if(tmpStr.indexOf("�����") >= 0) {
			tmpStr = tmpStr.replaceAll("�����","User");
		} else if(tmpStr.indexOf("���α׷�") >= 0) {
			tmpStr = tmpStr.replaceAll("���α׷�","Pgm");
		} else if(tmpStr.indexOf("�繫��ǥ") >= 0) {
			tmpStr = tmpStr.replaceAll("�繫��ǥ","BSPL");
		} else if(tmpStr.indexOf("�ΰ���ġ��") >= 0) {
			tmpStr = tmpStr.replaceAll("�ΰ���ġ��","VAT");
		} else if(tmpStr.indexOf("����") >= 0) {
			tmpStr = tmpStr.replaceAll("����","Crt");
		} else if(tmpStr.indexOf("�ڵ�") >= 0) {
			tmpStr = tmpStr.replaceAll("�ڵ�","Cd");
		} else if(tmpStr.indexOf("����") >= 0) {
			tmpStr = tmpStr.replaceAll("����","Doc");
		} else if(tmpStr.indexOf("����") >= 0) {
			tmpStr = tmpStr.replaceAll("����","Rpt");
		} else if(tmpStr.indexOf("��������") >= 0) {
			tmpStr = tmpStr.replaceAll("��������","Notice");
		} else if(tmpStr.indexOf("����") >= 0) {
			tmpStr = tmpStr.replaceAll("����","Notice");
		} else if(tmpStr.indexOf("ī��") >= 0) {
			tmpStr = tmpStr.replaceAll("ī��","Card");
		}
		
		tmpStr = tmpStr.replaceAll( "��", "Ga");    
		tmpStr = tmpStr.replaceAll( "��", "Gak");   
		tmpStr = tmpStr.replaceAll( "��", "Gan");   
		tmpStr = tmpStr.replaceAll( "��", "Gal");   
		tmpStr = tmpStr.replaceAll( "��", "Gam");   
		tmpStr = tmpStr.replaceAll( "��", "Gap");   
		tmpStr = tmpStr.replaceAll( "��", "Gat");   
		tmpStr = tmpStr.replaceAll( "��", "Gang");  
		tmpStr = tmpStr.replaceAll( "��", "Gae");   
		tmpStr = tmpStr.replaceAll( "��", "Gaek");  
		tmpStr = tmpStr.replaceAll( "��", "Geo");   
		tmpStr = tmpStr.replaceAll( "��", "Geon");  
		tmpStr = tmpStr.replaceAll( "��", "Geol");  
		tmpStr = tmpStr.replaceAll( "��", "Geom");  
		tmpStr = tmpStr.replaceAll( "��", "Geop");  
		tmpStr = tmpStr.replaceAll( "��", "Ge");    
		tmpStr = tmpStr.replaceAll( "��", "Gyeo");  
		tmpStr = tmpStr.replaceAll( "��", "Gyeok"); 
		tmpStr = tmpStr.replaceAll( "��", "Gyeon"); 
		tmpStr = tmpStr.replaceAll( "��", "Gyeol"); 
		tmpStr = tmpStr.replaceAll( "��", "Gyeom"); 
		tmpStr = tmpStr.replaceAll( "��", "Gyeop"); 
		tmpStr = tmpStr.replaceAll( "��", "Gyeong");
		tmpStr = tmpStr.replaceAll( "��", "Gye");   
		tmpStr = tmpStr.replaceAll( "��", "Go");    
		tmpStr = tmpStr.replaceAll( "��", "Gok");   
		tmpStr = tmpStr.replaceAll( "��", "Gon");   
		tmpStr = tmpStr.replaceAll( "��", "Gol");   
		tmpStr = tmpStr.replaceAll( "��", "Got");   
		tmpStr = tmpStr.replaceAll( "��", "Gong");  
		tmpStr = tmpStr.replaceAll( "��", "Got");   
		tmpStr = tmpStr.replaceAll( "��", "Gwa");   
		tmpStr = tmpStr.replaceAll( "��", "Gwak");  
		tmpStr = tmpStr.replaceAll( "��", "Gwan");  
		tmpStr = tmpStr.replaceAll( "��", "Gwal");  
		tmpStr = tmpStr.replaceAll( "��", "Gwang"); 
		tmpStr = tmpStr.replaceAll( "��", "Gwae");  
		tmpStr = tmpStr.replaceAll( "��", "Goe");   
		tmpStr = tmpStr.replaceAll( "��", "Goeng"); 
		tmpStr = tmpStr.replaceAll( "��", "Gyo");   
		tmpStr = tmpStr.replaceAll( "��", "Gu");    
		tmpStr = tmpStr.replaceAll( "��", "Guk");   
		tmpStr = tmpStr.replaceAll( "��", "Gun");   
		tmpStr = tmpStr.replaceAll( "��", "Gul");   
		tmpStr = tmpStr.replaceAll( "��", "Gut");   
		tmpStr = tmpStr.replaceAll( "��", "Gung");  
		tmpStr = tmpStr.replaceAll( "��", "Gwon");  
		tmpStr = tmpStr.replaceAll( "��", "Gwol");  
		tmpStr = tmpStr.replaceAll( "��", "Gwi");   
		tmpStr = tmpStr.replaceAll( "��", "Gyu");   
		tmpStr = tmpStr.replaceAll( "��", "Gyun");  
		tmpStr = tmpStr.replaceAll( "��", "Gyul");  
		tmpStr = tmpStr.replaceAll( "��", "Geu");   
		tmpStr = tmpStr.replaceAll( "��", "Geuk");  
		tmpStr = tmpStr.replaceAll( "��", "Geun");  
		tmpStr = tmpStr.replaceAll( "��", "Geul");  
		tmpStr = tmpStr.replaceAll( "��", "Geum");  
		tmpStr = tmpStr.replaceAll( "��", "Geup");  
		tmpStr = tmpStr.replaceAll( "��", "Geung"); 
		tmpStr = tmpStr.replaceAll( "��", "Gi");    
		tmpStr = tmpStr.replaceAll( "��", "Gin");   
		tmpStr = tmpStr.replaceAll( "��", "Gil");   
		tmpStr = tmpStr.replaceAll( "��", "Gim");   
		tmpStr = tmpStr.replaceAll( "��", "Kka");   
		tmpStr = tmpStr.replaceAll( "��", "Kkae");  
		tmpStr = tmpStr.replaceAll( "��", "Kko");   
		tmpStr = tmpStr.replaceAll( "��", "Kkok");  
		tmpStr = tmpStr.replaceAll( "��", "Kkot");  
		tmpStr = tmpStr.replaceAll( "��", "Kkoe");  
		tmpStr = tmpStr.replaceAll( "��", "Kku");   
		tmpStr = tmpStr.replaceAll( "��", "Kkum");  
		tmpStr = tmpStr.replaceAll( "��", "Kkeut"); 
		tmpStr = tmpStr.replaceAll( "��", "Kki");   
		tmpStr = tmpStr.replaceAll( "��", "Na");    
		tmpStr = tmpStr.replaceAll( "��", "Nak");   
		tmpStr = tmpStr.replaceAll( "��", "Nan");   
		tmpStr = tmpStr.replaceAll( "��", "Nal");   
		tmpStr = tmpStr.replaceAll( "��", "Nam");   
		tmpStr = tmpStr.replaceAll( "��", "Nap");   
		tmpStr = tmpStr.replaceAll( "��", "Nang");  
		tmpStr = tmpStr.replaceAll( "��", "Nae");   
		tmpStr = tmpStr.replaceAll( "��", "Naeng"); 
		tmpStr = tmpStr.replaceAll( "��", "Neo");   
		tmpStr = tmpStr.replaceAll( "��", "Neol");  
		tmpStr = tmpStr.replaceAll( "��", "Ne");    
		tmpStr = tmpStr.replaceAll( "��", "Nyeo");  
		tmpStr = tmpStr.replaceAll( "��", "Nyeok"); 
		tmpStr = tmpStr.replaceAll( "��", "Nyeon"); 
		tmpStr = tmpStr.replaceAll( "��", "Nyeom"); 
		tmpStr = tmpStr.replaceAll( "��", "Nyeong");
		tmpStr = tmpStr.replaceAll( "��", "No");    
		tmpStr = tmpStr.replaceAll( "��", "Nok");   
		tmpStr = tmpStr.replaceAll( "��", "Non");   
		tmpStr = tmpStr.replaceAll( "��", "Nol");   
		tmpStr = tmpStr.replaceAll( "��", "Nong");  
		tmpStr = tmpStr.replaceAll( "��", "Noe");   
		tmpStr = tmpStr.replaceAll( "��", "Nu");    
		tmpStr = tmpStr.replaceAll( "��", "Nun");   
		tmpStr = tmpStr.replaceAll( "��", "Nul");   
		tmpStr = tmpStr.replaceAll( "��", "Neu");   
		tmpStr = tmpStr.replaceAll( "��", "Neuk");  
		tmpStr = tmpStr.replaceAll( "��", "Neum");  
		tmpStr = tmpStr.replaceAll( "��", "Neung"); 
		tmpStr = tmpStr.replaceAll( "��", "Nui");   
		tmpStr = tmpStr.replaceAll( "��", "Ni");    
		tmpStr = tmpStr.replaceAll( "��", "Nik");   
		tmpStr = tmpStr.replaceAll( "��", "Nin");   
		tmpStr = tmpStr.replaceAll( "��", "Nil");   
		tmpStr = tmpStr.replaceAll( "��", "Nim");   
		tmpStr = tmpStr.replaceAll( "��", "Da");    
		tmpStr = tmpStr.replaceAll( "��", "Dan");   
		tmpStr = tmpStr.replaceAll( "��", "Dal");   
		tmpStr = tmpStr.replaceAll( "��", "Dam");   
		tmpStr = tmpStr.replaceAll( "��", "Dap");   
		tmpStr = tmpStr.replaceAll( "��", "Dang");  
		tmpStr = tmpStr.replaceAll( "��", "Dae");   
		tmpStr = tmpStr.replaceAll( "��", "Daek");  
		tmpStr = tmpStr.replaceAll( "��", "Deo");   
		tmpStr = tmpStr.replaceAll( "��", "Deok");  
		tmpStr = tmpStr.replaceAll( "��", "Do");    
		tmpStr = tmpStr.replaceAll( "��", "Dok");   
		tmpStr = tmpStr.replaceAll( "��", "Don");   
		tmpStr = tmpStr.replaceAll( "��", "Dol");   
		tmpStr = tmpStr.replaceAll( "��", "Dong");  
		tmpStr = tmpStr.replaceAll( "��", "Dwae");  
		tmpStr = tmpStr.replaceAll( "��", "Doe");   
		tmpStr = tmpStr.replaceAll( "��", "Doen");  
		tmpStr = tmpStr.replaceAll( "��", "Du");    
		tmpStr = tmpStr.replaceAll( "��", "Duk");   
		tmpStr = tmpStr.replaceAll( "��", "Dun");   
		tmpStr = tmpStr.replaceAll( "��", "Dwi");   
		tmpStr = tmpStr.replaceAll( "��", "Deu");   
		tmpStr = tmpStr.replaceAll( "��", "Deuk");  
		tmpStr = tmpStr.replaceAll( "��", "Deul");  
		tmpStr = tmpStr.replaceAll( "��", "Deung"); 
		tmpStr = tmpStr.replaceAll( "��", "Di");    
		tmpStr = tmpStr.replaceAll( "��", "Tta");   
		tmpStr = tmpStr.replaceAll( "��", "Ttang"); 
		tmpStr = tmpStr.replaceAll( "��", "Ttae");  
		tmpStr = tmpStr.replaceAll( "��", "Tto");   
		tmpStr = tmpStr.replaceAll( "��", "Ttu");   
		tmpStr = tmpStr.replaceAll( "��", "Ttuk");  
		tmpStr = tmpStr.replaceAll( "��", "Tteu");  
		tmpStr = tmpStr.replaceAll( "��", "Tti");   
		tmpStr = tmpStr.replaceAll( "��", "Ra");    
		tmpStr = tmpStr.replaceAll( "��", "Rak");   
		tmpStr = tmpStr.replaceAll( "��", "Ran");   
		tmpStr = tmpStr.replaceAll( "��", "Ram");   
		tmpStr = tmpStr.replaceAll( "��", "Rang");  
		tmpStr = tmpStr.replaceAll( "��", "Rae");   
		tmpStr = tmpStr.replaceAll( "��", "Raeng"); 
		tmpStr = tmpStr.replaceAll( "��", "Ryang"); 
		tmpStr = tmpStr.replaceAll( "��", "Reong"); 
		tmpStr = tmpStr.replaceAll( "��", "Re");    
		tmpStr = tmpStr.replaceAll( "��", "Ryeo");  
		tmpStr = tmpStr.replaceAll( "��", "Ryeok"); 
		tmpStr = tmpStr.replaceAll( "��", "Ryeon"); 
		tmpStr = tmpStr.replaceAll( "��", "Ryeol"); 
		tmpStr = tmpStr.replaceAll( "��", "Ryeom"); 
		tmpStr = tmpStr.replaceAll( "��", "Ryeop"); 
		tmpStr = tmpStr.replaceAll( "��", "Ryeong");
		tmpStr = tmpStr.replaceAll( "��", "Rye");   
		tmpStr = tmpStr.replaceAll( "��", "Ro");    
		tmpStr = tmpStr.replaceAll( "��", "Rok");   
		tmpStr = tmpStr.replaceAll( "��", "Ron");   
		tmpStr = tmpStr.replaceAll( "��", "Rong");  
		tmpStr = tmpStr.replaceAll( "��", "Roe");   
		tmpStr = tmpStr.replaceAll( "��", "Ryo");   
		tmpStr = tmpStr.replaceAll( "��", "Ryong"); 
		tmpStr = tmpStr.replaceAll( "��", "Ru");    
		tmpStr = tmpStr.replaceAll( "��", "Ryu");   
		tmpStr = tmpStr.replaceAll( "��", "Ryuk");  
		tmpStr = tmpStr.replaceAll( "��", "Ryun");  
		tmpStr = tmpStr.replaceAll( "��", "Ryul");  
		tmpStr = tmpStr.replaceAll( "��", "Ryung"); 
		tmpStr = tmpStr.replaceAll( "��", "Reu");   
		tmpStr = tmpStr.replaceAll( "��", "Reuk");  
		tmpStr = tmpStr.replaceAll( "��", "Reun");  
		tmpStr = tmpStr.replaceAll( "��", "Reum");  
		tmpStr = tmpStr.replaceAll( "��", "Reung"); 
		tmpStr = tmpStr.replaceAll( "��", "Ri");    
		tmpStr = tmpStr.replaceAll( "��", "Rin");   
		tmpStr = tmpStr.replaceAll( "��", "Rim");   
		tmpStr = tmpStr.replaceAll( "��", "Rip");   
		tmpStr = tmpStr.replaceAll( "��", "Ma");    
		tmpStr = tmpStr.replaceAll( "��", "Mak");   
		tmpStr = tmpStr.replaceAll( "��", "Man");   
		tmpStr = tmpStr.replaceAll( "��", "Mal");   
		tmpStr = tmpStr.replaceAll( "��", "Mang");  
		tmpStr = tmpStr.replaceAll( "��", "Mae");   
		tmpStr = tmpStr.replaceAll( "��", "Maek");  
		tmpStr = tmpStr.replaceAll( "��", "Maen");  
		tmpStr = tmpStr.replaceAll( "��", "Maeng"); 
		tmpStr = tmpStr.replaceAll( "��", "Meo");   
		tmpStr = tmpStr.replaceAll( "��", "Meok");  
		tmpStr = tmpStr.replaceAll( "��", "Me");    
		tmpStr = tmpStr.replaceAll( "��", "Myeo");  
		tmpStr = tmpStr.replaceAll( "��", "Myeok"); 
		tmpStr = tmpStr.replaceAll( "��", "Myeon"); 
		tmpStr = tmpStr.replaceAll( "��", "Myeol"); 
		tmpStr = tmpStr.replaceAll( "��", "Myeong");
		tmpStr = tmpStr.replaceAll( "��", "Mo");    
		tmpStr = tmpStr.replaceAll( "��", "Mok");   
		tmpStr = tmpStr.replaceAll( "��", "Mol");   
		tmpStr = tmpStr.replaceAll( "��", "Mot");   
		tmpStr = tmpStr.replaceAll( "��", "Mong");  
		tmpStr = tmpStr.replaceAll( "��", "Moe");   
		tmpStr = tmpStr.replaceAll( "��", "Myo");   
		tmpStr = tmpStr.replaceAll( "��", "Mu");    
		tmpStr = tmpStr.replaceAll( "��", "Muk");   
		tmpStr = tmpStr.replaceAll( "��", "Mun");   
		tmpStr = tmpStr.replaceAll( "��", "Mul");   
		tmpStr = tmpStr.replaceAll( "��", "Meu");   
		tmpStr = tmpStr.replaceAll( "��", "Mi");    
		tmpStr = tmpStr.replaceAll( "��", "Min");   
		tmpStr = tmpStr.replaceAll( "��", "Mil");   
		tmpStr = tmpStr.replaceAll( "��", "Ba");    
		tmpStr = tmpStr.replaceAll( "��", "Bak");   
		tmpStr = tmpStr.replaceAll( "��", "Ban");   
		tmpStr = tmpStr.replaceAll( "��", "Bal");   
		tmpStr = tmpStr.replaceAll( "��", "Bap");   
		tmpStr = tmpStr.replaceAll( "��", "Bang");  
		tmpStr = tmpStr.replaceAll( "��", "Bae");   
		tmpStr = tmpStr.replaceAll( "��", "Baek");  
		tmpStr = tmpStr.replaceAll( "��", "Baem");  
		tmpStr = tmpStr.replaceAll( "��", "Beo");   
		tmpStr = tmpStr.replaceAll( "��", "Beon");  
		tmpStr = tmpStr.replaceAll( "��", "Beol");  
		tmpStr = tmpStr.replaceAll( "��", "Beom");  
		tmpStr = tmpStr.replaceAll( "��", "Beop");  
		tmpStr = tmpStr.replaceAll( "��", "Byeo");  
		tmpStr = tmpStr.replaceAll( "��", "Byeok"); 
		tmpStr = tmpStr.replaceAll( "��", "Byeon"); 
		tmpStr = tmpStr.replaceAll( "��", "Byeol"); 
		tmpStr = tmpStr.replaceAll( "��", "Byeong");
		tmpStr = tmpStr.replaceAll( "��", "Bo");    
		tmpStr = tmpStr.replaceAll( "��", "Bok");   
		tmpStr = tmpStr.replaceAll( "��", "Bon");   
		tmpStr = tmpStr.replaceAll( "��", "Bong");  
		tmpStr = tmpStr.replaceAll( "��", "Bu");    
		tmpStr = tmpStr.replaceAll( "��", "Buk");   
		tmpStr = tmpStr.replaceAll( "��", "Bun");   
		tmpStr = tmpStr.replaceAll( "��", "Bul");   
		tmpStr = tmpStr.replaceAll( "��", "Bung");  
		tmpStr = tmpStr.replaceAll( "��", "Bi");    
		tmpStr = tmpStr.replaceAll( "��", "Bin");   
		tmpStr = tmpStr.replaceAll( "��", "Bil");   
		tmpStr = tmpStr.replaceAll( "��", "Bim");   
		tmpStr = tmpStr.replaceAll( "��", "Bing");  
		tmpStr = tmpStr.replaceAll( "��", "Ppa");   
		tmpStr = tmpStr.replaceAll( "��", "Ppae");  
		tmpStr = tmpStr.replaceAll( "��", "Ppeo");  
		tmpStr = tmpStr.replaceAll( "��", "Ppo");   
		tmpStr = tmpStr.replaceAll( "��", "Ppu");   
		tmpStr = tmpStr.replaceAll( "��", "Ppeu");  
		tmpStr = tmpStr.replaceAll( "��", "Ppi");   
		tmpStr = tmpStr.replaceAll( "��", "Sa");    
		tmpStr = tmpStr.replaceAll( "��", "Sak");   
		tmpStr = tmpStr.replaceAll( "��", "San");   
		tmpStr = tmpStr.replaceAll( "��", "Sal");   
		tmpStr = tmpStr.replaceAll( "��", "Sam");   
		tmpStr = tmpStr.replaceAll( "��", "Sap");   
		tmpStr = tmpStr.replaceAll( "��", "Sang");  
		tmpStr = tmpStr.replaceAll( "��", "Sat");   
		tmpStr = tmpStr.replaceAll( "��", "Sae");   
		tmpStr = tmpStr.replaceAll( "��", "Saek");  
		tmpStr = tmpStr.replaceAll( "��", "Saeng"); 
		tmpStr = tmpStr.replaceAll( "��", "Seo");   
		tmpStr = tmpStr.replaceAll( "��", "Seok");  
		tmpStr = tmpStr.replaceAll( "��", "Seon");  
		tmpStr = tmpStr.replaceAll( "��", "Seol");  
		tmpStr = tmpStr.replaceAll( "��", "Seom");  
		tmpStr = tmpStr.replaceAll( "��", "Seop");  
		tmpStr = tmpStr.replaceAll( "��", "Seong"); 
		tmpStr = tmpStr.replaceAll( "��", "Se");    
		tmpStr = tmpStr.replaceAll( "��", "Syeo");  
		tmpStr = tmpStr.replaceAll( "��", "So");    
		tmpStr = tmpStr.replaceAll( "��", "Sok");   
		tmpStr = tmpStr.replaceAll( "��", "Son");   
		tmpStr = tmpStr.replaceAll( "��", "Sol");   
		tmpStr = tmpStr.replaceAll( "��", "Sot");   
		tmpStr = tmpStr.replaceAll( "��", "Song");  
		tmpStr = tmpStr.replaceAll( "��", "Swae");  
		tmpStr = tmpStr.replaceAll( "��", "Soe");   
		tmpStr = tmpStr.replaceAll( "��", "Su");    
		tmpStr = tmpStr.replaceAll( "��", "Suk");   
		tmpStr = tmpStr.replaceAll( "��", "Sun");   
		tmpStr = tmpStr.replaceAll( "��", "Sul");   
		tmpStr = tmpStr.replaceAll( "��", "Sum");   
		tmpStr = tmpStr.replaceAll( "��", "Sung");  
		tmpStr = tmpStr.replaceAll( "��", "Swi");   
		tmpStr = tmpStr.replaceAll( "��", "Seu");   
		tmpStr = tmpStr.replaceAll( "��", "Seul");  
		tmpStr = tmpStr.replaceAll( "��", "Seum");  
		tmpStr = tmpStr.replaceAll( "��", "Seup");  
		tmpStr = tmpStr.replaceAll( "��", "Seung"); 
		tmpStr = tmpStr.replaceAll( "��", "Si");    
		tmpStr = tmpStr.replaceAll( "��", "Sik");   
		tmpStr = tmpStr.replaceAll( "��", "Sin");   
		tmpStr = tmpStr.replaceAll( "��", "Sil");   
		tmpStr = tmpStr.replaceAll( "��", "Sim");   
		tmpStr = tmpStr.replaceAll( "��", "Sip");   
		tmpStr = tmpStr.replaceAll( "��", "Sing");  
		tmpStr = tmpStr.replaceAll( "��", "Ssa");   
		tmpStr = tmpStr.replaceAll( "��", "Ssang"); 
		tmpStr = tmpStr.replaceAll( "��", "Ssae");  
		tmpStr = tmpStr.replaceAll( "��", "Sso");   
		tmpStr = tmpStr.replaceAll( "��", "Ssuk");  
		tmpStr = tmpStr.replaceAll( "��", "Ssi");   
		tmpStr = tmpStr.replaceAll( "��", "A");     
		tmpStr = tmpStr.replaceAll( "��", "Ak");    
		tmpStr = tmpStr.replaceAll( "��", "An");    
		tmpStr = tmpStr.replaceAll( "��", "Al");    
		tmpStr = tmpStr.replaceAll( "��", "Am");    
		tmpStr = tmpStr.replaceAll( "��", "Ap");    
		tmpStr = tmpStr.replaceAll( "��", "Ang");   
		tmpStr = tmpStr.replaceAll( "��", "Ap");    
		tmpStr = tmpStr.replaceAll( "��", "Ae");    
		tmpStr = tmpStr.replaceAll( "��", "Aek");   
		tmpStr = tmpStr.replaceAll( "��", "Aeng");  
		tmpStr = tmpStr.replaceAll( "��", "Ya");    
		tmpStr = tmpStr.replaceAll( "��", "Yak");   
		tmpStr = tmpStr.replaceAll( "��", "Yan");   
		tmpStr = tmpStr.replaceAll( "��", "Yang");  
		tmpStr = tmpStr.replaceAll( "��", "Eo");    
		tmpStr = tmpStr.replaceAll( "��", "Eok");   
		tmpStr = tmpStr.replaceAll( "��", "Eon");   
		tmpStr = tmpStr.replaceAll( "��", "Eol");   
		tmpStr = tmpStr.replaceAll( "��", "Eom");   
		tmpStr = tmpStr.replaceAll( "��", "Eop");   
		tmpStr = tmpStr.replaceAll( "��", "E");     
		tmpStr = tmpStr.replaceAll( "��", "Yeo");   
		tmpStr = tmpStr.replaceAll( "��", "Yeok");  
		tmpStr = tmpStr.replaceAll( "��", "Yeon");  
		tmpStr = tmpStr.replaceAll( "��", "Yeol");  
		tmpStr = tmpStr.replaceAll( "��", "Yeom");  
		tmpStr = tmpStr.replaceAll( "��", "Yeop");  
		tmpStr = tmpStr.replaceAll( "��", "Yeong"); 
		tmpStr = tmpStr.replaceAll( "��", "Ye");    
		tmpStr = tmpStr.replaceAll( "��", "O");     
		tmpStr = tmpStr.replaceAll( "��", "Ok");    
		tmpStr = tmpStr.replaceAll( "��", "On");    
		tmpStr = tmpStr.replaceAll( "��", "Ol");    
		tmpStr = tmpStr.replaceAll( "��", "Om");    
		tmpStr = tmpStr.replaceAll( "��", "Ong");   
		tmpStr = tmpStr.replaceAll( "��", "Wa");    
		tmpStr = tmpStr.replaceAll( "��", "Wan");   
		tmpStr = tmpStr.replaceAll( "��", "Wal");   
		tmpStr = tmpStr.replaceAll( "��", "Wang");  
		tmpStr = tmpStr.replaceAll( "��", "Wae");   
		tmpStr = tmpStr.replaceAll( "��", "Oe");    
		tmpStr = tmpStr.replaceAll( "��", "Oen");   
		tmpStr = tmpStr.replaceAll( "��", "Yo");    
		tmpStr = tmpStr.replaceAll( "��", "Yok");   
		tmpStr = tmpStr.replaceAll( "��", "Yong");  
		tmpStr = tmpStr.replaceAll( "��", "U");     
		tmpStr = tmpStr.replaceAll( "��", "Uk");    
		tmpStr = tmpStr.replaceAll( "��", "Un");    
		tmpStr = tmpStr.replaceAll( "��", "Ul");    
		tmpStr = tmpStr.replaceAll( "��", "Um");    
		tmpStr = tmpStr.replaceAll( "��", "Ung");   
		tmpStr = tmpStr.replaceAll( "��", "Wo");    
		tmpStr = tmpStr.replaceAll( "��", "Won");   
		tmpStr = tmpStr.replaceAll( "��", "Wol");   
		tmpStr = tmpStr.replaceAll( "��", "Wi");    
		tmpStr = tmpStr.replaceAll( "��", "Yu");    
		tmpStr = tmpStr.replaceAll( "��", "Yuk");   
		tmpStr = tmpStr.replaceAll( "��", "Yun");   
		tmpStr = tmpStr.replaceAll( "��", "Yul");   
		tmpStr = tmpStr.replaceAll( "��", "Yung");  
		tmpStr = tmpStr.replaceAll( "��", "Yut");   
		tmpStr = tmpStr.replaceAll( "��", "Eu");    
		tmpStr = tmpStr.replaceAll( "��", "Eun");   
		tmpStr = tmpStr.replaceAll( "��", "Eul");   
		tmpStr = tmpStr.replaceAll( "��", "Eum");   
		tmpStr = tmpStr.replaceAll( "��", "Eup");   
		tmpStr = tmpStr.replaceAll( "��", "Eung");  
		tmpStr = tmpStr.replaceAll( "��", "Ui");    
		tmpStr = tmpStr.replaceAll( "��", "I");     
		tmpStr = tmpStr.replaceAll( "��", "Ik");    
		tmpStr = tmpStr.replaceAll( "��", "In");    
		tmpStr = tmpStr.replaceAll( "��", "Il");    
		tmpStr = tmpStr.replaceAll( "��", "Im");    
		tmpStr = tmpStr.replaceAll( "��", "Ip");    
		tmpStr = tmpStr.replaceAll( "��", "Ing");   
		tmpStr = tmpStr.replaceAll( "��", "Ja");    
		tmpStr = tmpStr.replaceAll( "��", "Jak");   
		tmpStr = tmpStr.replaceAll( "��", "Jan");   
		tmpStr = tmpStr.replaceAll( "��", "Jam");   
		tmpStr = tmpStr.replaceAll( "��", "Jap");   
		tmpStr = tmpStr.replaceAll( "��", "Jang");  
		tmpStr = tmpStr.replaceAll( "��", "Jae");   
		tmpStr = tmpStr.replaceAll( "��", "Jaeng"); 
		tmpStr = tmpStr.replaceAll( "��", "Jeo");   
		tmpStr = tmpStr.replaceAll( "��", "Jeok");  
		tmpStr = tmpStr.replaceAll( "��", "Jeon");  
		tmpStr = tmpStr.replaceAll( "��", "Jeol");  
		tmpStr = tmpStr.replaceAll( "��", "Jeom");  
		tmpStr = tmpStr.replaceAll( "��", "Jeop");  
		tmpStr = tmpStr.replaceAll( "��", "Jeong"); 
		tmpStr = tmpStr.replaceAll( "��", "Je");    
		tmpStr = tmpStr.replaceAll( "��", "Jo");    
		tmpStr = tmpStr.replaceAll( "��", "Jok");   
		tmpStr = tmpStr.replaceAll( "��", "Jon");   
		tmpStr = tmpStr.replaceAll( "��", "Jol");   
		tmpStr = tmpStr.replaceAll( "��", "Jong");  
		tmpStr = tmpStr.replaceAll( "��", "Jwa");   
		tmpStr = tmpStr.replaceAll( "��", "Joe");   
		tmpStr = tmpStr.replaceAll( "��", "Ju");    
		tmpStr = tmpStr.replaceAll( "��", "Juk");   
		tmpStr = tmpStr.replaceAll( "��", "Jun");   
		tmpStr = tmpStr.replaceAll( "��", "Jul");   
		tmpStr = tmpStr.replaceAll( "��", "Jung");  
		tmpStr = tmpStr.replaceAll( "��", "Jwi");   
		tmpStr = tmpStr.replaceAll( "��", "Jeu");   
		tmpStr = tmpStr.replaceAll( "��", "Jeuk");  
		tmpStr = tmpStr.replaceAll( "��", "Jeul");  
		tmpStr = tmpStr.replaceAll( "��", "Jeum");  
		tmpStr = tmpStr.replaceAll( "��", "Jeup");  
		tmpStr = tmpStr.replaceAll( "��", "Jeung"); 
		tmpStr = tmpStr.replaceAll( "��", "Ji");    
		tmpStr = tmpStr.replaceAll( "��", "Jik");   
		tmpStr = tmpStr.replaceAll( "��", "Jin");   
		tmpStr = tmpStr.replaceAll( "��", "Jil");   
		tmpStr = tmpStr.replaceAll( "��", "Jim");   
		tmpStr = tmpStr.replaceAll( "��", "Jip");   
		tmpStr = tmpStr.replaceAll( "¡", "Jing");  
		tmpStr = tmpStr.replaceAll( "¥", "Jja");   
		tmpStr = tmpStr.replaceAll( "°", "Jjae");  
		tmpStr = tmpStr.replaceAll( "��", "Jjo");   
		tmpStr = tmpStr.replaceAll( "��", "Jji");   
		tmpStr = tmpStr.replaceAll( "��", "Cha");   
		tmpStr = tmpStr.replaceAll( "��", "Chak");  
		tmpStr = tmpStr.replaceAll( "��", "Chan");  
		tmpStr = tmpStr.replaceAll( "��", "Chal");  
		tmpStr = tmpStr.replaceAll( "��", "Cham");  
		tmpStr = tmpStr.replaceAll( "â", "Chang"); 
		tmpStr = tmpStr.replaceAll( "ä", "Chae");  
		tmpStr = tmpStr.replaceAll( "å", "Chaek"); 
		tmpStr = tmpStr.replaceAll( "ó", "Cheo");  
		tmpStr = tmpStr.replaceAll( "ô", "Cheok"); 
		tmpStr = tmpStr.replaceAll( "õ", "Cheon"); 
		tmpStr = tmpStr.replaceAll( "ö", "Cheol"); 
		tmpStr = tmpStr.replaceAll( "÷", "Cheom"); 
		tmpStr = tmpStr.replaceAll( "ø", "Cheop"); 
		tmpStr = tmpStr.replaceAll( "û", "Cheong");
		tmpStr = tmpStr.replaceAll( "ü", "Che");   
		tmpStr = tmpStr.replaceAll( "��", "Cho");   
		tmpStr = tmpStr.replaceAll( "��", "Chok");  
		tmpStr = tmpStr.replaceAll( "��", "Chon");  
		tmpStr = tmpStr.replaceAll( "��", "Chong"); 
		tmpStr = tmpStr.replaceAll( "��", "Choe");  
		tmpStr = tmpStr.replaceAll( "��", "Chu");
		tmpStr = tmpStr.replaceAll( "��", "Chwi");
		tmpStr = tmpStr.replaceAll( "��", "Chuk");  
		tmpStr = tmpStr.replaceAll( "��", "Chun");  
		tmpStr = tmpStr.replaceAll( "��", "Chul");  
		tmpStr = tmpStr.replaceAll( "��", "Chum");  
		tmpStr = tmpStr.replaceAll( "��", "Chung"); 
		tmpStr = tmpStr.replaceAll( "��", "Cheuk"); 
		tmpStr = tmpStr.replaceAll( "��", "Cheung");
		tmpStr = tmpStr.replaceAll( "ġ", "Chi");   
		tmpStr = tmpStr.replaceAll( "Ģ", "Chik");  
		tmpStr = tmpStr.replaceAll( "ģ", "Chin");  
		tmpStr = tmpStr.replaceAll( "ĥ", "Chil");  
		tmpStr = tmpStr.replaceAll( "ħ", "Chim");  
		tmpStr = tmpStr.replaceAll( "Ĩ", "Chip");  
		tmpStr = tmpStr.replaceAll( "Ī", "Ching"); 
		tmpStr = tmpStr.replaceAll( "ī", "Ka");   
		tmpStr = tmpStr.replaceAll( "��", "Ko");    
		tmpStr = tmpStr.replaceAll( "��", "Kwae");  
		tmpStr = tmpStr.replaceAll( "ũ", "Keu");   
		tmpStr = tmpStr.replaceAll( "ū", "Keun");  
		tmpStr = tmpStr.replaceAll( "Ű", "Ki");    
		tmpStr = tmpStr.replaceAll( "Ÿ", "Ta");    
		tmpStr = tmpStr.replaceAll( "Ź", "Tak");   
		tmpStr = tmpStr.replaceAll( "ź", "Tan");   
		tmpStr = tmpStr.replaceAll( "Ż", "Tal");   
		tmpStr = tmpStr.replaceAll( "Ž", "Tam");   
		tmpStr = tmpStr.replaceAll( "ž", "Tap");   
		tmpStr = tmpStr.replaceAll( "��", "Tang");  
		tmpStr = tmpStr.replaceAll( "��", "Tae");   
		tmpStr = tmpStr.replaceAll( "��", "Taek");  
		tmpStr = tmpStr.replaceAll( "��", "Taem");
		tmpStr = tmpStr.replaceAll( "��", "Taeng"); 
		tmpStr = tmpStr.replaceAll( "��", "Teo");   
		tmpStr = tmpStr.replaceAll( "��", "Te");   
		tmpStr = tmpStr.replaceAll( "��", "Tem");
		tmpStr = tmpStr.replaceAll( "��", "To");    
		tmpStr = tmpStr.replaceAll( "��", "Ton");   
		tmpStr = tmpStr.replaceAll( "��", "Tol");   
		tmpStr = tmpStr.replaceAll( "��", "Tong");  
		tmpStr = tmpStr.replaceAll( "��", "Toe");   
		tmpStr = tmpStr.replaceAll( "��", "Tu");    
		tmpStr = tmpStr.replaceAll( "��", "Tung");  
		tmpStr = tmpStr.replaceAll( "Ƣ", "Twi");   
		tmpStr = tmpStr.replaceAll( "Ʈ", "Teu");   
		tmpStr = tmpStr.replaceAll( "Ư", "Teuk");  
		tmpStr = tmpStr.replaceAll( "ƴ", "Teum");  
		tmpStr = tmpStr.replaceAll( "Ƽ", "Ti");    
		tmpStr = tmpStr.replaceAll( "��", "Pa");    
		tmpStr = tmpStr.replaceAll( "��", "Pan");   
		tmpStr = tmpStr.replaceAll( "��", "Pal");   
		tmpStr = tmpStr.replaceAll( "��", "Pae");   
		tmpStr = tmpStr.replaceAll( "��", "Paeng"); 
		tmpStr = tmpStr.replaceAll( "��", "Peo");   
		tmpStr = tmpStr.replaceAll( "��", "Pe");    
		tmpStr = tmpStr.replaceAll( "��", "Pyeo");  
		tmpStr = tmpStr.replaceAll( "��", "Pyeon"); 
		tmpStr = tmpStr.replaceAll( "��", "Pyeom"); 
		tmpStr = tmpStr.replaceAll( "��", "Pyeong");
		tmpStr = tmpStr.replaceAll( "��", "Pye");   
		tmpStr = tmpStr.replaceAll( "��", "Po");    
		tmpStr = tmpStr.replaceAll( "��", "Pok");   
		tmpStr = tmpStr.replaceAll( "ǥ", "Pyo");   
		tmpStr = tmpStr.replaceAll( "Ǫ", "Pu");    
		tmpStr = tmpStr.replaceAll( "ǰ", "Pum");   
		tmpStr = tmpStr.replaceAll( "ǳ", "Pung");  
		tmpStr = tmpStr.replaceAll( "��", "Peu");   
		tmpStr = tmpStr.replaceAll( "��", "Pi");    
		tmpStr = tmpStr.replaceAll( "��", "Pik");   
		tmpStr = tmpStr.replaceAll( "��", "Pil");   
		tmpStr = tmpStr.replaceAll( "��", "Pip");   
		tmpStr = tmpStr.replaceAll( "��", "Ha");    
		tmpStr = tmpStr.replaceAll( "��", "Hak");   
		tmpStr = tmpStr.replaceAll( "��", "Han");   
		tmpStr = tmpStr.replaceAll( "��", "Hal");   
		tmpStr = tmpStr.replaceAll( "��", "Ham");   
		tmpStr = tmpStr.replaceAll( "��", "Hap");   
		tmpStr = tmpStr.replaceAll( "��", "Hang");  
		tmpStr = tmpStr.replaceAll( "��", "Hae");   
		tmpStr = tmpStr.replaceAll( "��", "Haek");  
		tmpStr = tmpStr.replaceAll( "��", "Haeng"); 
		tmpStr = tmpStr.replaceAll( "��", "Hyang"); 
		tmpStr = tmpStr.replaceAll( "��", "Heo");   
		tmpStr = tmpStr.replaceAll( "��", "Heon");  
		tmpStr = tmpStr.replaceAll( "��", "Heom");  
		tmpStr = tmpStr.replaceAll( "��", "He");    
		tmpStr = tmpStr.replaceAll( "��", "Hyeo");  
		tmpStr = tmpStr.replaceAll( "��", "Hyeok"); 
		tmpStr = tmpStr.replaceAll( "��", "Hyeon"); 
		tmpStr = tmpStr.replaceAll( "��", "Hyeol"); 
		tmpStr = tmpStr.replaceAll( "��", "Hyeom"); 
		tmpStr = tmpStr.replaceAll( "��", "Hyeop"); 
		tmpStr = tmpStr.replaceAll( "��", "Hyeong");
		tmpStr = tmpStr.replaceAll( "��", "Hye");   
		tmpStr = tmpStr.replaceAll( "ȣ", "Ho");    
		tmpStr = tmpStr.replaceAll( "Ȥ", "Hok");   
		tmpStr = tmpStr.replaceAll( "ȥ", "Hon");   
		tmpStr = tmpStr.replaceAll( "Ȧ", "Hol");   
		tmpStr = tmpStr.replaceAll( "ȩ", "Hop");   
		tmpStr = tmpStr.replaceAll( "ȫ", "Hong");  
		tmpStr = tmpStr.replaceAll( "ȭ", "Hwa");   
		tmpStr = tmpStr.replaceAll( "Ȯ", "Hwak");  
		tmpStr = tmpStr.replaceAll( "ȯ", "Hwan");  
		tmpStr = tmpStr.replaceAll( "Ȱ", "Hwal");  
		tmpStr = tmpStr.replaceAll( "Ȳ", "Hwang"); 
		tmpStr = tmpStr.replaceAll( "ȳ", "Hwae");  
		tmpStr = tmpStr.replaceAll( "ȶ", "Hwaet"); 
		tmpStr = tmpStr.replaceAll( "ȸ", "Hoe");   
		tmpStr = tmpStr.replaceAll( "ȹ", "Hoek");  
		tmpStr = tmpStr.replaceAll( "Ⱦ", "Hoeng"); 
		tmpStr = tmpStr.replaceAll( "ȿ", "Hyo");   
		tmpStr = tmpStr.replaceAll( "��", "Hu");    
		tmpStr = tmpStr.replaceAll( "��", "Hun");   
		tmpStr = tmpStr.replaceAll( "��", "Hwon");  
		tmpStr = tmpStr.replaceAll( "��", "Hwe");   
		tmpStr = tmpStr.replaceAll( "��", "Hwi");   
		tmpStr = tmpStr.replaceAll( "��", "Hyu");   
		tmpStr = tmpStr.replaceAll( "��", "Hyul");  
		tmpStr = tmpStr.replaceAll( "��", "Hyung"); 
		tmpStr = tmpStr.replaceAll( "��", "Heu");   
		tmpStr = tmpStr.replaceAll( "��", "Heuk");  
		tmpStr = tmpStr.replaceAll( "��", "Heun");  
		tmpStr = tmpStr.replaceAll( "��", "Heul");  
		tmpStr = tmpStr.replaceAll( "��", "Heum");  
		tmpStr = tmpStr.replaceAll( "��", "Heup");  
		tmpStr = tmpStr.replaceAll( "��", "Heung"); 
		tmpStr = tmpStr.replaceAll( "��", "Hui");   
		tmpStr = tmpStr.replaceAll( "��", "Huin");  
		tmpStr = tmpStr.replaceAll( "��", "Hi");    
		tmpStr = tmpStr.replaceAll( "��", "Him");   
		tmpStr = tmpStr.replaceAll( "  ", " ");
		tmpStr = tmpStr.replaceAll( " ", "_");
		
		
		return tmpStr;	
	}		
}
