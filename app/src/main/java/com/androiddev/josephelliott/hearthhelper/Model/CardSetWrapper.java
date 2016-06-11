package com.androiddev.josephelliott.hearthhelper.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Joseph Elliott on 5/14/2016.
 */
public class CardSetWrapper {

    @SerializedName("Basic")
    private ArrayList<Card> basicCards;

    @SerializedName("Classic")
    private ArrayList<Card> classicCards;

    @SerializedName("Naxxramas")
    private ArrayList<Card> naxxCards;

    @SerializedName("Goblins vs Gnomes")
    private ArrayList<Card> gvgCards;

    @SerializedName("Blackrock Mountain")
    private ArrayList<Card> brmCards;

    @SerializedName("The Grand Tournament")
    private ArrayList<Card> tgtCards;

    @SerializedName("The League of Explorers")
    private ArrayList<Card> loeCards;

    @SerializedName("Whispers of the Old Gods")
    private ArrayList<Card> wogCards;

    public CardSetWrapper() {
        basicCards = new ArrayList<>();
        classicCards = new ArrayList<>();
        naxxCards = new ArrayList<>();
        gvgCards = new ArrayList<>();
        brmCards = new ArrayList<>();
        tgtCards = new ArrayList<>();
        loeCards = new ArrayList<>();
        wogCards = new ArrayList<>();
    }

    public ArrayList<Card> getAllCards() {
        ArrayList<Card> al = new ArrayList<>();
        al.addAll(basicCards);
        al.addAll(classicCards);
        al.addAll(naxxCards);
        al.addAll(gvgCards);
        al.addAll(brmCards);
        al.addAll(tgtCards);
        al.addAll(loeCards);
        al.addAll(wogCards);
        return al;
    }

    public ArrayList<Card> getBasicCards() {
        return basicCards;
    }

    public void setBasicCards(ArrayList<Card> basicCards) {
        this.basicCards = basicCards;
    }

    public ArrayList<Card> getClassicCards() {
        return classicCards;
    }

    public void setClassicCards(ArrayList<Card> classicCards) {
        this.classicCards = classicCards;
    }

    public ArrayList<Card> getNaxxCards() {
        return naxxCards;
    }

    public void setNaxxCards(ArrayList<Card> naxxCards) {
        this.naxxCards = naxxCards;
    }

    public ArrayList<Card> getGvgCards() {
        return gvgCards;
    }

    public void setGvgCards(ArrayList<Card> gvgCards) {
        this.gvgCards = gvgCards;
    }

    public ArrayList<Card> getBrmCards() {
        return brmCards;
    }

    public void setBrmCards(ArrayList<Card> brmCards) {
        this.brmCards = brmCards;
    }

    public ArrayList<Card> getTgtCards() {
        return tgtCards;
    }

    public void setTgtCards(ArrayList<Card> tgtCards) {
        this.tgtCards = tgtCards;
    }

    public ArrayList<Card> getLoeCards() {
        return loeCards;
    }

    public void setLoeCards(ArrayList<Card> loeCards) {
        this.loeCards = loeCards;
    }

    public ArrayList<Card> getWogCards() {
        return wogCards;
    }

    public void setWogCards(ArrayList<Card> wogCards) {
        this.wogCards = wogCards;
    }

}
