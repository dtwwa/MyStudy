package com.animation_study.activity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.animation_study.R;
import com.animation_study.in.RetrofitUrl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by css on 2017/11/17.
 */

public class RetrofitActivity extends BaseActiytiy {
    private TextView tvContent;

    @Override
    public Integer getLayout() {
        return R.layout.activity_retrofit;
    }

    @Override
    public void initData() {
        final Retrofit builder = new Retrofit.Builder()
                //设置数据解析
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, String>() {
                            @Override
                            public String convert(ResponseBody value) throws IOException {
                                return value.string();
                            }
                        };
                    }
                })//解析方法

                .baseUrl("http://www.baidu.com")
                .build();
        RetrofitUrl retrofitUrl = builder.create(RetrofitUrl.class);
        Call<String> bardu = retrofitUrl.getBardu();
        bardu.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tvContent.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void findView() {
        tvContent = $(R.id.tv_content);
    }

    protected <T extends View> T $(int id) {
        return (T) findViewById(id);
    }

}
