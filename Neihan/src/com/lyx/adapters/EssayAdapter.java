package com.lyx.adapters;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import com.lyx.bean.TextEntity;
import com.lyx.bean.UserEntity;
import com.lyx.neihan.R;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class EssayAdapter extends BaseAdapter {

	private Context context;
	private List<TextEntity> entities;
	private LayoutInflater inflater;
	private OnClickListener listener;
	
	public EssayAdapter(Context context, List<TextEntity> entities) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.entities = entities;
		inflater = LayoutInflater.from(context);
	}

	public void setListener(OnClickListener listener) {
		this.listener = listener;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entities.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return entities.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View ret = convertView;
		if(convertView == null){
			ret = inflater.inflate(R.layout.item_essay, parent, false);
		}
		
		if(ret != null){
			
			ViewHolder holder = (ViewHolder)ret.getTag();
			
			if(holder == null){
				holder = new ViewHolder();
				holder.btnGifPlay = (TextView)ret.findViewById(R.id.btnGifPlay);
				holder.btnShare = (ImageButton)ret.findViewById(R.id.item_share);
				holder.chbBuryCount = (CheckBox)ret.findViewById(R.id.item_bury_count);
				holder.chbDiggCount = (CheckBox)ret.findViewById(R.id.item_digg_count);
				holder.gifImageView = (GifImageView)ret.findViewById(R.id.gifView);
				holder.imgProfileImage = (ImageView)ret.findViewById(R.id.item_profile_image);
				holder.pbDownloadProgress = (ProgressBar)ret.findViewById(R.id.item_image_download_progress);
				holder.txtCommentCount = (TextView)ret.findViewById(R.id.item_comment_count);
				holder.txtContent = (TextView)ret.findViewById(R.id.item_content);
				holder.txtProfileNick = (TextView)ret.findViewById(R.id.item_profile_nick);
				ret.setTag(holder);
			}
			
			TextEntity entity = entities.get(position);
			 
			//1.先设置文本的内容数据
			
			UserEntity user = entity.getUser();
			String nick = "";
			if(user != null){
				nick = user.getName();
			}
			holder.txtProfileNick.setText(nick);
			
			String content = entity.getContent();
			holder.txtContent.setText(content);
			holder.txtContent.setOnClickListener(listener);
			holder.txtContent.setTag(Integer.toString(position));
			
			int diggCount = entity.getDiggCount();
			holder.chbDiggCount.setText(Integer.toString(diggCount));
			
			int userDigg = entity.getUserDigg(); //当前用户是否赞过
			//如果userDigg是1的话，代表了已经赞过，那么chbDiggCount必须禁用，所以用 !=1
			holder.chbDiggCount.setEnabled(userDigg != 1);
			
			int buryCount = entity.getBuryCount();
			holder.chbBuryCount.setText(Integer.toString(buryCount));
			
			int userBury = entity.getUserBury();
			holder.chbBuryCount.setEnabled(userBury != 1);
			
			int commentCount = entity.getCommentCount();
			holder.txtCommentCount.setText(Integer.toString(commentCount));
			
			//2.设置图片的数据
			
			//TODO 需要加载各种图片数据
		}
		
		return ret;
	}

	private static class ViewHolder{
		
		public ImageView imgProfileImage;
		public TextView txtProfileNick;
		public TextView txtContent;
		public ProgressBar pbDownloadProgress;
		public GifImageView gifImageView;
		public TextView btnGifPlay;
		public CheckBox chbDiggCount;
		public CheckBox chbBuryCount;
		public TextView txtCommentCount;
		public ImageButton btnShare;
		
	}
}
