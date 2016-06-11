package com.androiddev.josephelliott.hearthhelper.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

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
public class CustomAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private Bitmap imageReference;
    private ImageView imageView;

    public CustomAsyncTask(Bitmap image, ImageView imageView) {
        this.imageReference = image;
        this.imageView = imageView;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;
        } catch (Exception e) {
            return null;
        }
    }

    // Set the new imageView and card bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageReference = bitmap;
        imageView.setImageBitmap(imageReference);
    }
}
