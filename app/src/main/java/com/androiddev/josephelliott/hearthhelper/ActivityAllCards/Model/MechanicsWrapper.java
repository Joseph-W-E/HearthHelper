package com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by JoeyElliott on 6/18/2016.
 */
public class MechanicsWrapper {

    @SerializedName("name")
    private String name;

    public MechanicsWrapper() {
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
