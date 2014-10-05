package com.lyx.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.lyx.bean.ImageEntity;
import com.lyx.bean.ImageUrlList;
import com.lyx.client.ClientAPI;
import com.lyx.neihan.R;

/**
 * 这个文件就是一个测试入口，所有的测试内容，都可以放在这里
 * 
 * @author lenovo
 * 
 */
public class TestActivity extends Activity implements Response.Listener<String>{

	// 分类ID，1代表文本，2代表图片
	public static final int CATEGORY_TEXT = 1;
	public static final int CATEGORY_IMAGE = 2;
	private RequestQueue queue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		queue = Volley.newRequestQueue(this);
		ClientAPI.getList(queue, CATEGORY_IMAGE, 30, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	@Override
	public void onResponse(String arg0) {
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject(arg0);
			arg0 = json.toString(4);
			System.out.println(arg0);
			
			//获取根节点下的data对象
			JSONObject obj = json.getJSONObject("data");
			//从data对象中，获取名称为data的数组，它代表的是段子列表的数据
			JSONArray array = obj.getJSONArray("data");
			
			int len = array.length();
			
			if(len>0)
			{
				//遍历数组中的每一条图片段子信息
				for(int i=0;i<len;i++)
				{
					JSONObject item = array.getJSONObject(i);
					ImageEntity imageEntity = new ImageEntity();
					imageEntity.parseJson(item);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
