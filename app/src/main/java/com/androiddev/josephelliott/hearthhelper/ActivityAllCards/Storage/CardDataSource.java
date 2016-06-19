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
            CardSQLiteHelper.COLUMN_CARDS_CARD_NAME,
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
        Cursor cursor = database.rawQuery("SELECT " + CardSQLiteHelper.COLUMN_CARDS_CARD_ID
                + " from " + CardSQLiteHelper.TABLE_CARDS + " limit 1", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
        /*
        Cursor cursor = database.rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '"
                        + CardSQLiteHelper.TABLE_CARDS + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
        */
    }

    public void createCard(Card card) throws JSONException {
        ContentValues values = new ContentValues();

        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_ID,
                card.getCardId() != null ? card.getCardId() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_NAME,
                card.getName() != null ? card.getName() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_SET,
                card.getCardSet() != null ? card.getCardSet() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_TYPE,
                card.getType() != null ? card.getType() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_FACTION,
                card.getFaction() != null ? card.getFaction() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_PLAYER_CLASS,
                card.getPlayerClass() != null ? card.getPlayerClass() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_RARITY,
                card.getRarity() != null ? card.getRarity() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_TEXT,
                card.getText() != null ? card.getText() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_FLAVOR,
                card.getFlavor() != null ? card.getFlavor() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_ARTIST,
                card.getArtist() != null ? card.getArtist() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_COST, card.getCost());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_ATTACK, card.getAttack());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_HEALTH, card.getHealth());

        JSONObject json = new JSONObject();
        json.put("name", new JSONArray(card.getMechanics()));
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_MECHANICS,
                json.toString() != null ? json.toString() : "");

        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_ELITE,
                card.isElite() ? 1 : 0);
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_COLLECTIBLE,
                card.isCollectible() ? 1 : 0);
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_URL,
                card.getImg() != null ? card.getImg() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_BITMAP_AS_STRING,
                "" != null ? "" : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_GOLD_URL,
                card.getImgGold() != null ? card.getImgGold() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_GOLD_BITMAP_AS_STRING,
                "" != null ? "" : "");

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

    public boolean updateCardImgByteArray(Card card) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_BITMAP_AS_STRING, card.getImgByteArray());
        database.update(CardSQLiteHelper.TABLE_CARDS, contentValues, CardSQLiteHelper.
                COLUMN_CARDS_CARD_IMG_BITMAP_AS_STRING + "= ?", new String[] {card.getCardId()});
        return false;
    }

    private Card cursorToCard(Cursor cursor) throws JSONException {
        Card card = new Card();
        card.setCardId(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_ID)));
        card.setName(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_NAME)));
        card.setCardSet(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_SET)));
        card.setType(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_TYPE)));
        card.setFaction(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_FACTION)));
        card.setPlayerClass(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_PLAYER_CLASS)));
        card.setRarity(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_RARITY)));
        card.setText(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_TEXT)));
        card.setFlavor(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_FLAVOR)));
        card.setArtist(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_ARTIST)));
        card.setCost(cursor.getInt(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_COST)));
        card.setAttack(cursor.getInt(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_ATTACK)));
        card.setHealth(cursor.getInt(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_HEALTH)));
        // handle logic here to extract mechanics from json
        card.setMechanics(null);
        card.setElite(cursor.getInt(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_ELITE)));
        card.setCollectible(cursor.getInt(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_COLLECTIBLE)));
        card.setImg(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_URL)));
        // handle logic here to extract bitmap from encoded string
        card.setImgByteArray(null);
        card.setImgGold(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_GOLD_URL)));
        // handle logic here to extract bitmap from encoded string
        card.setImgGoldByteArray(null);
        return card;
    }

    public void dropEverything() {
        database.execSQL("DROP TABLE IF EXISTS "  + CardSQLiteHelper.TABLE_CARDS);
    }
}
