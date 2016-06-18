package com.androiddev.josephelliott.hearthhelper.AllCardsActivity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.androiddev.josephelliott.hearthhelper.Model.Card;
import com.androiddev.josephelliott.hearthhelper.Model.CardSetWrapper;
import com.androiddev.josephelliott.hearthhelper.Utility.CustomAsyncTask;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Joseph Elliott on 5/14/2016.
 */
public class CardViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Card> cards;

    public CardViewAdapter(Context c, CardSetWrapper cardSetWrapper) {
        this.context = c;
        try {
            cards = cardSetWrapper.getAllCards();
            Collections.sort(cards);
        } catch (NullPointerException e) {
            cards = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            // if our card for this position doesn't have an image, get one
            if (cards.get(position).getBitmapImg() == null) {
                CustomAsyncTask customAsyncTask = new CustomAsyncTask(cards.get(position).getBitmapImg(), imageView);
                customAsyncTask.execute(cards.get(position).getImg());
                Log.d("Asynctask", "Running an async task for " + cards.get(position).getName());
            } else {
                imageView.setImageBitmap(cards.get(position).getBitmapImg());
            }
        } else {
            imageView = (ImageView) convertView;
        }

        return imageView;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
