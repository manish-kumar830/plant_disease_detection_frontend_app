package com.plant.disease.plantdiseasedetection;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitApiCall {
    // as we are making a get request specifying annotation as get and adding a url end point to it.
    @Multipart
    @POST("plantdisease")
    Call<ResponseModel> getDiseaseName(
            @Part MultipartBody.Part file
            );
}
