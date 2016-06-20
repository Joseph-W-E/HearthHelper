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

    /**
     * The card database.
     */
    private SQLiteDatabase database;
    /**
     * The card database helper.
     */
    private CardSQLiteHelper dbHelper;
    /**
     * All of the columns available for the Card database.
     */
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
            CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_BITMAP_AS_BLOB,
            CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_GOLD_URL,
            CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_GOLD_BITMAP_AS_BLOB
    };

    /**
     * Creates a new instance of the database helper.
     * @param context The context of the activity this database will be used in.
     */
    public CardDataSource(Context context) {
        dbHelper = new CardSQLiteHelper(context);
    }

    /**
     * Open the database. Required to work with the database.
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Close the database. Do this every time you are done working with the database.
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Determines if the database currently has any cards in it.
     * @return cards already exist ? return true : return false
     */
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
    }

    /**
     * Creates the card to in the database.
     * Note that mechanics is stored as JSON.
     * @param card The card to be stored in the database.
     * @throws JSONException Thrown if mechanics fails to be converted into JSON.
     */
    public void createCard(Card card) {
        ContentValues values = new ContentValues();

        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_ID, card.getCardId() != null ? card.getCardId() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_NAME, card.getName() != null ? card.getName() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_SET, card.getCardSet() != null ? card.getCardSet() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_TYPE, card.getType() != null ? card.getType() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_FACTION, card.getFaction() != null ? card.getFaction() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_PLAYER_CLASS, card.getPlayerClass() != null ? card.getPlayerClass() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_RARITY, card.getRarity() != null ? card.getRarity() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_TEXT, card.getText() != null ? card.getText() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_FLAVOR, card.getFlavor() != null ? card.getFlavor() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_ARTIST, card.getArtist() != null ? card.getArtist() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_COST, card.getCost());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_ATTACK, card.getAttack());
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_HEALTH, card.getHealth());

        String mechanicsAsJSON;
        try {
            mechanicsAsJSON = card.getMechanicsAsJSON();
        } catch (JSONException e) {
            mechanicsAsJSON = "";
        }
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_MECHANICS, mechanicsAsJSON);

        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_ELITE, card.isElite() ? 1 : 0);
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_COLLECTIBLE, card.isCollectible() ? 1 : 0);
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_URL, card.getImg() != null ? card.getImg() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_BITMAP_AS_BLOB, "" != null ? "" : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_GOLD_URL, card.getImgGold() != null ? card.getImgGold() : "");
        values.put(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_GOLD_BITMAP_AS_BLOB, "" != null ? "" : "");

        database.insert(CardSQLiteHelper.TABLE_CARDS, null, values);
    }

    /**
     * Deletes a card from the database.
     * Deletion is based off the cardId.
     * @param card The card to be deleted.
     */
    public void deleteCard(Card card) {
        database.delete(CardSQLiteHelper.TABLE_CARDS, CardSQLiteHelper.COLUMN_CARDS_CARD_ID + " = " + card.getCardId(), null);
    }

    /**
     * Gets a card from the database based.
     * @param cardId Id of the card.
     * @return The card from the database, or null if no card is found.
     */
    public Card getCard(String cardId) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + CardSQLiteHelper.TABLE_CARDS
                + " where " + CardSQLiteHelper.COLUMN_CARDS_CARD_ID + " = '" + cardId + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                Card card = cursorToCard(cursor);
                cursor.close();
                return card;
            }
        }
        return null;
    }

    /**
     * Gets all the cards from the database and stores them as an Array List.
     * @return Array List of the cards in the database.
     */
    public ArrayList<Card> getCards() {
        ArrayList<Card> cards = new ArrayList<>();

        Cursor cursor = database.query(CardSQLiteHelper.TABLE_CARDS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Card card = cursorToCard(cursor);
            cards.add(card);
            cursor.moveToNext();
        }

        cursor.close();
        return cards;
    }

    /**
     * Updates the ImgByteArray of a card.
     * @param card The card to be updated.
     * @return The number of rows updated.
     */
    public int updateCardImgByteArray(Card card) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_BITMAP_AS_BLOB, card.getImgByteArray());
        return database.update(CardSQLiteHelper.TABLE_CARDS, contentValues,
                CardSQLiteHelper.COLUMN_CARDS_CARD_ID + "= '" + card.getCardId() + "'", null);
    }

    /**
     * Converts a cursor object into a card.
     * @param cursor The cursor to be converted.
     * @return The card obtained from the cursor.
     */
    private Card cursorToCard(Cursor cursor) {
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
        try {
            card.setMechanicsFromJSON(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_MECHANICS)));
        } catch (JSONException e) {
            card.setMechanics(null);
        }
        card.setElite(cursor.getInt(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_ELITE)));
        card.setCollectible(cursor.getInt(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_COLLECTIBLE)));
        card.setImg(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_URL)));
        card.setImgByteArray(cursor.getBlob(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_BITMAP_AS_BLOB)));
        card.setImgGold(cursor.getString(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_GOLD_URL)));
        card.setImgGoldByteArray(cursor.getBlob(cursor.getColumnIndex(CardSQLiteHelper.COLUMN_CARDS_CARD_IMG_GOLD_BITMAP_AS_BLOB)));
        return card;
    }

    /**
     * Drops the table.
     */
    public void dropEverything() {
        database.execSQL("DROP TABLE IF EXISTS "  + CardSQLiteHelper.TABLE_CARDS);
    }
}
