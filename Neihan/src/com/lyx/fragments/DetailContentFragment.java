package com.lyx.fragments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.lyx.adapters.CommentAdapter;
import com.lyx.bean.Comment;
import com.lyx.bean.CommentList;
import com.lyx.bean.TextEntity;
import com.lyx.bean.UserEntity;
import com.lyx.client.ClientAPI;
import com.lyx.neihan.R;

import android.R.integer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailContentFragment extends Fragment implements OnTouchListener, Listener<String> {

	private TextEntity entity;
	private ScrollView scrollView;
	private TextView txtHotCommentCount;
	private TextView txtRecentCommentCount;
	private RequestQueue queue;
	public DetailContentFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(entity == null){
			Bundle arguments = getArguments();
			Serializable serializable = arguments.getSerializable("entity");
			if(serializable instanceof TextEntity){
				entity = (TextEntity)serializable;
			}
		}
		
		queue = Volley.newRequestQueue(getActivity());
	}
	
	private CommentAdapter hotAdapter;
	private CommentAdapter recentAdapter;
	private List<Comment> hotComments;
	private List<Comment> recentComments;
	int offset = 0;
	private long groupId;
	private boolean hasMore;
	private LinearLayout linearLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View ret = inflater.inflate(R.layout.fragment_detail_content, container, false);
		scrollView = (ScrollView)ret.findViewById(R.id.detail_scroll);
		scrollView.setOnTouchListener(this);
		linearLayout = (LinearLayout)ret.findViewById(R.id.scroll_content);
		
		setEssayContent(ret);
		
		txtHotCommentCount = (TextView)ret.findViewById(R.id.txt_hot_comments_count);
		hotCommentListView = (ListView)ret.findViewById(R.id.hot_comments_list);
		
		hotComments = new ArrayList<Comment>();
		hotAdapter = new CommentAdapter(getActivity(), hotComments);
		hotCommentListView.setAdapter(hotAdapter);
		
		txtRecentCommentCount = (TextView)ret.findViewById(R.id.txt_recent_comments_count);
		recentCommentListView = (ListView)ret.findViewById(R.id.recent_comments_list);
		
		recentComments = new ArrayList<Comment>();
		recentAdapter = new CommentAdapter(getActivity(), recentComments);
		recentCommentListView.setAdapter(recentAdapter);
		
		groupId = entity.getGroupId();
		ClientAPI.getComments(queue, groupId, 0, this);
		
		return ret;
	}

	/**
	 * 设置段子主体内容
	 * @param ret
	 */
	public void setEssayContent(View ret) {
		//1.先设置文本的内容数据
		TextView btnGifPlay = (TextView)ret.findViewById(R.id.btnGifPlay);
		ImageButton btnShare = (ImageButton)ret.findViewById(R.id.item_share);
		CheckBox chbBuryCount = (CheckBox)ret.findViewById(R.id.item_bury_count);
		CheckBox chbDiggCount = (CheckBox)ret.findViewById(R.id.item_digg_count);
		GifImageView gifImageView = (GifImageView)ret.findViewById(R.id.gifView);
		ImageView imgProfileImage = (ImageView)ret.findViewById(R.id.item_profile_image);
		ProgressBar pbDownloadProgress = (ProgressBar)ret.findViewById(R.id.item_image_download_progress);
		TextView txtCommentCount = (TextView)ret.findViewById(R.id.item_comment_count);
		TextView txtContent = (TextView)ret.findViewById(R.id.item_content);
		TextView txtProfileNick = (TextView)ret.findViewById(R.id.item_profile_nick);
		
		UserEntity user = entity.getUser();
		String nick = "";
		if(user != null){
			nick = user.getName();
		}
		txtProfileNick.setText(nick);
		
		String content = entity.getContent();
		txtContent.setText(content);
		
		int diggCount = entity.getDiggCount();
		chbDiggCount.setText(Integer.toString(diggCount));
		
		int userDigg = entity.getUserDigg(); //当前用户是否赞过
		//如果userDigg是1的话，代表了已经赞过，那么chbDiggCount必须禁用，所以用 !=1
		chbDiggCount.setEnabled(userDigg != 1);
		
		int buryCount = entity.getBuryCount();
		chbBuryCount.setText(Integer.toString(buryCount));
		
		int userBury = entity.getUserBury();
		chbBuryCount.setEnabled(userBury != 1);
		
		int commentCount = entity.getCommentCount();
		txtCommentCount.setText(Integer.toString(commentCount));
		
		//2.设置图片的数据
		
		//TODO 需要加载各种图片数据
	}

	private boolean hasMove = false;
	private ListView recentCommentListView;
	private ListView hotCommentListView;
	
	/**
	 * 处理ScrollView 触摸事件，用于在ScrollView滚动到最下面的时候，自动加载数据
	 * @param v
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		boolean bret = false;
		int action = event.getAction();
		if(action == MotionEvent.ACTION_DOWN){
			bret = true;
			hasMove = false;
		} else if (action == MotionEvent.ACTION_MOVE) {
			hasMove = true;
		} else if (action == MotionEvent.ACTION_UP) {
			if(hasMove){
				int sy = scrollView.getScrollY();
				int mh = scrollView.getMeasuredHeight();
				int ch = linearLayout.getHeight();
				
				if (sy + mh >= ch) {
					ClientAPI.getComments(queue, groupId, offset, this);
				}
			}
		}
		return bret;
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

			hasMore = commentList.isHasMore();
			
			List<Comment> topComments = commentList.getTopComments();
			List<Comment> rtComments = commentList.getRecentComments();
			
			if(topComments != null){
				hotComments.addAll(topComments);
				hotAdapter.notifyDataSetChanged();
			}
			
			if(rtComments != null){
				recentComments.addAll(rtComments);
				recentAdapter.notifyDataSetChanged();
			}

			//TODO 直接把 CommentList 提交给 ListView 的 Adapter
			//利用Adapter更新数据
			
			//分页标识，要求服务器每次返回20条评论，通过 hasMore 来进行判断是否还需要分页
			offset += 20;
			
//			hotCommentListView.requestLayout();
//			recentCommentListView.requestLayout();
//
//			int childCount = hotCommentListView.getChildCount();
//			if(childCount > 0){
//				int totalHeight = 0;
//				for(int i = 0;i<childCount;i++){
//					View view = hotCommentListView.getChildAt(i);
//					totalHeight += view.getHeight();
//				}
//				ViewGroup.LayoutParams ps = hotCommentListView.getLayoutParams();
//				ps.height = totalHeight;
//				hotCommentListView.setLayoutParams(ps);
//			}
//			
//			childCount = recentCommentListView.getChildCount();
//			if(childCount > 0){
//				int totalHeight = 0;
//				for(int i = 0;i<childCount;i++){
//					View view = recentCommentListView.getChildAt(i);
//					totalHeight += view.getHeight();
//				}
//				ViewGroup.LayoutParams ps = recentCommentListView.getLayoutParams();
//				ps.height = totalHeight;
//				recentCommentListView.setLayoutParams(ps);
//			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
