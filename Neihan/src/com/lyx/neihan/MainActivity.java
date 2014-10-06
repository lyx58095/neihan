package com.lyx.neihan;

import java.util.ArrayList;
import java.util.List;

import com.lyx.fragments.HuodongFragment;
import com.lyx.fragments.ImageListFragment;
import com.lyx.fragments.MineFragment;
import com.lyx.fragments.ReviewFragment;
import com.lyx.fragments.TextListFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener{

	private RadioGroup radioGroup;
	private List<Fragment> fragments;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		radioGroup = (RadioGroup)this.findViewById(R.id.main_tab_bar);
		radioGroup.setOnCheckedChangeListener(this);
		
		fragments = new ArrayList<Fragment>();
		fragments.add(new TextListFragment());
		fragments.add(new ImageListFragment());
		fragments.add(new ReviewFragment());
		fragments.add(new HuodongFragment());
		fragments.add(new MineFragment());
		
		Fragment fragment = fragments.get(0);
		
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.main_fragment_container, fragment);
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		int childCount = group.getChildCount();
		int checkedIndex = 0;
		RadioButton btn = null;
		for(int i=0;i<childCount;i++){
			btn = (RadioButton)group.getChildAt(i);
			if(btn.isChecked()){
				checkedIndex = i;
				break;
			}
		}
		
		Fragment fragment = fragments.get(checkedIndex);
		
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.main_fragment_container, fragment);
		transaction.commit();
//		switch (checkedIndex) {
//		case 0:
//			
//			break;
//		case 1:
//			
//			break;
//		case 2:
//			
//			break;
//		case 3:
//			
//			break;
//		case 4:
//			
//			break;
//		}
	}

}
