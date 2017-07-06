package com.redcdn.monitor.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostRequest {
  public static String go(String urls, String postData) {
    try {
      byte[] bt = postData.getBytes("UTF-8");
      URL url = new URL(urls);
      HttpURLConnection connection = (HttpURLConnection) url
          .openConnection();
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      connection.setDoInput(true);
      OutputStream out = (OutputStream) connection.getOutputStream();
      out.write(bt);
      out.close();
      InputStream content = (InputStream) connection.getInputStream();
      BufferedReader in = new BufferedReader(new InputStreamReader(
          content,"UTF-8"));
      StringBuffer sb=new StringBuffer();
      String line;
      while ((line = in.readLine()) != null) {
        sb.append(line);
      }
      in.close();
      return sb.toString();
    } catch (Exception e) {
    	
      e.printStackTrace();
    }
    return null;
  }
  
  public static void main(String[] args) {
	  String url="http://localhost:8080/monitorServer/getPath/getPath";
	  String data="sid=53739662_589072861_77775933_77776425";
	  System.out.println(go(url,data));
}
 
}
