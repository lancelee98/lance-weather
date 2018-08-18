package com.lanceweather.android.gson;

import com.google.gson.annotations.SerializedName;

public class Forecast {
    public String date;
    @SerializedName("cond")
    public  More more;

    @SerializedName("tmp")
    public  Temperature temperature;

    public class  Temperature
    {
        public String min;
        public  String max;
    }
    public  class  More
    {
        @SerializedName("txt_d")
        public String info;
    }
}
