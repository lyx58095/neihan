package com.lyx.fragments;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lyx.adapters.EssayAdapter;
import com.lyx.bean.DataStore;
import com.lyx.bean.EntityList;
import com.lyx.bean.TextEntity;
import com.lyx.client.ClientAPI;
import com.lyx.neihan.EssayDetailActivity;
import com.lyx.neihan.R;

/**
 * 1.列表界面，第一次启动，并且数据为空的时候，自动加载数据
 * 2.只要列表没有数据，进入这个界面的时候，就尝试加载数据
 * 3.只要有数据，就不进行数据的加载
 * 4.进入这个界面，并且有数据的情况下，尝试检查新信息的个数
 * @author lenovo
 *
 */
public class TextListFragment extends Fragment implements OnClickListener,OnScrollListener,OnRefreshListener2<ListView>, OnItemClickListener{

	private LinearLayout quickTools;
	private TextView textNotify;
	private int lastIndex = 0;
	private View header;
	private EssayAdapter adapter;
	//private List<TextEntity> entities;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == 1){
				textNotify.setVisibility(View.INVISIBLE);
			}
		}
	};
	
	// 分类ID，1代表文本，2代表图片
	public static final int CATEGORY_TEXT = 1;
	public static final int CATEGORY_IMAGE = 2;
	private RequestQueue queue;
	private long lastTime = 0;
	
	private int requestCategory = CATEGORY_TEXT;
	public TextListFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(queue == null){
			queue = Volley.newRequestQueue(getActivity());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(savedInstanceState != null){
			lastTime = savedInstanceState.getLong("lastTime");
		}
		
		View view = inflater.inflate(R.layout.fragment_text, container, false);
		
		//获取标题控件，增加点击，进行新消息悬浮框显示的功能
		
		View titleView = view.findViewById(R.id.textlist_title);
		titleView.setOnClickListener(this);
		//TODO 获取ListView并且设置数据
		
		PullToRefreshListView refreshListView = (PullToRefreshListView)view.findViewById(R.id.textlist_listview);
		
		//设置上拉与下拉的时间监听
		refreshListView.setOnRefreshListener(this);
		
		refreshListView.setMode(Mode.BOTH);
		ListView listView = refreshListView.getRefreshableView();
		listView.setOnItemClickListener(this);
		
		header = inflater.inflate(R.layout.textlist_header_tools, listView, false);
		listView.addHeaderView(header);
		
		View quickPublish = header.findViewById(R.id.quick_tools_publish);
		quickPublish.setOnClickListener(this);
		
		View quickReview = header.findViewById(R.id.quick_tools_review);
		quickReview.setOnClickListener(this);
		
		List<TextEntity> entities = DataStore.getInstance().getTextEntities();
		
//		if(entities == null){
//			entities = new ArrayList<TextEntity>();
//		}
		adapter = new EssayAdapter(getActivity(), entities);
		adapter.setListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v instanceof TextView){
					String string = (String)v.getTag();
					if(string !=null){
						int position = Integer.parseInt(string);
						Intent intent = new Intent(getActivity(), EssayDetailActivity.class);
						intent.putExtra("currentEssayPosition",position);
						intent.putExtra("category",requestCategory);
						startActivity(intent);
					}
				}
			}
		});
		listView.setAdapter(adapter);
		
		listView.setOnScrollListener(this);
		
		//TODO 获取快速的工具条（发布和审核），用于列表滚动的显示和隐藏
		
		quickTools = (LinearLayout)view.findViewById(R.id.textlist_quick_tools);
		quickTools.setVisibility(View.INVISIBLE);
		
		quickPublish = quickTools.findViewById(R.id.quick_tools_publish);
		quickPublish.setOnClickListener(this);
		
		quickReview = quickTools.findViewById(R.id.quick_tools_review);
		quickReview.setOnClickListener(this);
		
		//TODO 获取新条目通知控件，每次有新数据要显示，过一段时间隐藏
		textNotify = (TextView)view.findViewById(R.id.textlist_new_notify);
		textNotify.setVisibility(View.INVISIBLE);
		
		return view;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
		outState.putLong("lastTime", lastTime);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		
		this.adapter = null;
		this.header = null;
		this.quickTools = null;
		this.textNotify = null;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.textlist_title:
			textNotify.setVisibility(View.VISIBLE);
			handler.sendEmptyMessageDelayed(1, 3000);
			break;
		}
	}

	
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		int offset = lastIndex - firstVisibleItem;
		if(offset < 0 || firstVisibleItem == 0){
			//证明现在移动是向上移动
			if(quickTools != null){
				quickTools.setVisibility(View.INVISIBLE);
			}
		} else if (offset > 0) {
			if(quickTools != null){
				quickTools.setVisibility(View.VISIBLE);
				
//				if(header.getVisibility() == View.VISIBLE){
//					header.setVisibility(View.INVISIBLE);
//				}
			}
		}
		lastIndex = firstVisibleItem;
	}

	//列表下拉刷新与上拉加载
	
	/**
	 * 列表网络获取回调部分,这个方法，是在Volley联网响应返回的时候调用，
	 * 它是在主线程执行的，因此可以直接更新UI
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
			// TODO 把entityList 这个段子的数据集合体，传递给 ListView 之类的 Adapter 即可显示
			List<TextEntity> ets = entityList.getEntites();
			if(ets!=null){
				if(!ets.isEmpty()){
					
					DataStore.getInstance().addTextEntities(ets);
					
//					entities.addAll(0, ets);
					
					//手动添加
//					int len = ets.size();
//					for(int index = len-1;index>=0;index--){
//						//把object添加到指定的location位置，原有location以及以后的内容添加在后面
//						entities.add(0, ets.get(index));
//					}
					
					adapter.notifyDataSetChanged();
				} else {
					//TODO 没有更多的数据了，需要提示一下
				}
			} else {
				//TODO 没有获取到网络数据，可能是数据解析错误，网络出错，需要提示一下
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 从上向下拉动列表，那么就要进行加载新数据的操作
	 */
	@Override
	public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		ClientAPI.getList(queue, requestCategory, 30, lastTime, new Response.Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				// TODO 加载新数据
				refreshView.onRefreshComplete();
				listonResponse(arg0);
			}
		});
	}

	/**
	 * 从下向上拉动，那么就要考虑是否进行加载旧的数据
	 */
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		position--;
		Intent intent = new Intent(getActivity(), EssayDetailActivity.class);
		intent.putExtra("currentEssayPosition",position);
		intent.putExtra("category",requestCategory);
		startActivity(intent);
		
	}
}
