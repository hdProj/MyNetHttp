package com.example.donghe.mynethttp.net;


import com.example.donghe.mynethttp.HealthClassifyBean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by hedong on 2016/4/19.
 */
public interface LocalApi {

    //获取类别
    @GET("classify")
    Observable<List<HealthClassifyBean>> getHealthClassify();


}
