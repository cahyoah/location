package com.example.asus.checkinlokasi.presenter;

import com.example.asus.checkinlokasi.data.network.network.RetrofitClient;
import com.example.asus.checkinlokasi.ui.AddLocationView;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                            JsonObject jsonObject = response.body();
                            String status = jsonObject.get("status").getAsString();
                            String message = jsonObject.get("message").getAsString();
                            if(status.equals("success")){
                                addLocationView.onSuccessPostLocation(message);
                            }else{
                                addLocationView.onFailedPostLocation(message);
                            }

                        }else {
                            addLocationView.onFailedPostLocation("Penyimpanan Gagal");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        addLocationView.onFailedPostLocation(t.getMessage());
                    }
                });

    }
}
