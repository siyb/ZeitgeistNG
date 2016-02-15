package com.sphericalelephant.zeitgeistng.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquaredImageView extends ImageView {
	public SquaredImageView(Context context) {
		super(context);
	}

	public SquaredImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SquaredImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
	}

	@Override
	public String toString() {
		return String.valueOf(super.hashCode());
	}
}