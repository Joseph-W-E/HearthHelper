package com.androiddev.josephelliott.hearthhelper.Interfaces;

import com.androiddev.josephelliott.hearthhelper.Model.CardSetWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by Joseph Elliott on 5/14/2016.
 */
public interface HearthstoneAPIEndPointInterface {

    String URL = "https://omgvamp-hearthstone-v1.p.mashape.com/";
    String KEY = "70ELjsq44Mmsh2W51d1uQcSQ2jfAp1Qj05ejsnqcM5W99e13Eq";

    @Headers("X-Mashape-Key: " + KEY)
    @GET("cards?collectible=1")
    Call<CardSetWrapper> loadCardSets();
}
