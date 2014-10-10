package com.lyx.adapters;

import java.util.Date;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import com.lyx.bean.Comment;
import com.lyx.neihan.R;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

	private Context context;
	private List<Comment> comments;
	private LayoutInflater inflater;
	public CommentAdapter(Context context, List<Comment> comments) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.comments = comments;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return comments.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View ret = convertView;
		if(convertView==null){
			ret = inflater.inflate(R.layout.item_comment, parent, false);
		}
		
		if(ret != null){
			ViewHolder holder = (ViewHolder)ret.getTag();
			if(holder == null){
				holder = new ViewHolder();
				holder.chbDigg = (CheckBox)ret.findViewById(R.id.comment_digg_count);
				holder.profileImage = (ImageView)ret.findViewById(R.id.comment_profile_image);
				holder.txtNick = (TextView)ret.findViewById(R.id.comment_profile_nick);
				holder.txtCreateTime = (TextView)ret.findViewById(R.id.comment_create_time);
				holder.txtContent = (TextView)ret.findViewById(R.id.comment_content);
				ret.setTag(holder);
			}
			Comment comment = comments.get(position);
			holder.txtNick.setText(comment.getUser_name());
			Date date = new Date(comment.getCreate_time());
			holder.txtCreateTime.setText(date.toString());
			holder.txtContent.setText(comment.getText());
			
			int diggCount = comment.getDigg_count();
			holder.chbDigg.setText(Integer.toString(diggCount));
			int userDigg = comment.getUser_digg();
			holder.chbDigg.setEnabled(userDigg == 0);
		}
		
		return ret;
	}

	private static class ViewHolder{
		public ImageView profileImage;
		public TextView txtNick;
		public TextView txtCreateTime;
		public TextView txtContent;
		public CheckBox chbDigg;
	}
}
