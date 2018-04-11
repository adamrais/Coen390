

package com.farmingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtil {
	private static SharedPreferenceUtil sharedPreferenceUtil;

	private static SharedPreferences sharedPreferences;
	private final static String KEY = "alarm_sharedpreferences_p";

	private SharedPreferenceUtil(Context context) {
		sharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
	}

	public static SharedPreferenceUtil getInstance(Context context) {
		if (sharedPreferenceUtil == null) {
			sharedPreferenceUtil = new SharedPreferenceUtil(context);
		}
		return sharedPreferenceUtil;
	}
	public void putString(String key, String value) {
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public void putLong(String key, long value) {
		Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public void putInt(String key, int value) {
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public void putBoolean(String key, boolean value) {
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public void putFloat(String key, float value) {
		Editor editor = sharedPreferences.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public String getString(String key) {
		return sharedPreferences == null ? "" : sharedPreferences.getString(key, "");
	}

	public boolean getBoolean(String key, boolean deafultValue) {
		return sharedPreferences.getBoolean(key, deafultValue);
	}

	public int getInt(String key) {
		return sharedPreferences.getInt(key, 0);
	}


	public long getLong(String key) {
		return sharedPreferences.getLong(key, 0);
	}


	public float getFloat(String key) {
		return sharedPreferences.getFloat(key, 0);
	}

	public boolean has(String key) {
		return sharedPreferences.contains(key);
	}

}
