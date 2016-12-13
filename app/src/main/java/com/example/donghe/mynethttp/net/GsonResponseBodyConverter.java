package com.example.donghe.mynethttp.net;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by hedong on 2016/4/19.
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    /**
     * {
     * "status": true,
     * "data": [
     * {
     * "description": "",
     * "id": 6,
     * "keywords": "",
     * "name": "",
     * "seq": 1,
     * "title": ""
     * },
     * {
     * "description": "",
     * "id": 5,
     * "keywords": "",
     * "name": "",
     * "seq": 2,
     * "title": ""
     * }
     * ]
     * }
     *
     * @param value
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Log.d("Network", "response>>" + response);
        try {

            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                //result==true表示成功返回，继续用本来的Model类解析
                String data = jsonObject.getString("data");

                return gson.fromJson(data, type);

            } else {
                //ErrResponse 将msg解析为异常消息文本
                ErrResponse errResponse = gson.fromJson(response, ErrResponse.class);
                throw new ResultException(0, errResponse.getMsg());
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Network", e.getMessage());
            return null;
        }
    }
}
