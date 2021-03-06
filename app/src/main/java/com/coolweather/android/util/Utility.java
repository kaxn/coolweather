package com.coolweather.android.util;

import android.text.TextUtils;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import gson.Weather;

/**
 * Created by kaxn on 2018/1/8.
 */

public class Utility {
    public static boolean handleProvinceResponse(String  response){
        if (!TextUtils. isEmpty(response)){
            try{
                JSONArray allProvinces=new JSONArray(response);

                for (int i=0;i<allProvinces.length();i++) {
                    JSONObject provinceObject=allProvinces.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();

                } return true;
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        return  false;
    }
    public static boolean handleCityResponse(String  response,int provinceId){
        if (!TextUtils. isEmpty(response)){
            try{
                JSONArray allCities=new JSONArray(response);

                for (int i=0;i<allCities.length();i++) {
                    JSONObject cityObject=allCities.getJSONObject(i);
                    City city=new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();

                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        return  false;
    }

    public static boolean handleCountryResponse(String  response,int cityId){
        if (!TextUtils. isEmpty(response)){
            try{
                JSONArray allCountries=new JSONArray(response);

                for (int i=0;i<allCountries.length();i++) {
                    JSONObject cityObject=allCountries.getJSONObject(i);
                    County county=new County();
                    county.setCountyName(cityObject.getString("name"));
                    county.setWeatherId(cityObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();

                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        return  false;
    }
    public static Weather handleWeatherResponse(String response){
        try {
//            该方法通过JSONObject、JSONArray将天气数据中的主体内容解析出来
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
            String weatherContent =jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }


}
