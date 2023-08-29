package com.example.hutsadmin.messeges;

import static com.example.hutsadmin.messeges.Values.CONTENT_TYPES;
import static com.example.hutsadmin.messeges.Values.SERVER_KEY;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers({"Authorization: key="+SERVER_KEY,"Content-Type:"+CONTENT_TYPES})
    @POST("fcm/send")
    Call<PushNotification> sendNotification(@Body PushNotification notification);
}
