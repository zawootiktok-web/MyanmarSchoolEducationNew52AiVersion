package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;


import java.net.*;
import java.io.*;
//import java.net.*;(.)net.*;
//import java(.)io.*;

public class EbookJSONDownloader
{
	public static String download(String url){
		StringBuffer result=new StringBuffer();
		try
		{
			HttpURLConnection httpConn = ((HttpURLConnection)new URL(url).openConnection());
			BufferedReader reader = new BufferedReader(
				new InputStreamReader(httpConn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null)
				result.append(line).append("\n");

			reader.close();
			httpConn.disconnect();

		}
		catch (IOException e)
		{
			e.printStackTrace();
			return "";
		}

		return result.toString();
	}
}
