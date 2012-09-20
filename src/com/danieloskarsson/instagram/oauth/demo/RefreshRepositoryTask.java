package com.danieloskarsson.instagram.oauth.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Daniel Oskarsson (daniel.oskarson@gmail.com)
 */
class RefreshRepositoryTask extends AsyncTask<String, Void, String> {

	private final static String TAG = RefreshRepositoryTask.class.getSimpleName();

	protected String doInBackground(String... urls) {
		for (String url : urls) {
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
					Log.e(TAG, "Failed to download file");
					return null;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return builder.toString();
		}

		return null;
	}

	protected void onPostExecute(String jsonString) {
		if (jsonString == null) return;

		InstagramObjects.fromJson(jsonString);
		URL url = InstagramObjects.current().getImageLink();
		new RefreshViewTask().execute(url);
	}

}