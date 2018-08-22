package com.lanceweather.android;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class MainActivity extends AppCompatActivity {
    public LocationClient mLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        HeConfig.init("HE1808161509581231", "87ba45595c7b4ba78a14c61eeaae2316");
        HeConfig.switchToFreeServerNode();
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getString("weather",null)!=null){
            Intent intent = new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
        }
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)!=
                PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(!permissionList.isEmpty()){
            String [] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);
        }
        else requestLocation();

    }
    private   void requestLocation(){
        initLocation();
        mLocationClient.start();
    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setScanSpan(5000);
        mLocationClient.setLocOption(option);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                if(grantResults.length>0){
                    boolean status=true;
                    for(int result :grantResults)
                    {
                        if(result!=PackageManager.PERMISSION_GRANTED){
                            status=false;
                        }
                    }
                    if(status==false)
                    {
                        Toast.makeText(this,"未同意定位权限，请您手动选择城市。",Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.activity_main);
                    }
                    else if(status==true)
                    {
                        Toast.makeText(this,"已同意定位权限，自动帮您选择城市。",Toast.LENGTH_SHORT).show();
                        requestLocation();
                    }
                }
                else {
                    Toast.makeText(this,"未知错误。",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
        }
    }
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location){
            String s=location.getLongitude()+","+location.getLatitude();
            Log.i("Log", "onSuccess: "+s);
            HeWeather.getWeatherNow(getApplicationContext(), s, Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
                    new HeWeather.OnResultWeatherNowBeanListener() {
                        @Override
                        public void onError(Throwable throwable) {
                            Log.i("Log", "onError: ", throwable);
                        }

                        @Override
                        public void onSuccess(List<Now> list) {
                            String weatherId=list.get(0).getBasic().getCid();
                            Intent intent = new Intent(getApplicationContext(),WeatherActivity.class);
                            intent.putExtra("weather_id",weatherId);
                            startActivity(intent);
                            finish();
                        }
                    });

        }
    }

   @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
