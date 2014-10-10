package com.lyx.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageUrlList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8282193541419112384L;
	private List<String> largeImageUrls;
	private String uri;
	private int width;
	private int heigth;

	public List<String> getLargeImageUrls() {
		return largeImageUrls;
	}

	public String getUri() {
		return uri;
	}

	public int getWidth() {
		return width;
	}

	public int getHeigth() {
		return heigth;
	}

	public void parseJson(JSONObject json) throws JSONException{
		largeImageUrls = parseImageUrlList(json);
		uri = json.getString("uri");
		width = json.getInt("width");
		heigth = json.getInt("height");
	}
	
	private List<String> parseImageUrlList(JSONObject largeImage) throws JSONException {
		JSONArray urls = largeImage.getJSONArray("url_list");
		
		List<String> largeImageUrls = new ArrayList<String>();
		int ulen = urls.length();
		for(int j=0;j<ulen;j++)
		{
			JSONObject uobj = urls.getJSONObject(j);
			String url = uobj.getString("url");
			largeImageUrls.add(url);
		}
		
		return largeImageUrls;
	}
}
