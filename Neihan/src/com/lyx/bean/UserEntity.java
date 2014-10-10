package com.lyx.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1984018371741834589L;
	//头像网址
	private String avatarUrl;
	private long userId;
	private String name;
	//用户是否验证
	private boolean userVerified;
	
	public String getAvatarUrl() {
		return avatarUrl;
	}

	public long getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public boolean isUserVerified() {
		return userVerified;
	}

	public void parseJson(JSONObject json) throws JSONException{
		if(json != null){
			avatarUrl = json.getString("avatar_url");
			userId = json.getLong("user_id");
			name = json.getString("name");
			userVerified = json.getBoolean("user_verified");
		}
	}

/*	"user": {
        "avatar_url": "http://p1.pstatp.com/thumb/1367/2213311454",
        "user_id": 3080520868,
        "name": "请叫我梓安哥",
        "user_verified": false
    },*/
}
