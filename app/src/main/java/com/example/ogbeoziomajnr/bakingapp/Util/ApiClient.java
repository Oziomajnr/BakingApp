package com.example.ogbeoziomajnr.bakingapp.Util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SQ-OGBE PC on 27/06/2017.
 */

public class ApiClient {
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
