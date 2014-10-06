package com.lyx.bean;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 文本段子实体
 * @author lenovo
 *
 */
public class TextEntity {

	private int type;
	private long createTime;
	private int favoriteCount;
	/**
	 * 代表当前用户是否踩了，0代表没有，1代表踩了
	 */
	private int userBury;
	private int userFavorite;
	private int buryCount;
	//用于第三方分享，提交的网址参数
	private String shareUrl;
	//TODO 分析这个字段的含义
	private int label;
	private String content;
	private int commentCount;
	/**
	 * 状态，其中的可选值3，需要分析是什么类型？
	 */
	private int status;
	/**
	 * 状态描述信息
	 */
	private String statusDesc;
	private int hasComments;
	//TODO 需要分析这个字段的含义
	private int goDetailCount;
	//TODO 需要分析这个字段的含义
	private int userDigg;
	//digg个数
	private int diggCount;
	//段子的Id，访问详情和评论时，用这个作为接口的参数
	private long groupId;
	//TODO 需要分析这个字段的含义
	private int level;
	//TODO 需要分析这个字段的含义
	private int repinCount;
	//代表用户是否repin，0代表没有
	private int userRepin;
	//是否含有热门评论
	private int hasHotComments;
	//内容分类类型，1文本，2图片
	private int categoryId;
	//TODO 需要分析comments这个JSON数组中的内容是什么？
	private long onlingTime;
	private long displayTime;
	private UserEntity user;
	
	public int getType() {
		return type;
	}

	public long getCreateTime() {
		return createTime;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public int getUserBury() {
		return userBury;
	}

	public int getUserFavorite() {
		return userFavorite;
	}

	public int getBuryCount() {
		return buryCount;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public int getLabel() {
		return label;
	}

	public String getContent() {
		return content;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public int getHasComments() {
		return hasComments;
	}

	public int getGoDetailCount() {
		return goDetailCount;
	}

	public int getUserDigg() {
		return userDigg;
	}

	public int getDiggCount() {
		return diggCount;
	}

	public long getGroupId() {
		return groupId;
	}

	public int getLevel() {
		return level;
	}

	public int getRepinCount() {
		return repinCount;
	}

	public int getUserRepin() {
		return userRepin;
	}

	public int getHasHotComments() {
		return hasHotComments;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public long getOnlingTime() {
		return onlingTime;
	}

	public long getDisplayTime() {
		return displayTime;
	}

	public UserEntity getUser() {
		return user;
	}

	public void parseJson(JSONObject json) throws JSONException{
		if(json!=null){
			onlingTime = json.getLong("online_time");
			displayTime = json.getLong("display_time");
			JSONObject group = json.getJSONObject("group");
			createTime = group.getLong("create_time");
			favoriteCount = group.getInt("favorite_count");
			userBury = group.getInt("user_bury");
			userFavorite = group.getInt("user_favorite");
			buryCount = group.getInt("bury_count");
			shareUrl = group.getString("share_url");
			label = group.optInt("label",0);
			content = group.getString("content");
			commentCount = group.getInt("comment_count");
			status = group.getInt("status");
			hasComments = group.getInt("has_comments");
			goDetailCount = group.getInt("go_detail_count");
			statusDesc = group.getString("status_desc");
			user = new UserEntity();
			user.parseJson(group.getJSONObject("user"));
			userDigg = group.getInt("user_digg");
			groupId = group.getLong("group_id");
			level = group.getInt("level");
			repinCount = group.getInt("repin_count");
			diggCount = group.getInt("digg_count");
			hasHotComments = group.optInt("has_hot_comments",0);
			userRepin = group.getInt("user_repin");
			categoryId = group.getInt("category_id");
			type = json.getInt("type");
		}
	}
	/*
	 * {
                "online_time": 1411878957,
                "display_time": 1411878957,
                "group": {
                    "create_time": 1411718218.0,
                    "favorite_count": 1209,
                    "user_bury": 0,
                    "user_favorite": 0,
                    "bury_count": 1516,
                    "share_url": "http://toutiao.com/group/3560859160/?iid=2337593504&app=joke_essay",
                    "label": 1,
                    "content": "甲:昨天碰到抢劫的，被打了两顿。乙:为啥啊？甲:他问我有钱吗我说没有，他从我身上搜出一包软中华然后就被打了一顿。等他走了，不一会儿又回来打了我一顿，因为他发现里面塞的是白红梅，劫匪走时还留下一句‘没钱还装B’",
                    "comment_count": 177,
                    "status": 3,
                    "has_comments": 0,
                    "go_detail_count": 4370,
                    "status_desc": "已发表到热门列表",
                    "user": {
                        "avatar_url": "http://p1.pstatp.com/thumb/1367/2213311454",
                        "user_id": 3080520868,
                        "name": "请叫我梓安哥",
                        "user_verified": false
                    },
                    "user_digg": 0,
                    "group_id": 3560859160,
                    "level": 4,
                    "repin_count": 1209,
                    "digg_count": 18424,
                    "has_hot_comments": 1,
                    "user_repin": 0,
                    "category_id": 1
                },
                "comments": [],
                "type": 1
            },
	 */
}
