package com.lanceweather.android.util;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.lanceweather.android.db.City;
import com.lanceweather.android.db.County;
import com.lanceweather.android.db.Province;

import org.json.JSONArray;
import org.json.JSONObject;

public class Utility {
    public  static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response))
            try {
                JSONArray allProvinces = new JSONArray(response);
                for(int i=0;i<allProvinces.length();i++)
                {
                    JSONObject provinceObject=allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        return false;
    }
    public  static boolean handleCityResponse(String response,int provinceId){
        if(!TextUtils.isEmpty(response))
            try {
                JSONArray allCity = new JSONArray(response);
                for(int i=0;i<allCity.length();i++)
                {
                    JSONObject cityObject=allCity.getJSONObject(i);
                    City city = new City();
                    city.setCityCode(cityObject.getInt("id"));
                    city.setCityName(cityObject.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        return false;
    }
    public  static boolean handleCountyResponse(String response ,int cityId){
        if(!TextUtils.isEmpty(response))
            try {
                JSONArray allCounties = new JSONArray(response);
                for(int i=0;i<allCounties.length();i++)
                {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCityId(cityId);
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.save();
                }
                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        return false;
    }
}
