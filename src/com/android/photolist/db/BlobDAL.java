package com.android.photolist.db;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BlobDAL extends SQLiteOpenHelper {
  public BlobDAL(Context context) {
  	super(context, "photoList.db", null, 1);
  }

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE [IMGS] ([IDPK] integer PRIMARY KEY autoincrement, IMG_DATA blob)";
		db.execSQL(sql);
	}
	
	// 存储图片
	public void insertImg(Bitmap bmp) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
		
		cv.put("IMG_DATA", os.toByteArray());
		db.insert("IMGS", null, cv);
	}
	
	// 读取
	public List<Bitmap> readImg(){
		SQLiteDatabase db = getReadableDatabase();
		Cursor cr = db.rawQuery("select * from IMGS", null);
		List<Bitmap> list = new ArrayList<Bitmap>();
		while (cr.moveToNext()) {
			byte[] in = cr.getBlob(cr.getColumnIndex("IMG_DATA"));
			list.add(BitmapFactory.decodeByteArray(in, 0, in.length));
		}
		return list;
	}
	
	// 删除所有图片
	public void deleteImgs() {
		SQLiteDatabase db = getWritableDatabase();
		db.delete("IMGS", null, null);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
  
}
