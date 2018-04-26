package com.example.asus.checkinlokasi;

import android.location.Location;

interface LocationServiceView {
    void onDisabledGPSFromService(String internet_and_gps_not_available);

    void onSuccessGetLocationFromService(Location location);
}
