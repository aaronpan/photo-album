package com.android.photolist.activity;

import android.support.v7.app.ActionBarActivity;

import com.android.photolist.R;
import com.android.photolist.db.BlobDAL;
import com.android.photolist.helper.CameraHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {
  CameraHelper mCameraHelper;
	BlobDAL mBlobDAL;
	
  @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button show_photo_btn = (Button) findViewById(R.id.show_photo);
		show_photo_btn.setOnClickListener(show_photo_button_listener);
		Button take_photo_btn = (Button) findViewById(R.id.take_photo);
		take_photo_btn.setOnClickListener(take_photo_button_listener);
		Button delete_images_btn = (Button) findViewById(R.id.delete_photos);
		delete_images_btn.setOnClickListener(delete_photos_button_listener);
		
		mBlobDAL = new BlobDAL(this);
		mCameraHelper = new CameraHelper(this, mBlobDAL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	private Button.OnClickListener show_photo_button_listener = new Button.OnClickListener(){
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, PhotoShowActivity.class);
			startActivity(intent);
		}
	};
	
	private Button.OnClickListener take_photo_button_listener = new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			mCameraHelper.OnOpenCamera();
		}
		
	};
	
	private Button.OnClickListener delete_photos_button_listener = new Button.OnClickListener(){
		@Override
		public void onClick(View v) {
			mBlobDAL.deleteImgs();
		}
		
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mCameraHelper.HandleonActivityResult(requestCode, resultCode, data);
	};
}
