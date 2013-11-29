package com.javacodegeeks.android.androidasynctasktutorial;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwh.brent.herbian.component.HttpAsyncTask;
import com.lwh.brent.herbian.component.HttpImageDownloadAsyncTask;

public class MainActivity extends Activity {
	final Context context = this;

	/** Called when the activity is first created. */
	private String url = "http://herbian.ait.net.tw/mobileapi/mGetTableOrders.aspx?tabno=1";
	String[] arr;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		arr = new String[2];
		arr[0] = "https://fbcdn-sphotos-d-a.akamaihd.net/hphotos-ak-prn2/1452357_548423345244490_441723598_n.jpg";
		arr[1] = "https://fbcdn-sphotos-f-a.akamaihd.net/hphotos-ak-prn2/q71/s720x720/1375079_548133411940150_639522538_n.jpg";

	}

	
	static int index = 0;
	public void loadImg(View view){
		index += 1;
		
		HttpImageDownloadAsyncTask httpImageDownloadAsyncTask = new HttpImageDownloadAsyncTask(arr[index % 2]);
		httpImageDownloadAsyncTask.start(new HttpImageDownloadAsyncTask.Inteface_httpResponse(){
		
			@Override
			public void callback(Bitmap _bitmap) {
				if(_bitmap != null)
				{
					ImageView img = (ImageView)MainActivity.this.findViewById(R.id.main_img);
					img.setImageBitmap(_bitmap);
				}
			}
		});
		
	}
	
	
	
	public void loadJson(View view) {
		HttpAsyncTask httpAsyncTask = new HttpAsyncTask(this,url);
		
		httpAsyncTask.registerCallback(new HttpAsyncTask.Inteface_httpResponse() {
			
			@Override
			public void callback(String _str_response) {
				
				
				JSONObject jsonRoot;
				try {
					jsonRoot = new JSONObject(_str_response);
					
					Log.d("test", "cqty = "+jsonRoot.getInt("cqty"));
					Log.d("test", "dgno = "+jsonRoot.getString("dgno"));
					Log.d("test", "salno = "+jsonRoot.getString("salno"));
					
					//set ui
					TextView tv_cqty = (TextView)MainActivity.this.findViewById(R.id.main_tv_cqty);
					tv_cqty.setText(jsonRoot.getInt("cqty")+"");//int 要轉string
					TextView tv_dgno = (TextView)MainActivity.this.findViewById(R.id.main_tv_dgno);
					tv_dgno.setText(jsonRoot.getString("dgno"));
					TextView tv_salno = (TextView)MainActivity.this.findViewById(R.id.main_tv_salno);
					tv_salno.setText(jsonRoot.getString("salno") );
					
					
					if(jsonRoot.getString("response").equals("0"))
					{
		    			JSONArray jsonArr = jsonRoot.getJSONArray("ordersList");//解析餐點資料
		    			for(int i = 0;i<jsonArr.length();i++)
		    			{
		    				
		    				JSONObject json_order = jsonArr.getJSONObject(i);
		    				JSONArray jsonArr_rmno = json_order.getJSONArray("arr_rmno");
		    				ArrayList<Integer> _list_i_rmno = new ArrayList<Integer>();
		    				for(int index = 0 ; index < jsonArr_rmno.length() ; index++)
		    				{
		    					_list_i_rmno.add(jsonArr_rmno.getInt(index));
		    				}
		    			}
					}else{
						//未使用
					}
				
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		httpAsyncTask.start();
	}
}