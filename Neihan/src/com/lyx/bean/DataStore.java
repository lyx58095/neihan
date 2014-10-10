package com.lyx.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 段子类表数据存储区
 * @author lenovo
 *
 */
public class DataStore {

	private static DataStore outInstance;
	
	public static DataStore getInstance(){
		if(outInstance == null){
			outInstance = new DataStore();
		}
		return outInstance;
	}
	
	private List<TextEntity> textEntities;
	private List<TextEntity> imageEntities;
	
	public List<TextEntity> getTextEntities() {
		return textEntities;
	}

	public List<TextEntity> getImageEntities() {
		return imageEntities;
	}

	private DataStore() {
		// TODO Auto-generated constructor stub
		textEntities = new ArrayList<TextEntity>();
		imageEntities = new ArrayList<TextEntity>();
	}

	/**
	 * 把获取到的文本段子列表放到最前面 ，这个方法针对是下拉刷新操作
	 * @param entities
	 */
	public void addTextEntities(List<TextEntity> entities){
		if(entities != null){
			textEntities.addAll(0, entities);
		}
	}
	
	/**
	 * 把获取到的文本段子列表放到最后面，这个方法针对的是上拉查看旧数据的操作
	 * @param entities
	 */
	public void appendTextEntities(List<TextEntity> entities){
		if(entities != null){
			textEntities.addAll(entities);
		}
	}
	
	/**
	 * 把获取到的文本段子列表放到最前面 ，这个方法针对是下拉刷新操作
	 * @param entities
	 */
	public void addImageEntities(List<TextEntity> entities){
		if(entities != null){
			imageEntities.addAll(0, entities);
		}
	}
	
	/**
	 * 把获取到的文本段子列表放到最后面，这个方法针对的是上拉查看旧数据的操作
	 * @param entities
	 */
	public void appendImageEntities(List<TextEntity> entities){
		if(entities != null){
			imageEntities.addAll(entities);
		}
	}
}
