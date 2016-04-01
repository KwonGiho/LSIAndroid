package com.example.kwongyo.lsi;

import retrofit2.http.POST;

/**
 * Created by kwongyo on 2016-04-01.
 */
public interface MovieUpload {
    @POST("127.0.0.1:8089/MovieUploadServlet")
    public void MovieUpload();
}
