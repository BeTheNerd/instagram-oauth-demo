package com.danieloskarsson.instagram.oauth.demo;

import java.io.IOException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Daniel Oskarsson (daniel.oskarson@gmail.com)
 */
class RefreshViewTask extends AsyncTask<URL, Void, Bitmap> {

	private final static String TAG = RefreshViewTask.class.getSimpleName();

	private final static int REFRESH_INTERVAL = 5 * 1000;

	private static ImageView image;
	private static TextView caption;
	private static TextView name;
	private static TextView likes;

	public static void setViews(ImageView image, TextView caption, TextView name, TextView likes) {
		RefreshViewTask.image = image;
		RefreshViewTask.caption = caption;
		RefreshViewTask.name = name;
		RefreshViewTask.likes = likes;
	}

	protected Bitmap doInBackground(URL... urls) {
		for (URL url : urls) {
			try {
				return BitmapFactory.decodeStream(url.openConnection().getInputStream());
			} catch (IOException e) {
				Log.e(TAG, e.getLocalizedMessage());
			}
		}
		return null;
	}

	protected void onPostExecute(Bitmap bitmap) {
		InstagramObject instagramObject = InstagramObjects.current();

		RefreshViewTask.image.setImageBitmap(bitmap);
		RefreshViewTask.caption.setText(instagramObject.getCaption());
		RefreshViewTask.name.setText("@"+instagramObject.getUsername());
		RefreshViewTask.likes.setText(String.format("%d likes", instagramObject.getNumberOfLikes()));

		InstagramObjects.next();
		handler.postDelayed(runnable, REFRESH_INTERVAL);
	}

	private Handler handler = new Handler();

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (InstagramObjects.size() <= 1)
				return;

			URL url = InstagramObjects.current().getImageLink();
			new RefreshViewTask().execute(url);
		}
	};
}
