package com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by JoeyElliott on 6/18/2016.
 */
public class CardSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_CARDS = "cards";
    public static final String COLUMN_CARDS_CARD_ID = "cardId";
    public static final String COLUMN_CARDS_CARD_NAME = "name";
    public static final String COLUMN_CARDS_CARD_SET = "cardSet";
    public static final String COLUMN_CARDS_CARD_TYPE = "type";
    public static final String COLUMN_CARDS_CARD_FACTION = "faction";
    public static final String COLUMN_CARDS_CARD_PLAYER_CLASS = "playerClass";
    public static final String COLUMN_CARDS_CARD_RARITY = "rarity";
    public static final String COLUMN_CARDS_CARD_TEXT = "text";
    public static final String COLUMN_CARDS_CARD_FLAVOR = "flavor";
    public static final String COLUMN_CARDS_CARD_ARTIST = "artist";
    public static final String COLUMN_CARDS_CARD_COST = "cost";
    public static final String COLUMN_CARDS_CARD_ATTACK = "attack";
    public static final String COLUMN_CARDS_CARD_HEALTH = "health";
    public static final String COLUMN_CARDS_CARD_MECHANICS = "mechanics";
    public static final String COLUMN_CARDS_CARD_ELITE = "elite";
    public static final String COLUMN_CARDS_CARD_COLLECTIBLE = "collectible";
    public static final String COLUMN_CARDS_CARD_IMG_URL = "imgUrl";
    public static final String COLUMN_CARDS_CARD_IMG_BITMAP_AS_STRING = "imgBitmap";
    public static final String COLUMN_CARDS_CARD_IMG_GOLD_URL = "imgGoldUrl";
    public static final String COLUMN_CARDS_CARD_IMG_GOLD_BITMAP_AS_STRING = "imgGoldBitmap";

    private static final String CARD_DATABASE_NAME = "cards.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String CARD_DATABASE_CREATE = "create table "
            + TABLE_CARDS + "(" + COLUMN_CARDS_CARD_ID + " primary key, "
            + COLUMN_CARDS_CARD_NAME + ", "
            + COLUMN_CARDS_CARD_SET + ", "
            + COLUMN_CARDS_CARD_TYPE + ", "
            + COLUMN_CARDS_CARD_FACTION + ", "
            + COLUMN_CARDS_CARD_PLAYER_CLASS + ", "
            + COLUMN_CARDS_CARD_RARITY + ", "
            + COLUMN_CARDS_CARD_TEXT + ", "
            + COLUMN_CARDS_CARD_FLAVOR + ", "
            + COLUMN_CARDS_CARD_ARTIST + ", "
            + COLUMN_CARDS_CARD_COST + ", "
            + COLUMN_CARDS_CARD_ATTACK + ", "
            + COLUMN_CARDS_CARD_HEALTH + ", "
            + COLUMN_CARDS_CARD_MECHANICS + ", "
            + COLUMN_CARDS_CARD_ELITE + ", "
            + COLUMN_CARDS_CARD_COLLECTIBLE + ", "
            + COLUMN_CARDS_CARD_IMG_URL + ", "
            + COLUMN_CARDS_CARD_IMG_BITMAP_AS_STRING + ", "
            + COLUMN_CARDS_CARD_IMG_GOLD_URL + ", "
            + COLUMN_CARDS_CARD_IMG_GOLD_BITMAP_AS_STRING + ");";

    public CardSQLiteHelper(Context context) {
        super(context, CARD_DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CARD_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(CardSQLiteHelper.class.getName(), "Upgrading database from version " +
                oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS "  + TABLE_CARDS);
        onCreate(db);
    }
}
