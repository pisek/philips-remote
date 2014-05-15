package com.example.philipsremote.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

public final class Utils {

	public static final JSONObject parseJsonObject(InputStream is) throws IOException, JSONException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line).append('\n');
		}
		
		return new JSONObject(sb.toString());
	}
	
}
