package com.example.hutsadmin;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NUMBER = "number";
    private static final String HUT_NAME = "hutName";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_ADMIN_FCM_TOKEN = "admin_fcm_token";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(HUT_NAME, Context.MODE_PRIVATE);

        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(KEY_NUMBER , Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(KEY_IS_LOGGED_IN , Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(KEY_ADMIN_FCM_TOKEN , Context.MODE_PRIVATE);


        editor = sharedPreferences.edit();
    }
    public void setAdminFcmToken(String adminFcmToken) {
        editor.putString(KEY_ADMIN_FCM_TOKEN, adminFcmToken);
        editor.apply();
    }

    public String getAdminFcmToken() {
        return sharedPreferences.getString(KEY_ADMIN_FCM_TOKEN, null);
    }
    public void saveCredentials(String name, String password, String email ,String number) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NUMBER ,number);
        editor.apply();
    }
    public void saveCredentials(String password, String email , boolean isLogin ) {

        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_EMAIL, email);

        editor.putBoolean(KEY_IS_LOGGED_IN ,isLogin);
        editor.apply();
    }

    public String getNaame() {
        return sharedPreferences.getString(KEY_NAME, "");
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getNumber()
    {
        return sharedPreferences.getString(KEY_NUMBER,"");
    }





    public void saveHutName(String hutName) {
        editor.putString(HUT_NAME, hutName);
        editor.apply();
    }

    public String getHutName() {
        return sharedPreferences.getString(HUT_NAME, "");
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
