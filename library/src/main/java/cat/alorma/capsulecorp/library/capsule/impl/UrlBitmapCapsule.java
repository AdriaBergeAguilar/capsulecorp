package cat.alorma.capsulecorp.library.capsule.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import cat.alorma.capsulecorp.library.capsule.abs.ImageUrlCapsule;

/**
 * Created by Bernat on 12/01/14.
 */
public class UrlBitmapCapsule extends ImageUrlCapsule {

    public UrlBitmapCapsule(Context ctx, String url) {
        super(url);
    }

    @Override
    protected void executeAsync(String url) {
        new AsyncBitmap().execute(url);
    }

    private class AsyncBitmap extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                java.net.URL url = new java.net.URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                if(input == null){
                    return null;
                }else{
                    return BitmapFactory.decodeStream(input);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }catch (OutOfMemoryError e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            setDownloadedBitmap(bitmap);
        }
    }
}
