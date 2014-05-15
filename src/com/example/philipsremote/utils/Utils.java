package com.example.philipsremote.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public final class Utils {
	
	public static final String TAG = "Utils";
	
	public static final Integer HTTP_TIMEOUT = 5000;

	public static final JSONObject parseJsonObject(InputStream is) throws IOException, JSONException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line).append('\n');
		}
		
		return new JSONObject(sb.toString());
	}
	
	public static class HttpRequestTask extends AsyncTask<Object, Object, HttpResponse> {
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

	public static JSONObject executeJSONRequest(HttpClient client, HttpRequest request) {
		
		try {
			
			HttpResponse response = new HttpRequestTask().execute(client, request).get(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
			
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				
				if (response.getEntity() != null) {
					return Utils.parseJsonObject(response.getEntity().getContent());
				} else {
					Log.d(TAG, "No response provided");
					return new JSONObject();
				}
				
			} else {
				
				Log.e(TAG, "Http response status: "+statusCode+" - "+statusLine.getReasonPhrase());
				
			}

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
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
