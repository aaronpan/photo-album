package com.android.photolist;

import android.content.Context;
import android.view.View;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

@SuppressWarnings("deprecation")
public class ImageSwitcherViewFactory implements ViewSwitcher.ViewFactory {
  private Context mContext;
  
	public ImageSwitcherViewFactory(Context ctx) {
		mContext = ctx;
	}
  
	/*
	 * 生成 ImageView
	 * @see android.widget.ViewSwitcher.ViewFactory#makeView()
	 */
	@Override
	public View makeView() {
		ImageView i = new ImageView(mContext);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.
				LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return i;
	}

}
