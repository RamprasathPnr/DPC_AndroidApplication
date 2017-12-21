/*@author Sathish kumar.R
 * Blaze dream technologies
 * */
package com.omneagate.dpc.Service;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;

public class Application extends android.app.Application {
	private static Context mContext;
	private static Application mInstance = null;
	public static final String TAG = Application.class.getSimpleName();

	public static synchronized Application getInstance() {
		return mInstance;
	}

	private RequestQueue mRequestQueue;

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	@Override
	public void onCreate() {
		mInstance = this;
		mContext = getApplicationContext();
		// The following line triggers the initialization of CrashHandler
		//		Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(mContext));
//		Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(mContext));
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		mInstance = null;
		super.onTerminate();
	}

	public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
		ConnectivityReceiver.connectivityReceiverListener = listener;
	}

}
