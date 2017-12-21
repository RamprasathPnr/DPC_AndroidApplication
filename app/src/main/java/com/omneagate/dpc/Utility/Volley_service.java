package com.omneagate.dpc.Utility;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.omneagate.dpc.Service.Application;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user1 on 22/11/16.
 */
public class Volley_service {

    public void execute(final String url,
                        final String requestParams,
                        final ResultUpdatable result, final String id) {
        JsonObjectRequest req = null;
        try {
            req = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(requestParams), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    result.setResult(response.toString(), id);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    result.setResult(error.toString(),id);
                    Log.e("error", error.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Cookie", "JSESSIONID=" + SessionId.getInstance().getSessionId());
                    headers.put("Cookie", "SESSION=" + SessionId.getInstance().getSessionId());
                    return headers;
                }
            };
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int socketTimeout = 15000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        Application.getInstance().addToRequestQueue(req);
    }
}
