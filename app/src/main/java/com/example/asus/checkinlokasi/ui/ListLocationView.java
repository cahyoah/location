package com.example.asus.checkinlokasi.ui;

import com.example.asus.checkinlokasi.data.network.model.Location;

import java.util.List;

public interface ListLocationView {
    void onSuccessShowAllLocation(List<Location> locationList);

    void onFailedShowAllLocation(String data_not_found);
}
