package com.lanceweather.android.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lanceweather.android.WeatherActivity;

import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;

public class HeWeather {
    public  static String weatherId;
    public static String getWeatherId(String locationString)
    {
        Log.i("Log","locationString1"+locationString);
        interfaces.heweather.com.interfacesmodule.view.HeWeather.getWeatherNow(MyApplication.getContext(), locationString, Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
                new interfaces.heweather.com.interfacesmodule.view.HeWeather.OnResultWeatherNowBeanListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.i("Log", "onError: ", throwable);
                        return ;
                    }

                    @Override
                    public void onSuccess(List<Now> list) {
                        weatherId=list.get(0).getBasic().getCid();
                        Log.i("Log","locationString1"+weatherId);
                    }
                });
        if(weatherId!=null)
            return weatherId;
        return null;

    }
}
