package com.example.philipsremote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.philipsremote.utils.Utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		//String uri = "http://"+ip.getText()+":1925/1/audio/volume";//TODO
		String uri = "http://echo.jsontest.com/muted/false/current/15/min/0/max/60";
		
		
		
		HttpClient client = new DefaultHttpClient();
		HttpUriRequest request = new HttpGet(uri);
		
		
		try {
			
			HttpResponse response = new HttpRequestTask().execute(client, request).get();
		
			JSONObject jsonObject = Utils.parseJsonObject(response.getEntity().getContent());

			Log.i(TAG, jsonObject.toString());

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		};
	}
	
	private class HttpRequestTask extends AsyncTask<Object, Object, HttpResponse> {
		@Override
		protected HttpResponse doInBackground(Object... params) {
			try {
				return ((HttpClient)params[0]).execute((HttpUriRequest) params[1]);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}

}
