package util;

import java.util.UUID;

public class UUID_generator {
	
	public static String get_UUID_with_Line()
	{
		UUID uuid  =UUID.randomUUID();
		String str = uuid.toString();
		return str;
	}
	
	public static String get_UUID_no_Line()
	{
		UUID uuid  =UUID.randomUUID();
		String str = uuid.toString();
		String temp = str.substring(0,8)+str.substring(9,13)+str.substring(14,18)
		                 +str.substring(19,23)+str.substring(24);
		return temp;
	}

}
