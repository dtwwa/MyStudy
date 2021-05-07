package com.animation_study.in;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by css on 2017/11/17.
 */

public interface RetrofitUrl {
    @GET("/")
    Call<String> getBardu();
}
