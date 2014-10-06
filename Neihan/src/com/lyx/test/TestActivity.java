package com.lyx.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.lyx.bean.AdEntity;
import com.lyx.bean.CommentList;
import com.lyx.bean.EntityList;
import com.lyx.bean.ImageEntity;
import com.lyx.bean.ImageUrlList;
import com.lyx.bean.TextEntity;
import com.lyx.client.ClientAPI;
import com.lyx.neihan.R;

/**
 * 这个文件就是一个测试入口，所有的测试内容，都可以放在这里
 * 
 * @author lenovo
 * 
 */
public class TestActivity extends Activity implements Response.Listener<String> {

	// 分类ID，1代表文本，2代表图片
	public static final int CATEGORY_TEXT = 1;
	public static final int CATEGORY_IMAGE = 2;
	private RequestQueue queue;
	private Button button;
	private long lastTime = 0;
	private int offset = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		button = (Button) this.findViewById(R.id.button1);
		queue = Volley.newRequestQueue(this);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ClientAPI.getList(queue, CATEGORY_TEXT, 30, lastTime,
				//		TestActivity.this);
				ClientAPI.getComments(queue, 3550036269L, offset, TestActivity.this);
			}
		});
		//long groupId = 1409991087;
		
		
	}

	@Override
	public void onResponse(String arg0) {
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject(arg0);
			arg0 = json.toString(4);
			System.out.println(arg0);
			CommentList commentList = new CommentList();
			commentList.parseJson(json);

			long groupId = commentList.getGroupId();
			boolean hasMore = commentList.isHasMore();

			System.out.println(groupId);
			System.out.println(hasMore);
			
			//TODO 直接把 CommentList 提交给 ListView 的 Adapter
			//利用Adapter更新数据
			
			//分页标识，要求服务器每次返回20条评论，通过 hasMore 来进行判断是否还需要分页
			offset += 20;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	/**
	 * 列表网络获取回调部分
	 * 
	 * @param arg0
	 *            列表JSON数据字符串
	 */
	public void listonResponse(String arg0) {
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject(arg0);
			// arg0 = json.toString(4);
			// System.out.println(arg0);

			// 获取根节点下的data对象
			JSONObject obj = json.getJSONObject("data");

			// 解析段子列表的完整数据
			EntityList entityList = new EntityList();
			// 这个方法是解析JSON的方法，其中包含的支持 图片、文本、广告的解析
			entityList.parseJson(obj); // 相当于获取数据内容

			// 如果 isHasMore 返回 true ，代表还可以更新一次数据
			if (entityList.isHas_more()) {
				lastTime = entityList.getMin_time(); // 获取更新时间标识
			} else { // 没有更多数据了，休息一会儿
				System.out.println(entityList.getTip());
			}

			// 获取段子内容列表
			// List<TextEntity> entities = entityList.getEntites();
			// TODO 把entityList 这个段子的数据集合体，传递给 ListView 之类的 Adapter 即可显示
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
