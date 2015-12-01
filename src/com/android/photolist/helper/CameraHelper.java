package com.android.photolist.helper;

import com.android.photolist.activity.MainActivity;
import com.android.photolist.db.BlobDAL;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Display;
// can kao: http://www.cnblogs.com/vir56k/archive/2012/10/24/2737119.html
// http://blog.csdn.net/zjbpku/article/details/8877524
public class CameraHelper {
	MainActivity mContext;
	Uri photoUri;
	public static final int REQUEST_CODE_camera = 2222;
	BlobDAL mBlobDAL;	
	
	public CameraHelper(MainActivity ctx, BlobDAL dal) {
		mBlobDAL = dal;
		mContext = ctx;
	}
	
	public void OnOpenCamera(){
		ContentValues values = new ContentValues();
		// 向 MediaStore.Images.Media.EXTERNAL_CONTENT_URI 插入数据，返回标识 ID
		photoUri = mContext.getContentResolver().insert(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定新照片的文件名
		// 在拍完照以后，新的照片会以此处的 photoUri 命名
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
		
		// 启动拍照的窗体，并注册回调处理
		mContext.startActivityForResult(intent, REQUEST_CODE_camera);
	}
	
	public void HandleonActivityResult(int requestCode, int resultCode,
      Intent data) {
		if (requestCode == REQUEST_CODE_camera) {
			ContentResolver cr = mContext.getContentResolver();
			
			if (photoUri == null) {return;}
			
			// 查询数据库，获取更多的照片信息（图片的物理绝对路径）
			Cursor cursor = cr.query(photoUri, null, null, null, null);
			if (cursor != null) {
				if (cursor.moveToNext()) {
					String path = cursor.getString(1);
					
					// 获取图片
					Bitmap bp = getBitMapFromPath(path);
//					imageView1.setImageBitmap(bp);
					
					// 写入数据库
					mBlobDAL.insertImg(bp);
				}
				cursor.close();
			}
			
			photoUri = null;
		}
	}
	
	// 获取图片，并缩放
  private Bitmap getBitMapFromPath(String imageFilePath) {
  	Display currentDisplay = mContext.getWindowManager().getDefaultDisplay();
  	@SuppressWarnings("deprecation")
		int dw = currentDisplay.getWidth();
  	@SuppressWarnings("deprecation")
		int dh = currentDisplay.getHeight();
  	
  	BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
  	bmpFactoryOptions.inJustDecodeBounds = true;
  	Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
  	int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight/(float)dh);
  	int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth/(float)dw);
  	
  	// 均大于1 说明图片的大小大于屏幕的大小
  	if (heightRatio > 1 && widthRatio > 1) {
  		// 按比例缩放图片
  		if (heightRatio > widthRatio) {
  			bmpFactoryOptions.inSampleSize = heightRatio;
  		} else {
  			bmpFactoryOptions.inSampleSize = widthRatio;
  		}
  	}
  	
  	bmpFactoryOptions.inJustDecodeBounds = false;
  	bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
  	return bmp;
  }
}
