package com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Utility;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Model.Card;
import com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Model.CardSetWrapper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Joseph Elliott on 5/14/2016.
 */
public class CardViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Card> cards;

    public CardViewAdapter(Context c, ArrayList<Card> cards) {
        this.context = c;
        this.cards = cards != null ? cards : new ArrayList<Card>();
        Collections.sort(cards);
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
                BitmapToImageViewAsyncTask bitmapToImageViewAsyncTask = new BitmapToImageViewAsyncTask(cards.get(position).getBitmapImg(), imageView);
                bitmapToImageViewAsyncTask.execute(cards.get(position).getImg());
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
