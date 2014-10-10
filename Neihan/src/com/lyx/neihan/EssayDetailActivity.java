package com.lyx.neihan;

import java.util.ArrayList;
import java.util.List;

import com.lyx.adapters.DetailPagerAdapter;
import com.lyx.bean.DataStore;
import com.lyx.bean.TextEntity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class EssayDetailActivity extends FragmentActivity {

	private ViewPager viewPager;
	private DetailPagerAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_essay_detail);
		
		viewPager = (ViewPager)this.findViewById(R.id.detail_pager_content);
		//设置FragmentPagerAdapter
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		int category = 1;
		int currentEssayPosition = 0;
		if(extras != null){
			category = extras.getInt("category", 1);
			currentEssayPosition = extras.getInt("currentEssayPosition", 0);
		}
		DataStore dataStore = DataStore.getInstance();
		
		List<TextEntity> entities = null;
		
		if(category ==1){
			entities = dataStore.getTextEntities();
		} else if (category == 2) {
			entities = dataStore.getImageEntities();
		}
		adapter = new DetailPagerAdapter(getSupportFragmentManager(), entities);
		viewPager.setAdapter(adapter);
		
		if(currentEssayPosition > 0){
			viewPager.setCurrentItem(currentEssayPosition);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.essay_detail, menu);
		return true;
	}

}
