package com.example.donghe.mynethttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.donghe.mynethttp.net.AbsAPICallback;
import com.example.donghe.mynethttp.net.LocalService;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    List<HealthClassifyBean> titleList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestData();
    }

    private void requestData() {
        LocalService.getApi().getHealthClassify()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<HealthClassifyBean>>() {
                    @Override
                    protected void onDone(List<HealthClassifyBean> list) {

                        //请求成功，做相应的页面操作

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        //e.getMessage() 可获取服务器返回错误信息
                    }
                });
    }

}
