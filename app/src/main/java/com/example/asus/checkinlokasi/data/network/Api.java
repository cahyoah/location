package com.example.asus.checkinlokasi.data.network;

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


    @GET("episode/showallepisode")
    Call<JsonObject> showAllEpisode() ;

    @POST("service/location.php")
    @FormUrlEncoded
    Call<JsonObject> postLocation(@Field("episode_name") String episodeName,
                                  @Field("keterangan") String note,
                                  @Field("kontributor")  String contributor,
                                  @Field("longitude") String longitude,
                                  @Field("latitude") String latitude,
                                  @Field("aksi") String action);

    @DELETE("episode/delete/{episodeId}")
    Call<JsonObject> deleteEpisode(@Path("episodeId") int episodeId);


    @GET("materi/showAllMateriByEpisodeId/{episodeId}")
    Call<JsonObject> showAllMateriByEpisodeId(@Path("episodeId") int episodeId) ;

    @Multipart
    @POST("materi/post")
    Call<JsonObject> postMateri(
            @Part("episode_id") int episode,
            @Part("materi_picture") RequestBody name,
            @Part MultipartBody.Part file);




}
