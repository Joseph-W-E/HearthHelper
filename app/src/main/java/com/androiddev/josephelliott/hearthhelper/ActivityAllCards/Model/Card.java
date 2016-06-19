package com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Joseph Elliott on 5/14/2016.
 */
public class Card implements Comparable<Card> {
    /*
    public static String[] LIST_OF_MECHANICS = {
            "Battlecry", "Deathrattle", "Charge", "Taunt",
            "Divine Shield", "Stealth", "Enrage", "Windfury",
            "Choose One", "Overload", "Inspire"
    };*/

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
    private Bitmap bitmapImg;

    @SerializedName("imgGold")
    private String imgGold;
    private Bitmap bitmapImgGold;

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

    public void setMechanics(ArrayList<MechanicsWrapper> mechanics) {
        this.mechanics = mechanics;
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

    public Bitmap getBitmapImg() {
        return bitmapImg;
    }

    public void setBitmapImg(Bitmap bitmap) {
        bitmapImg = bitmap;
    }

    public String getImgGold() {
        return imgGold;
    }

    public void setImgGold(String imgGold) {
        this.imgGold = imgGold;
    }

    public Bitmap getBitmapImgGold() {
        return bitmapImgGold;
    }

    public void setBitmapImgGold(Bitmap bitmap) {
        bitmapImgGold = bitmap;
    }

    public void initializeBitmap() {
        if (bitmapImg == null) {
            try {
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                URL url = new URL(img);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                bitmapImg = bmp;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int compareTo(Card another) {
        return name.compareTo(another.getName());
    }
}
