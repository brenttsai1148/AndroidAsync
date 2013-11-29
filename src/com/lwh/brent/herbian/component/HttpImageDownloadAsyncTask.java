package com.lwh.brent.herbian.component;


import android.graphics.Bitmap;
import android.os.AsyncTask;

public class HttpImageDownloadAsyncTask  extends AsyncTask<String, Integer, Long> {
	
	static int Value_timeoutConnection = 2000;
	static int Value_timeoutSocket = 2000;
	
//	ProgressDialog _ProgressDialog;
//	Activity mActivity;
//	String str_result;
	String str_url;
	Bitmap bm_bookCover;
	
	
	Inteface_httpResponse inteface_httpResponse;
	
	public interface Inteface_httpResponse {
        void callback(Bitmap _bitmap);
    }
	
	public void registerCallback(Inteface_httpResponse _inteface_httpResponse) {
		inteface_httpResponse = _inteface_httpResponse;
	}
	
	
	
	public HttpImageDownloadAsyncTask(String _url){
		
//		mActivity = _mActivity;
		str_url = _url;
		
	}
	
	public void start(Inteface_httpResponse _inteface_httpResponse)
	{
		
		inteface_httpResponse = _inteface_httpResponse;
		this.execute(str_url);
		
	}
	

	
	
	@Override
    protected void onPreExecute() {
//		_ProgressDialog = ProgressDialog.show(mActivity, "LOADING...", "Åª¨ú¤¤", true);
    }
	
	@Override
	protected Long doInBackground(String... arg0) {
		
		bm_bookCover = CommonFunction.getBitmapFromUrl(arg0[0]);
		return null;
	}
	@Override
	 protected void onProgressUpdate(Integer... progress) {
     }
	@Override
     protected void onPostExecute(Long result) {
		
//		_ProgressDialog.dismiss();
		inteface_httpResponse.callback(bm_bookCover);
     }
}
