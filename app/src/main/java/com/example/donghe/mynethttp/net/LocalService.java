package com.example.donghe.mynethttp.net;

import android.os.Build;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by hedong on 2016/4/19.
 */
public class LocalService {
    public static final String API_BASE_URL = "http://www.tngou.net/api/info/";

    private static final LocalApi service = getRetrofit().create(LocalApi.class);

    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;

    public final static int CONNECT_TIMEOUT = 60;        //设置连接超时时间
    public final static int READ_TIMEOUT = 60;            //设置读取超时时间
    public final static int WRITE_TIMEOUT = 60;           //设置写的超时时间

    private static OkHttpClient genericClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("source-terminal", "Android")   //操作系统名称（注：ios、android）//设备型号
                                .addHeader("device-model", Build.MODEL)         //设备型号
                                .addHeader("os-version", Build.VERSION.RELEASE)//操作系统版本号
                                //.addHeader("app-name", name);//应用名称
                                .build();
                        return chain.proceed(request);
                    }
                }).build();
        return httpClient;
    }

    public static LocalApi getApi() {
        return service;
    }

    protected static Retrofit getRetrofit() {

        if (null == mRetrofit) {
            if (null == mOkHttpClient) {
                mOkHttpClient = genericClient();
            }


            mRetrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(ResponseConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();

        }

        return mRetrofit;
    }
}
