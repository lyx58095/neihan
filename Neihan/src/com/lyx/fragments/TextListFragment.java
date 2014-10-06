package com.lyx.fragments;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lyx.neihan.R;

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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TextListFragment extends Fragment implements OnClickListener,OnScrollListener,OnRefreshListener2<ListView>{

	private LinearLayout quickTools;
	private TextView textNotify;
	private int lastIndex = 0;
	private View header;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == 1){
				textNotify.setVisibility(View.INVISIBLE);
			}
		}
	};
	public TextListFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
		List<String> strings = new ArrayList<String>();
		
		strings.add("java");
		strings.add("java");
		strings.add("java");
		strings.add("java");
		strings.add("java");
		strings.add("java");
		strings.add("java");
		strings.add("java");
		strings.add("java");
		strings.add("java");
		strings.add("java");
		strings.add("java");
		strings.add("java");
		strings.add("java");
		strings.add("java");
		
		header = inflater.inflate(R.layout.textlist_header_tools, listView, false);
		listView.addHeaderView(header);
		
		View quickPublish = header.findViewById(R.id.quick_tools_publish);
		quickPublish.setOnClickListener(this);
		
		View quickReview = header.findViewById(R.id.quick_tools_review);
		quickReview.setOnClickListener(this);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strings);
		
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
	 * 从上向下拉动列表，那么就要进行加载新数据的操作
	 */
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 从下向上拉动，那么就要考虑是否进行加载旧的数据
	 */
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}
}
