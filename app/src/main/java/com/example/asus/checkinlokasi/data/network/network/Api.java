package com.example.asus.checkinlokasi.data.network.network;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by adhit on 23/01/2018.
 */

public interface Api {


    @POST("service/lokasi.php")
    @FormUrlEncoded
    Call<JsonObject> postLocation(@Field("nama") String locationName,
                                  @Field("keterangan") String note,
                                  @Field("kontributor")  String contributor,
                                  @Field("lon") String longitude,
                                  @Field("lat") String latitude,
                                  @Field("aksi") String action);

    @GET("service/lokasi.php")
    Call<JsonObject> showAllLocation();

}
