package com.android.photolist;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private List<Bitmap> imageRes;
	
	public ImageAdapter(Context c, List<Bitmap> ir) {
		mContext = c;
		imageRes = ir;
	}

	@Override
	public int getCount() {
		return imageRes.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	/*
	 * 动态生成 ImageView
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(mContext);
		// 设置图片源
		Bitmap bmp = imageRes.get(position);
		BitmapDrawable bmpd = new BitmapDrawable(bmp);
		i.setImageDrawable(bmpd);
		i.setAdjustViewBounds(true);
		
		// 设置图片大小
		i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		// 设置图片背景
//		i.setBackgroundResource(R.drawable.abc_ic_menu_paste_mtrl_am_alpha;
		return i;
	}

}
