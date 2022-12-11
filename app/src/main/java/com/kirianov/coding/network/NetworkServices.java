package com.kirianov.coding.network;

import com.kirianov.coding.network.responses.DetailsItemResponse;
import com.kirianov.coding.network.responses.ListItemResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkServices {

    @GET("resource/s3k6-pzi2.json")
    Call<List<ListItemResponse>> getSchoolList();

    @GET("resource/f9bf-2cp4.json")
    Call<List<DetailsItemResponse>> getDetails(@Query("dbn") String dbn);
}
