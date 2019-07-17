package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Dossy on 7/29/2018.
 */

public class SharedPrefManager {


    //Key Pemanggilan Fungsi
    public static final String NAMA_APLIKASI = "qtaasteelapp";
    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_NAME = "USER_NAME";
    public static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    public static final String USER_ID = "USER_ID";

    public static final String NAMA_PENERIMA = "NAMA_PENERIMA";
    public static final String NO_PENERIMA = "NO_PENERIMA";
    public static final String ALAMAT_PENERIMA = "ALAMAT_PENERIMA";
    public static final String ALAMAT_ID = "ALAMAT_ID";
    public static final String JARAK = "JARAK";
    public static final String CITY = "CITY";
    public static final String ACTIVE_PAYMENT_TIME = "ACTIVE_PAYMENT_TIME";
    public static final String NAV_SELECTED = "NAV_SELECTED";
    public static final String HAVE_LOCATION = "HAVE_LOCATION";

    //Profile
    public static final String name = "name";
    public static final String email = "email";
    public static final String no_hp = "no_hp";
    public static final String tempat_lahir = "tempat_lahir";
    public static final String tanggal_lahir = "tanggal_lahir";
    public static final String gender = "gender";
    public static final String tentang = "tentang";

    public static final String ganti_kota = "ganti_kota";
    public static final String have_search_loc = "have_search_loc";

    //Message Setting
    public static final String notif = "notif";
    public static final String voice = "voice";
    public static final String vibrate = "vibrate";
    public static final String speakers = "speakers";

    //Define
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(NAMA_APLIKASI, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void setName(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getName() {
        return sharedPreferences.getString(name, "");
    }

    public void setEmail(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getEmail() {
        return sharedPreferences.getString(email, "");
    }

    public void setNo_hp(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getNo_hp() {
        return sharedPreferences.getString(no_hp, "");
    }

    public void setTempat_lahir(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getTempat_lahir() {
        return sharedPreferences.getString(tempat_lahir, "");
    }

    public void setTanggal_lahir(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getTanggal_lahir() {
        return sharedPreferences.getString(tanggal_lahir, "");
    }

    public void setGender(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getGender() {
        return sharedPreferences.getString(gender, "");
    }

    public void setTentang(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getTentang() {
        return sharedPreferences.getString(tentang, "");
    }


    public void setUserToken(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getUserToken() {
        return sharedPreferences.getString(USER_TOKEN, "");
    }

    public void setUserEmail(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getUserEmail() {
        return sharedPreferences.getString(USER_EMAIL, "");
    }

    public void setUserName(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getUserName() {
        return sharedPreferences.getString(USER_NAME, "");
    }

    public void setUserPosition(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }


    public void setUserId(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID, "");
    }

    public void setIsLoggedIn(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean getIsLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void setNamaPenerima(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getNamaPenerima() {
        return sharedPreferences.getString(NAMA_PENERIMA, "");
    }

    public void setNoPenerima(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getNoPenerima() {
        return sharedPreferences.getString(NO_PENERIMA, "");
    }

    public void setAlamatPenerima(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getAlamatPenerima() {
        return sharedPreferences.getString(ALAMAT_PENERIMA, "");
    }

    public void setCity(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getCity() {
        return sharedPreferences.getString(CITY, "");
    }

    public void setAlamatId(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getAlamatId() {
        return sharedPreferences.getString(ALAMAT_ID, "");
    }

    public void setJarak(String key, int nilai) {
        editor.putInt(key, nilai);
        editor.commit();
    }

    public int getJarak() {
        return sharedPreferences.getInt(JARAK, 0);
    }

    public void setActivePaymentTime(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean getActivePaymentTime() {
        return sharedPreferences.getBoolean(ACTIVE_PAYMENT_TIME, false);
    }

    public void setNavSelected(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getNavSelected() {
        return sharedPreferences.getInt(NAV_SELECTED, 0);
    }

    public void setHaveLocation(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getHaveLocation() {
        return sharedPreferences.getBoolean(HAVE_LOCATION, false);
    }


    public void setGanti_kota(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getGanti_Kota() {
        return sharedPreferences.getBoolean(ganti_kota, false);
    }

    public void setHave_search_loc(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getHave_search_loc() {
        return sharedPreferences.getBoolean(have_search_loc, true);
    }

    public void setNotif(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getNotif() {
        return sharedPreferences.getBoolean(notif, false);
    }


    public void setVoice(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getVoice() {
        return sharedPreferences.getBoolean(voice, false);
    }

    public void setVibrate(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getVibrate() {
        return sharedPreferences.getBoolean(vibrate, false);
    }

    public void setSpeakers(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getSpeakers() {
        return sharedPreferences.getBoolean(speakers, false);
    }

    public void deleteAll() {
        editor.remove(IS_LOGGED_IN);
        editor.commit();
    }
    
}