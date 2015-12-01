package com.android.photolist.activity;

import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;

import com.android.photolist.ImageAdapter;
import com.android.photolist.ImageSwitcherViewFactory;
import com.android.photolist.R;
import com.android.photolist.db.BlobDAL;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageSwitcher;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class PhotoShowActivity extends ActionBarActivity implements OnItemSelectedListener {
	private ImageSwitcher mSwitcher;
	private List<Bitmap> imageRes = new ArrayList<Bitmap>();
	BlobDAL mBlobDAL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mBlobDAL = new BlobDAL(this);
		
		imageRes = mBlobDAL.readImg();
		
    setContentView(R.layout.activity_photo_show);
		setTitle("图片列表");
		
		mSwitcher = (ImageSwitcher) findViewById(R.id.switcher1);
		mSwitcher.setFactory(new ImageSwitcherViewFactory(this));
		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_fade_in));
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_fade_out));
		
		Gallery g = (Gallery) findViewById(R.id.gallery1);
		g.setAdapter(new ImageAdapter(this, imageRes));
		g.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_show, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Bitmap bmp = imageRes.get(position);
		BitmapDrawable bmpd = new BitmapDrawable(bmp);
		mSwitcher.setImageDrawable(bmpd);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
