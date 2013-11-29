package com.lwh.brent.herbian.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class CommonFunction {
	
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	

	
	//存放圖片
	public static void saveBitmapToSD(Bitmap BitmapPhoto,String bitmapName){
		try {
				File folder = new File("/sdcard/HerBian/");
				if(!folder.exists()){
					folder.mkdirs();
				}
			   File myFile = new File("/sdcard//HerBian/"+ bitmapName +".png");
			   FileOutputStream out = new FileOutputStream(myFile);
			   BitmapPhoto.compress(Bitmap.CompressFormat.PNG, 100, out);
			} catch (Exception e) {
			       e.printStackTrace();
		}
	}
	
	//取得圖片
	public static Bitmap getBitmapFromSdCard(String filePath)
	{
	   	//Only decode image size. Not whole image
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, option);
        //The new size to decode to 
        final int NEW_SIZE = 100;
        //Now we have image width and height. We should find the correct scale value. (power of 2)
        int width=option.outWidth;
        int height=option.outHeight;
        int scale=1;
        while(true){

            if(width/2 < NEW_SIZE || height/2 < NEW_SIZE)

                break;
            width/=2;
            height/=2;
            scale++;

        }
        //Decode again with inSampleSize
        option = new BitmapFactory.Options();
        option.inSampleSize=scale;
        return BitmapFactory.decodeFile(filePath, option);
	   
	}
	
	public static Bitmap getBitmapFromUrl(String _str_url)
	{
	    URL imageUrl = null;
	    Bitmap bitmap = null;
	    while(bitmap == null){
	    	try{
		    	/* new URL物件將網址傳入 */
		    	imageUrl = new URL(_str_url);
		    }catch(MalformedURLException e){
		    	e.printStackTrace();
		    }
		    if(imageUrl==null){return null;}
		    try{
		    	/* 取得連線 */
		    	HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
//		    	HttpURLConnection.setFollowRedirects(false);
		    	conn.setConnectTimeout(2000);
		    	conn.setReadTimeout(2000);
		    	
		    	conn.connect();
		    	/* 取得回傳的InputStream */
		    	InputStream is = conn.getInputStream();
		    	/* 將InputStream變成Bitmap */
		    	bitmap = BitmapFactory.decodeStream(is);
		    	is.close();
		      
		    } catch (IOException e){
		      e.printStackTrace();
		      
		      return null;
		    }
	    }
	    return bitmap;
	}
		
	
}
