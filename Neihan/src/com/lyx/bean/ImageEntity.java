package com.lyx.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageEntity {

	private int type;
	private int commentCount;
	private Long groupId;
	private String content;
	private ImageUrlList largeList;
	private ImageUrlList middleList;

	public ImageEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public void parseJson(JSONObject item) throws JSONException{
		type = item.getInt("type");
		JSONObject group = item.getJSONObject("group");
		commentCount = group.getInt("comment_count");
		JSONObject largeImage = group.getJSONObject("large_image");
		JSONObject middleImage = group.getJSONObject("middle_image");
		groupId = group.getLong("group_id");
		content = group.getString("content");
		
		largeList = new ImageUrlList();
		largeList.parseJson(largeImage);
		
		middleList = new ImageUrlList();
		middleList.parseJson(middleImage);
	}

}
