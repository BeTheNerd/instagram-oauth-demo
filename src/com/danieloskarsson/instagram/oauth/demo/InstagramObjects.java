package com.danieloskarsson.instagram.oauth.demo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

/**
 * 
 * @author Daniel Oskarsson (daniel.oskarsson@gmail.com)
 * 
 *         The purpose of this class is to act as Instagram object repository.
 *         There are methods to populate the repository with objects from a json
 *         string, to get a random Instagram object, and to get the next
 *         Instagram object.
 * 
 */
class InstagramObjects {

	private final static String TAG = InstagramObject.class.getSimpleName();

	private static List<InstagramObject> instagramObjects = new ArrayList<InstagramObject>();
	private static Random random = new Random();
	private static int index;

	private InstagramObjects() {}

	/**
	 * Clear the object repository and load it with data from the json string.
	 * Not all data in the json string is represented in a Instagram object.
	 * This method also randomizes the position of the cursor.
	 * 
	 * @param jsonString
	 *            Json String representation of a list of Instagram objects.
	 */
	public static void fromJson(String jsonString) {
		instagramObjects = new ArrayList<InstagramObject>();

		try {
			JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("data");
			Log.d(TAG, "Number of entries in Json string " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				double latitude = jsonObject.getJSONObject("location").getDouble("latitude");
				double longitude = jsonObject.getJSONObject("location").getDouble("longitude");
				String filterName = jsonObject.getString("filter");
				String createdTime = jsonObject.getString("created_time");
				String publicLink = jsonObject.getString("link");
				int numberOfLikes = jsonObject.getJSONObject("likes").getInt("count");
				String imageLink = jsonObject.getJSONObject("images").getJSONObject("standard_resolution")
						.getString("url");
				String caption = jsonObject.isNull("caption") ? ""
						: jsonObject.getJSONObject("caption").getString("text");
				String username = jsonObject.getJSONObject("user").getString("username");
				String fullName = jsonObject.getJSONObject("user").getString("full_name");

				InstagramObject instagramObject = new InstagramObject(latitude, longitude, filterName, createdTime, new URL(
						publicLink), numberOfLikes, new URL(imageLink), caption, username, fullName);

				Log.d(TAG, instagramObject.toString());

				instagramObjects.add(instagramObject);
			}

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage());
		}

		random();
	}

	/**
	 * Move the cursor to a random position.
	 */
	private static void random() {
		index = random.nextInt(size());
	}

	/**
	 * Move the cursor one step. If the current is the last move to the first position.
	 */
	public static void next() {
		if (++index == size())
			index = 0;
	}
	
	/**
	 * @return The InstagramObject at the current position.
	 */
	public static InstagramObject current() {
		return instagramObjects.get(index);
	}

	/**
	 * @return The number of Instagram objects.
	 */
	public static int size() {
		return instagramObjects.size();
	}
}
