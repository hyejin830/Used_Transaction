package com.with_tnt.db_connection;


/**
 * Created by ANGELZIN on 2017-04-14.
 */

public class DB_Setting_Value
{
	public static class Setting_Value
	{
		public static final String DBCLASS = "net.sourceforge.jtds.jdbc.Driver";
		public static final String DBID = "DealIt";
		public static final String DBPW = "rla123";
		public final static String AUTH_KEY_FCM = "AAAA3GXDtvU:APA91bHnkLtzZTOZWPzmbnzn3UG2G2evjY4g5UxajwPxMMgQBH9IetVvuMeCTqz2moh-er4X2HRP7yiCW87tMSum5-yURMp5agi_LB-uUI40RMUznyAiD2SsDCaOHz30nzF2ameReKJ9";
		public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
		// public static final String PREDATAKEY = "UserID";
		// public static final String PREDATANAME = "UserInfo";
		public static final String SERVER_DB_IP = "jdbc:jtds:sqlserver://117.16.244.136:1678/DealIt";
		public static final String IMAGE_SERVER_IP = "14.34.46.152";
		public static final String IMAGE_LOCATION = "http://" + IMAGE_SERVER_IP + "/temp/DealIt/";
		public static final int SERVER_PORT = 7777;
		// public static final int TOAST_TIME = 1000;
	}
}