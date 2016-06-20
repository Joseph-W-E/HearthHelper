package com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Utility.BitmapUtility;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Joseph Elliott on 5/14/2016.
 */
public class Card implements Comparable<Card> {

    @SerializedName("cardId")
    private String cardId;

    @SerializedName("name")
    private String name;

    @SerializedName("cardSet")
    private String cardSet;

    @SerializedName("type")
    private String type;

    @SerializedName("faction")
    private String faction;

    @SerializedName("playerClass")
    private String playerClass;

    @SerializedName("rarity")
    private String rarity;

    @SerializedName("text")
    private String text;

    @SerializedName("falor")
    private String flavor;

    @SerializedName("artist")
    private String artist;

    @SerializedName("cost")
    private int cost;

    @SerializedName("attack")
    private int attack;

    @SerializedName("health")
    private int health;

    @SerializedName("mechanics")
    private ArrayList<MechanicsWrapper> mechanics;

    @SerializedName("elite")
    private boolean elite;

    @SerializedName("collectible")
    private boolean collectible;

    @SerializedName("img")
    private String img;
    private byte[] imgByteArray;

    @SerializedName("imgGold")
    private String imgGold;
    private byte[] imgGoldByteArray;

    public Card() {

    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public ArrayList<MechanicsWrapper> getMechanics() {
        return mechanics;
    }

    public String getMechanicsAsJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("name", new JSONArray(getMechanics()));
        return json.toString();
    }

    public void setMechanics(ArrayList<MechanicsWrapper> mechanics) {
        this.mechanics = mechanics;
    }

    public void setMechanicsFromJSON(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONArray array = object.getJSONArray("name");
        ArrayList<MechanicsWrapper> mechanics = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            mechanics.add(new MechanicsWrapper(array.getString(i)));
        }
        setMechanics(mechanics);
    }

    public boolean isElite() {
        return elite;
    }

    public void setElite(boolean elite) {
        this.elite = elite;
    }

    public void setElite(int x) {
        elite = x != 0;
    }

    public boolean isCollectible() {
        return collectible;
    }

    public void setCollectible(boolean collectible) {
        this.collectible = collectible;
    }

    public void setCollectible(int x) {
        collectible = x != 0;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public byte[] getImgByteArray() {
        return imgByteArray;
    }

    public void setImgByteArray(byte[] array) {
        imgByteArray = array;
    }

    public String getImgGold() {
        return imgGold;
    }

    public void setImgGold(String imgGold) {
        this.imgGold = imgGold;
    }

    public byte[] getImgGoldByteArray() {
        return imgGoldByteArray;
    }

    public void setImgGoldByteArray(byte[] array) {
        imgGoldByteArray = array;
    }

    @Override
    public int compareTo(Card another) {
        return name.compareTo(another.getName());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getCardId() + ",");
        builder.append(getName() + ",");
        builder.append(getCardSet() + ",");
        builder.append(getType() + ",");
        builder.append(getFaction() + ",");
        builder.append(getPlayerClass() + ",");
        builder.append(getRarity() + ",");
        builder.append(getText() + ",");
        builder.append(getFlavor() + ",");
        builder.append(getArtist() + ",");
        builder.append(getCost() + ",");
        builder.append(getAttack() + ",");
        builder.append(getHealth() + ",");
        builder.append((getMechanics() != null ? Arrays.toString(getMechanics().toArray()) : "") + ",");
        builder.append((isElite() ? "true" : "false") + ",");
        builder.append((isCollectible() ? "true" : "false") + ",");
        builder.append(getImg() + ",");
        builder.append((getImgByteArray() != null ? getImgByteArray().length : "0"));
        builder.append(getImgGold() + ",");
        builder.append((getImgGoldByteArray() != null ? getImgGoldByteArray().length : "0"));
        return builder.toString();
    }
}
