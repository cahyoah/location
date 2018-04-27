package com.example.asus.checkinlokasi.presenter;

import com.example.asus.checkinlokasi.data.network.model.Location;
import com.example.asus.checkinlokasi.data.network.network.RetrofitClient;
import com.example.asus.checkinlokasi.ui.ListLocationView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListLocationPresenter {

    private ListLocationView listLocationView;

    public ListLocationPresenter (ListLocationView listLocationView){
        this.listLocationView = listLocationView;
    }

    public void showAllLocation(){
        RetrofitClient.getInstance()
                .getApi()
                .showAllLocation()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            String status = body.get("status").getAsString();
                            if(status.equals("success")){
                                JsonArray locationArray = body.get("data").getAsJsonArray();
                                Type type = new TypeToken<List<Location>>(){}.getType();
                                List<Location> locationList =  new Gson().fromJson(locationArray, type);
                                listLocationView.onSuccessShowAllLocation(locationList);
                            }else{
                                listLocationView.onFailedShowAllLocation("Data not Found");
                            }
                        }else{
                            listLocationView.onFailedShowAllLocation("Data not Found");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        listLocationView.onFailedShowAllLocation(t.getMessage());
                    }
                });
    }



}
