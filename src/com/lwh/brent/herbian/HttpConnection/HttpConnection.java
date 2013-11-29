package com.lwh.brent.herbian.HttpConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class HttpConnection {
	
	public static String baseDomain = "http://herbian.ait.net.tw/mobileapi/";
	public static String url_sendOrders= "http://herbian.ait.net.tw/mobileapi/mSendOrders.aspx";
	
	public static int Value_timeoutConnection = 10000;
	public static int Value_timeoutSocket = 10000;
	

	
	//¨ú±o®àªp
	public static String str_url_getTableOrders(String tabno){
		String urlapi = baseDomain + "mGetTableOrders.aspx?tabno=" +tabno;
		return urlapi;
	}
	

	
	private static String httpPostRequest(String _url){
		String Result = "";
		HttpPost httpPost = new HttpPost(_url);
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = Value_timeoutConnection;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = Value_timeoutSocket;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		try {
			HttpResponse httpr = new DefaultHttpClient(httpParameters).execute(httpPost); 
			Result = EntityUtils.toString(httpr.getEntity());

		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "fail";
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "fail";
		}
		return Result;
	}
	
	
	
	
	public static  String http_sendJson(final String str_url , final String str_value) {
		
		
		HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        HttpResponse response;
        
        try {
            HttpPost post = new HttpPost(str_url);
            StringEntity se = new StringEntity( str_value );  
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(se);
            response = client.execute(post);

            /*Checking response */
            if(response!=null){
                InputStream in = response.getEntity().getContent(); //Get the data in the entity
                return convertStreamToString(in);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static String convertStreamToString(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;

	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }

	    is.close();

	    return sb.toString();
	}
}
