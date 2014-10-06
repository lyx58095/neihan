package com.lyx.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class Comment {

	private long uid;
	private String platform;
	private String text;
	private int digg_count;
	private int user_digg;
	private boolean user_verified;
	private int bury_count;
	private String user_profile_url;
	private long id;
	private String user_name;
	private int user_bury;
	private String user_profile_image_url;
	private String description;
	private long create_time;
	private long user_id;

	public void parseJson(JSONObject json) throws JSONException{
		if(json != null){
			uid = json.getLong("uid");
			platform = json.getString("platform");
			text = json.getString("text");
			digg_count = json.getInt("digg_count");
			user_digg = json.getInt("user_digg");
			user_verified = json.getBoolean("user_verified");
			bury_count = json.getInt("bury_count");
			user_profile_url = json.getString("user_profile_url");
			id = json.getLong("id");
			user_name = json.getString("user_name");
			user_bury = json.getInt("user_bury");
			user_profile_image_url = json.getString("user_profile_image_url");//用户头像网址
			description = json.optString("description");
			create_time = json.getLong("create_time");
			user_id = json.getLong("user_id");
		}
	}

	public long getUid() {
		return uid;
	}

	public String getPlatform() {
		return platform;
	}

	public String getText() {
		return text;
	}

	public int getDigg_count() {
		return digg_count;
	}

	public int getUser_digg() {
		return user_digg;
	}

	public boolean isUser_verified() {
		return user_verified;
	}

	public int getBury_count() {
		return bury_count;
	}

	public String getUser_profile_url() {
		return user_profile_url;
	}

	public long getId() {
		return id;
	}

	public String getUser_name() {
		return user_name;
	}

	public int getUser_bury() {
		return user_bury;
	}

	public String getUser_profile_image_url() {
		return user_profile_image_url;
	}

	public String getDescription() {
		return description;
	}

	public long getCreate_time() {
		return create_time;
	}

	public long getUser_id() {
		return user_id;
	}
	
}
