package com.omneagate.dpc.Service;

import java.util.HashMap;


public interface ServiceResponseListener {
    public void onSuccessResponse(String url,
                                  HashMap<String, Object> requestParams, String response);

    public void onError(String url, HashMap<String, Object> requestParams,
                        String errorMessage, Boolean isVolleyError);

    public void onNetWorkFailure(String url);
}
