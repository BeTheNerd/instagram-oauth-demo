package com.danieloskarsson.instagram.oauth.demo;

import org.gerstner.oauth2android.Client;
import org.gerstner.oauth2android.OAuth;
import org.gerstner.oauth2android.Server;
import org.gerstner.oauth2android.common.Connection;
import org.gerstner.oauth2android.token.BearerTokenTypeDefinition;
import org.gerstner.oauth2android.token.TokenTypeDefinition;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * @author Daniel Oskarsson (daniel.oskarsson@gmail.com)
 * 
 * Based on: https://github.com/poller/instagram-demo-for-android
 */
public class OAuthActivity extends Activity {

	public final static String TOKEN = "token";

	private final static String AUTHORIZATION_SERVER = "https://instagram.com/oauth/authorize/";
	private final static String ACCESS_TOKEN_SERVER = "https://instagram.com/oauth/authorize/";
	private final static String RESOURCE_SERVER = "https://api.instagram.com/v1";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = this.getIntent();
		String clientId = intent.getExtras().getString("clientId");
		String clientSecret = intent.getExtras().getString("clientSecret");
		String redirectUri = intent.getExtras().getString("redirectUri");
		Client client = new Client(clientId, clientSecret, redirectUri);

		Server server = new Server(AUTHORIZATION_SERVER, ACCESS_TOKEN_SERVER, RESOURCE_SERVER);
		server.setPreferredHttpMethod(Connection.HTTP_METHOD_GET);
		server.useAuthorizationHeader(false);

		TokenTypeDefinition tokenTypeDefinition = new BearerTokenTypeDefinition();

		OAuth oAuth = new OAuth(server, client, tokenTypeDefinition);
		String authorizationUri = oAuth.returnAuthorizationRequestUri();
		authorizationUri = authorizationUri.replace("code&", "token&");

		Uri uri = Uri.parse(authorizationUri);
		Intent web = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(web);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Uri uri = intent.getData();
		if (uri != null) {
			String path = uri.toString();
			String accessToken = path.substring(path.indexOf("=") + 1);

			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
			sharedPreferences.edit().putString(TOKEN, accessToken).commit();
		}
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}