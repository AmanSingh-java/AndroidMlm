package com.finnotive.mlm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class ProgressBarUtil {

	private static ProgressDialog mProgressDialog;
	private static Context mContext;
	private static Activity mActivity;
	private static final String TAG = "ProgressBarUtil";
	public static void show(Context aContext,String aMessage,Boolean isCancelable){
	    //Log.d(TAG, "show entry");
	    dismiss();
		mContext = aContext;
		mActivity = (Activity) aContext;
		mProgressDialog = ProgressDialog.show(aContext,"Finnotive",aMessage);
		
		mProgressDialog.setCancelable(isCancelable);

		// Login Code goes here. ignore if its pending.
		mProgressDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
			}
		});
		//mProgressDialog.setCancelable(true);
		//Log.d(TAG, "show exit");
	}

	public static void show(Activity aContext,String aMessage,Boolean isCancelable){
		//Log.d(TAG, "show entry");
		dismiss();

		mActivity = aContext;
		mProgressDialog = ProgressDialog.show(aContext,"Finnotive",aMessage);

		mProgressDialog.setCancelable(isCancelable);

		// Login Code goes here. ignore if its pending.
		mProgressDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
			}
		});
		//mProgressDialog.setCancelable(true);
		//Log.d(TAG, "show exit");
	}
	public static void dismiss(){
		//Log.d(TAG, "dismiss entry");
		try
		{
			if(null!= mProgressDialog && mProgressDialog.isShowing()){				
				mProgressDialog.dismiss();
			}
		}
		catch(Exception ex)
		{
			
		}
		//Log.d(TAG, "dismiss exit");
	}	
}
