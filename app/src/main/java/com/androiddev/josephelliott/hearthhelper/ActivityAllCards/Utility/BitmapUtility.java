package com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Model.Card;
import com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Storage.CardDataSource;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.sql.SQLException;

/**
 * http://stackoverflow.com/questions/11790104/how-to-storebitmap-image-and-retrieve-image-from-sqlite-database-in-android
 * @ LazyNinja
 * Thanks man.
 * Created by JoeyElliott on 6/19/2016.
 */
public class BitmapUtility {

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    /**
     * Created by Joseph Elliott on 5/14/2016.
     *
     * Takes in a bitmap and an image view.
     * In the background, find the image from the url and load it.
     * Set the bitmap and the image view to the bitmap.
     *
     * Why both the bitmap and image view?
     * When this asynctask is finished, we need to notify the grid view
     * AND the list of cards. This keeps the grid and list decoupled.
     */
    public static class BitmapToImageViewAsyncTask extends AsyncTask<String, Void, Void> {

        private Card card;
        private Context context;

        public BitmapToImageViewAsyncTask(Card card, Context context) {
            this.card = card;
            this.context = context;
        }

        @Override
        protected Void doInBackground(String... params) {
            card.initializeBitmap();
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            CardDataSource dataSource = new CardDataSource(context);
            try {
                dataSource.open();
                dataSource.updateCardImgByteArray(card);
                Log.i("Completed", "Completed update of database for " + card.getName());
            } catch (SQLException e) {
                Log.w("Failed", "Failed to update database byte[] for " + card.getName());
            } finally {
                dataSource.close();
            }
        }
    }
}
