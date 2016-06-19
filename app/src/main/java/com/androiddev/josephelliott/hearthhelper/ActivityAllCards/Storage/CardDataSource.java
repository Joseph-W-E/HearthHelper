package com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Model.Card;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by JoeyElliott on 6/18/2016.
 */
public class CardDataSource {

    // Database fields
    private SQLiteDatabase database;
    private CardSQLiteHelper dbHelper;
    private String[] allColumns = {
            CardSQLiteHelper.COLUMN_CARDS_CARD_ID,
            CardSQLiteHelper.COLUMN_CARDS_CARD_SET,
            CardSQLiteHelper.COLUMN_CARDS_CARD_TYPE,
            CardSQLiteHelper.COLUMN_CARDS_CARD_FACTION,
            CardSQLiteHelper.COLUMN_CARDS_CARD_PLAYER_CLASS,
            CardSQLiteHelper.COLUMN_CARDS_CARD_RARITY,
            CardSQLiteHelper.COLUMN_CARDS_CARD_TEXT,
            CardSQLiteHelper.COLUMN_CARDS_CARD_FLAVOR,
            CardSQLiteHelper.COLUMN_CARDS_CARD_ARTIST,
            CardSQLiteHelper.COLUMN_CARDS_CARD_COST,
            CardSQLiteHelper.COLUMN_CARDS_CARD_ATTACK,
            CardSQLiteHelper.COLUMN_CARDS_CARD_HEALTH,
            CardSQLiteHelper.COLUMN_CARDS_CARD_MECHANICS,
            CardSQLiteHelper.COLUMN_CARDS_CARD_ELITE,
            CardSQLiteHelper.COLUMN_CARDS_CARD_COLLECTIBLE,
            CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_URL,
            CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_BITMAP_AS_STRING,
            CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_GOLD_URL,
            CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_GOLD_BITMAP_AS_STRING
    };

    public CardDataSource(Context context) {
        dbHelper = new CardSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean hasCardsTable() {
        Cursor cursor = database.rawQuery("SELECT * FROM " + CardSQLiteHelper.TABLE_CARDS +
                " LIMIT 10;", null);
        boolean exists = !cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public void createCard(Card card) throws JSONException {
        ContentValues values = new ContentValues();

        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_ID, card.getCardId());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_SET, card.getCardSet());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_TYPE, card.getType());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_FACTION, card.getFaction());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_PLAYER_CLASS, card.getPlayerClass());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_RARITY, card.getRarity());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_TEXT, card.getText());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_FLAVOR, card.getFlavor());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_ARTIST, card.getArtist());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_COST, card.getCost());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_ATTACK, card.getAttack());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_HEALTH, card.getHealth());

        JSONObject json = new JSONObject();
        json.put("name", new JSONArray(card.getMechanics()));
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_MECHANICS, json.toString());

        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_ELITE, card.isElite());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_COLLECTIBLE, card.isCollectible());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_URL, card.getImg());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_BITMAP_AS_STRING, "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_GOLD_URL, card.getImgGold());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_GOLD_BITMAP_AS_STRING, "");

        database.insert(CardSQLiteHelper.TABLE_CARDS, null, values);
    }

    public void deleteCard(Card card) {
        database.delete(CardSQLiteHelper.TABLE_CARDS, CardSQLiteHelper.COLUMN_CARDS_CARD_ID
                + " = " + card.getCardId(), null);
    }

    public ArrayList<Card> getCards() throws JSONException {
        ArrayList<Card> cards = new ArrayList<>();

        Cursor cursor = database.query(CardSQLiteHelper.TABLE_CARDS,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Card card = cursorToCard(cursor);
            cards.add(card);
            cursor.moveToNext();
        }

        cursor.close();
        return cards;
    }

    private Card cursorToCard(Cursor cursor) throws JSONException {
        Card card = new Card();
        card.setCardId(cursor.getString(0));
        card.setName(cursor.getString(1));
        card.setCardSet(cursor.getString(2));
        card.setType(cursor.getString(3));
        card.setFaction(cursor.getString(4));
        card.setPlayerClass(cursor.getString(5));
        card.setRarity(cursor.getString(6));
        card.setText(cursor.getString(7));
        card.setFlavor(cursor.getString(8));
        card.setArtist(cursor.getString(9));
        card.setCost(cursor.getInt(10));
        card.setAttack(cursor.getInt(11));
        card.setHealth(cursor.getInt(12));
        // handle logic here to extract mechanics from json
        card.setMechanics(null);
        card.setElite(cursor.getInt(14));
        card.setCollectible(cursor.getInt(15));
        card.setImg(cursor.getString(16));
        // handle logic here to extract bitmap from encoded string
        card.setBitmapImg(null);
        card.setImgGold(cursor.getString(18));
        // handle logic here to extract bitmap from encoded string
        card.setBitmapImgGold(null);
        return card;
    }
}
