package com.lyx.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EntityList {

	private boolean has_more;
	private long min_time;
	private String tip;
	private long max_time;
	
	private List<TextEntity> entites;

	public void parseJson(JSONObject json) throws JSONException{
		if(json!=null){
			has_more = json.getBoolean("has_more"); //是否可以加载更多
			tip = json.getString("tip");
			if(has_more == true){
				min_time = json.getLong("min_time");
			}
			max_time = json.optLong("max_time");
			
			//从data对象中，获取名称为data的数组，它代表的是段子列表的数据
			JSONArray array = json.getJSONArray("data");
			
			int len = array.length();
			
			if(len>0)
			{
				entites = new ArrayList<TextEntity>();
				
				//遍历数组中的每一条图片段子信息
				for(int i=0;i<len;i++)
				{
					JSONObject item = array.getJSONObject(i);
					
					int type = item.getInt("type"); //获取类型，1段子，5广告
					if(type == 5){
						//TODO处理广告内容
						AdEntity entity = new AdEntity();
						entity.parseJson(item);
						String downloadUri = entity.getDownload_url();
						System.out.println("-------------"+downloadUri);
					} else if(type == 1){
						//TODO处理段子类型
						JSONObject group = item.getJSONObject("group");
						int cid = group.getInt("category_id");
						TextEntity entity = null;
						if(cid == 1){
							//TODO解析文本段子
							entity = new TextEntity();
						} else if (cid == 2) {
							//TODO解析图片段子
							entity = new ImageEntity();
						}
						entity.parseJson(item);
						
						entites.add(entity);
						System.out.println("---------------"+entity.getGroupId());
					}
				}
			}
		}
	}

	public boolean isHas_more() {
		return has_more;
	}

	public long getMin_time() {
		return min_time;
	}

	public String getTip() {
		return tip;
	}

	public long getMax_time() {
		return max_time;
	}

	public List<TextEntity> getEntites() {
		return entites;
	}
	
}
