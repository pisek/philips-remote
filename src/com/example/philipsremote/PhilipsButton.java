package com.example.philipsremote;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

public class PhilipsButton extends Button {

	private String jsonKey;

	public PhilipsButton(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		TypedArray a = context.getTheme().obtainStyledAttributes(
				attrs,
				R.styleable.PhilipsButton,
				0, 0);

		try {
			setJsonKey(a.getString(R.styleable.PhilipsButton_json_key));
		} finally {
			a.recycle();
		}
		
	}

	public String getJsonKey() {
		return jsonKey;
	}

	public void setJsonKey(String jsonKey) {
		this.jsonKey = jsonKey;
	}
	
}
