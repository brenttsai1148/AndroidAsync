package com.lwh.brent.herbian.component;


import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class HttpAsyncTask  extends AsyncTask<String, Integer, Long> {
	
	static int Value_timeoutConnection = 6000;
	static int Value_timeoutSocket = 6000;
	
	ProgressDialog _ProgressDialog;
	Activity mActivity;
	String str_result;
	String str_url;
	
	
	
	public Inteface_httpResponse inteface_httpResponse;
	
	public interface Inteface_httpResponse {
        void callback(String _str_response);
    }
	
	public void registerCallback(Inteface_httpResponse _inteface_httpResponse) {
		inteface_httpResponse = _inteface_httpResponse;
	}
	
	
	
	public HttpAsyncTask(Activity _mActivity,String _url){
		
		mActivity = _mActivity;
		str_url = _url;
		
	}
	
	public void start()
	{
		
		this.execute(str_url);
		
	}
	

	
	
	@Override
    protected void onPreExecute() {
		_ProgressDialog = ProgressDialog.show(mActivity, "LOADING...", "Åª¨ú¤¤", true);
    }
	
	@Override
	protected Long doInBackground(String... arg0) {
		
		str_result = httpPostRequest(arg0[0]);
		
		
		return null;
	}
	@Override
	 protected void onProgressUpdate(Integer... progress) {
     }
	@Override
     protected void onPostExecute(Long result) {
		
		_ProgressDialog.dismiss();
		if(inteface_httpResponse != null)
			inteface_httpResponse.callback(str_result);
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
}
