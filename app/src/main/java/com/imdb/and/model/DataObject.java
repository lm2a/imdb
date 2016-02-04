package com.imdb.and.model;

import android.content.ContentValues;


import com.imdb.and.helpers.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataObject {
	
	public int primaryKey = Constants.NO_PRIMARY_KEY;
	
	public static final int TYPE_MODEL_1 = 0;
	public static final int TYPE_MODEL_2 = 0;
	
	public DataObject() {
		
	}
	
	public DataObject(JSONObject jsonObject) {
		
	}

	public List<Integer> extractArrayIntegers(String json) throws JSONException {
		JSONArray array = new JSONArray(json);
		List<Integer> extractedInts = new ArrayList<Integer>(array.length());
		
		for (int i = 0; i < array.length(); i++) {
			extractedInts.add(array.getInt(i));
		}
		return extractedInts;
	}
	
	public List<String> extractArrayStrings(String json) throws JSONException {
		JSONArray array = new JSONArray(json);
		List<String> extractedStrings = new ArrayList<String>(array.length());
		
		for (int i = 0; i < array.length(); i++) {
			extractedStrings.add(array.getString(i));
		}
		return extractedStrings;
	}
	
	
	public Map<String, Object> dictionary() {
		Map<String, Object> dictionary = new HashMap<String, Object>();
		dictionary.put("primaryKey", this.primaryKey);
		return dictionary;
	}
	
	public ContentValues contentValues() {
		ContentValues values = new ContentValues();
		values.put("ID", this.primaryKey);
		return values;
	}

	
	public String toJsonString() {
		return "";
	}
}
