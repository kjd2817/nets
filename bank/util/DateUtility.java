package bank.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {
	// ���Ͻú��� ����Ÿ�� ��´�.
	public synchronized final static String getMMddHHmmss() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss", java.util.Locale.KOREA);
		return sdf.format(new Date());
	}
	
	// ����� ����Ÿ�� ��´�.
	public synchronized final static String getyyyyMMdd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
		return sdf.format(new Date());
	}	
	
	// ����Ͻú��� ����Ÿ�� ��´�.
	public synchronized final static String getyyyyMMddHHmmss() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.KOREA);
		return sdf.format(new Date());
	}		
	
	public void test() {
		
	}
}
