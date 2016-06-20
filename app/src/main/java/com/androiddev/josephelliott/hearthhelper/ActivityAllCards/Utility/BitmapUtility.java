package com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Utility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;

import com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Model.Card;
import com.androiddev.josephelliott.hearthhelper.ActivityAllCards.Storage.CardDataSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

/**
 * http://stackoverflow.com/questions/11790104/how-to-storebitmap-image-and-retrieve-image-from-sqlite-database-in-android
 * @ LazyNinja
 * Thanks man.
 * @ Google
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

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmap(byte[] arr, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(arr, 0, arr.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(arr, 0, arr.length, options);
    }

    /**
     * Created by Joseph Elliott on 5/14/2016.
     *
     * Takes in a bitmap and a context.
     * The bitmap is loaded in the background, and then stored in the cards database.
     */
    public static class BitmapDownloadToDatabaseAsyncTask extends AsyncTask<String, Void, Void> {

        private Card card;
        private Context context;

        public BitmapDownloadToDatabaseAsyncTask(Card card, Context context) {
            this.card = card;
            this.context = context;
        }

        /**
         * Downloads the image from the card's img url.
         * @param params
         * @return
         */
        @Override
        protected Void doInBackground(String... params) {
            if (card.getImgByteArray() == null) {
                try {
                    if (android.os.Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }
                    URL url = new URL(card.getImg());
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    card.setImgByteArray(getBytes(decodeSampledBitmap(getBytes(bmp), 100, 100)));
                    bmp.recycle();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        /**
         * After the download is finished, update the database with the proper url.
         * @param v
         */
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
