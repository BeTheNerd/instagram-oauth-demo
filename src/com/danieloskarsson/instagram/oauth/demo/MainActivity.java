package com.danieloskarsson.instagram.oauth.demo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Daniel Oskarsson (daniel.oskarson@gmail.com)
 */
public class MainActivity extends Activity {

	private static final double LATITUDE = 57.7018646;
	private static final double LONGITUDE = 11.9549555;

	private final static String CLIENT_SECRET
	private final static String CLIENT_ID

	private final static String MEDIA_SEARCH_URL = "https://api.instagram.com/v1/media/search?lat=%f&lng=%f&access_token=%s";

	private String getAccessToken() {
		SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
		String accessToken = preferenceManager.getString(OAuthActivity.TOKEN, null);
		if (accessToken == null) {
			Intent intent = new Intent(this, OAuthActivity.class);
			intent.putExtra("clientId", CLIENT_ID);
			intent.putExtra("clientSecret", CLIENT_SECRET);
			intent.putExtra("redirectUri", this.getString(R.string.redirect_url));
			startActivity(intent);
			finish();
		}
		return accessToken;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		RefreshViewTask.setViews(
			(ImageView) findViewById(R.id.imageView1),
			(TextView) findViewById(R.id.textViewCaption),
			(TextView) findViewById(R.id.textViewName),
			(TextView)findViewById(R.id.textViewLikes));

		String accessToken = getAccessToken();
		if (accessToken != null) {
			String url = String.format(MEDIA_SEARCH_URL, LATITUDE, LONGITUDE, accessToken);
			new RefreshRepositoryTask().execute(url);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_delete:
			SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
			preferenceManager.edit().remove(OAuthActivity.TOKEN).commit();
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
