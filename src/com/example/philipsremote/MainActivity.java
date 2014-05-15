package com.example.philipsremote;

import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.philipsremote.utils.Utils;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	private HttpClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		client = new DefaultHttpClient();
		client.getParams().setParameter("http.socket.timeout", Utils.HTTP_TIMEOUT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void clickButton(View view) {
		
		if (!(view instanceof Button)) {
			Log.e(TAG, "Given view is not a Button");
		}
		
		TextView ip = (TextView) findViewById(R.id.ip_address);
		String uri = "http://"+ip.getText()+":1925/1/input/key";
//		String uri = "http://echo.jsontest.com/muted/false/current/15/min/0/max/60";
		

		JSONObject json = new JSONObject();
		try {
			json.put("key", "VolumeUp");
		} catch (JSONException e) {
			Log.e(TAG, "Could not put values to JSONObject", e);
			return;
		}
		
		
		HttpRequest request = new JSONRequestBuilder().method(JSONRequestBuilder.HttpMethod.POST).uri(uri).jsonObject(json).build();
		JSONObject jsonObject = Utils.executeJSONRequest(client, request);
		
		if (jsonObject == null) {
			Log.e(TAG, "JSONObject is null. Something went wrong.");
			return;
		}
		
		Log.i(TAG, jsonObject.toString());
		
	}
	
}
