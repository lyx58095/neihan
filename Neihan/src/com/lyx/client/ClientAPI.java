package com.lyx.client;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lyx.test.TestActivity;

/**
 * 所有和服务器接口对接的方法，全部在这个类里面
 * @author lenovo
 *
 */
public class ClientAPI {

	public ClientAPI() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取内涵段子列表内容
	 * @param queue Volley请求的队列
	 * @param categoryType 要获取的参数类型
	 * @param itemCount 服务器一次传回来的条目数
	 * @param responseListener 用于获取段子列表的回调接口，任何调用getList方法时，此参数用于更新段子列表
	 * @see TestActivity#CATEGORY_TEXT
	 * @see TestActivity#CATEGORY_IMAGE
	 */
	public static void getList(RequestQueue queue, int categoryType, int itemCount, Response.Listener<String> responseListener) {
		// 测试内涵段子列表接口，获取文本列表
	
		String CATEGORY_LIST_API = "http://ic.snssdk.com/2/essay/zone/category/data/";
	
		// 分类参数，根据类型获取不同的数据
		String categoryParam = "category_id=" + categoryType;
		String countParam = "count=" + itemCount;
		String deviceType = "device_type=KFTT";
		String openUDID = "openudid=b90ca6a3a19a78d6";
		String url = CATEGORY_LIST_API
				+ "?"
				+ categoryParam
				+ "&"
				+ countParam
				+ "&"
				+ deviceType
				+ "&"
				+ openUDID
				+ "&level=6&iid=2337593504&device_id=2757969807&ac=wifi&channel=wandoujia&aid=7&app_name=joke_essay&version_code=302&device_platform=android&os_api=15&os_version=4.0.3";
	
		queue.add(new StringRequest(Request.Method.GET, url,
				responseListener, new Response.ErrorListener() {
	
					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
	
					}
				}));
	}

}
