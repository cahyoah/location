package com.example.asus.checkinlokasi.presenter;

import com.example.asus.checkinlokasi.data.network.RetrofitClient;
import com.example.asus.checkinlokasi.ui.AddLocationView;
import com.example.asus.checkinlokasi.ui.LocationView;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;

public class AddLocationPresenter {

    private AddLocationView addLocationView;

    public AddLocationPresenter(AddLocationView addLocationView){
        this.addLocationView = addLocationView;
    }

    public void postLocation( String locationName,
                              String note,
                              String contributor,
                              String longitude,
                              String latitude,
                              String action){

        RetrofitClient.getInstance()
                .getApi()
                .postLocation(locationName,
                        note,
                        contributor,
                        longitude,
                        latitude,
                        action)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            addLocationView.onSuccessPostLocation("g");
                        }else {
                            addLocationView.onFailedPostLocation("t");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        addLocationView.onFailedPostLocation(t.getMessage());
                    }
                });

    }
}
