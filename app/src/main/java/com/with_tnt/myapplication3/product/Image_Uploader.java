package com.with_tnt.myapplication3.product;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.with_tnt.db_connection.DB_Setting_Value;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ilwoo on 2017-04-01.
 */

public class Image_Uploader extends AsyncTask<String, String, String>
{
	String File_Path;
	String ID;
	String sendBack;
	Context exCon;

	public Image_Uploader(Context exCon, String File_Path, String ID)
	{
		this.ID = ID;
		this.File_Path = File_Path;
		this.exCon = exCon;
	}

	@Override
	protected String doInBackground(String... arg0)
	{
		// TODO Auto-generated method stub
		String serverIp = DB_Setting_Value.Setting_Value.IMAGE_SERVER_IP;
		Socket socket = null;
		DataOutputStream dos;
		FileInputStream fis;
		BufferedInputStream bis;
		try
		{
			// 서버 연결
			String userName = ID;
			socket = new Socket(serverIp, DB_Setting_Value.Setting_Value.SERVER_PORT);
			dos = new DataOutputStream(socket.getOutputStream());
			sendBack = userName + "_" + new SimpleDateFormat("yy-MM-dd_ss").format(new Date()) + ".jpg";
			dos.writeUTF(sendBack);
			File imageFile = new File(File_Path);
			fis = new FileInputStream(imageFile);
			bis = new BufferedInputStream(fis);
			int len;
			int size = 4096;
			byte[] data = new byte[size];
			while ((len = bis.read(data)) != -1)
			{
				dos.write(data, 0, len);
			}
			dos.flush();
			dos.close();
			bis.close();
			fis.close();
		}
		catch (Exception e)
		{
			Log.i("test", e.toString());
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result)
	{
		// TODO Auto-generated method stub
		((UploadProductActivity) exCon).imageBtn.setText("제출 완료");
		((UploadProductActivity) exCon).toasting(sendBack);
	}

}