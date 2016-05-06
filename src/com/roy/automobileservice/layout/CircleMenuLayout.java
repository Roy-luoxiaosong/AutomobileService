package com.roy.automobileservice.layout;

import com.roy.automobileservice.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * <pre>
 * @author zhy 
 * http://blog.csdn.net/lmj623565791/article/details/43131133
 * </pre>
 */
public class CircleMenuLayout extends ViewGroup
{
	private int mRadius;
	/**
	 * 该容器内child item的默认尺�?
	 */
	private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
	/**
	 * 菜单的中心child的默认尺�?
	 */
	private float RADIO_DEFAULT_CENTERITEM_DIMENSION = 1 / 3f;
	/**
	 * 该容器的内边�?无视padding属�?，如�?��距请用该变量
	 */
	private static final float RADIO_PADDING_LAYOUT = 1 / 12f;

	/**
	 * 当每秒移动角度达到该值时，认为是快�?移动
	 */
	private static final int FLINGABLE_VALUE = 300;

	/**
	 * 如果移动角度达到该�?，则屏蔽点击
	 */
	private static final int NOCLICK_VALUE = 3;

	/**
	 * 当每秒移动角度达到该值时，认为是快�?移动
	 */
	private int mFlingableValue = FLINGABLE_VALUE;
	/**
	 * 该容器的内边�?无视padding属�?，如�?��距请用该变量
	 */
	private float mPadding;
	/**
	 * 布局时的�?��角度
	 */
	private double mStartAngle = 0;
	/**
	 * 菜单项的文本
	 */
	private String[] mItemTexts;
	/**
	 * 菜单项的图标
	 */
	private int[] mItemImgs;

	/**
	 * 菜单的个�?
	 */
	private int mMenuItemCount;

	/**
	 * �?��按下到抬起时旋转的角�?
	 */
	private float mTmpAngle;
	/**
	 * �?��按下到抬起时使用的时�?
	 */
	private long mDownTime;

	/**
	 * 判断是否正在自动滚动
	 */
	private boolean isFling;

	private int mMenuItemLayoutId = R.layout.circle_menu_item;

	public CircleMenuLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// 无视padding
		setPadding(0, 0, 0, 0);
	}

	/**
	 * 设置布局的宽高，并策略menu item宽高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int resWidth = 0;
		int resHeight = 0;

		/**
		 * 根据传入的参数，分别获取测量模式和测量�?
		 */
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);

		int height = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		/**
		 * 如果宽或者高的测量模式非精确�?
		 */
		if (widthMode != MeasureSpec.EXACTLY
				|| heightMode != MeasureSpec.EXACTLY)
		{
			// 主要设置为背景图的高�?
			resWidth = getSuggestedMinimumWidth();
			// 如果未设置背景图片，则设置为屏幕宽高的默认�?
			resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;

			resHeight = getSuggestedMinimumHeight();
			// 如果未设置背景图片，则设置为屏幕宽高的默认�?
			resHeight = resHeight == 0 ? getDefaultWidth() : resHeight;
		} else
		{
			// 如果都设置为精确值，则直接取小�?�?
			resWidth = resHeight = Math.min(width, height);
		}

		setMeasuredDimension(resWidth, resHeight);

		// 获得半径
		mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());

		// menu item数量
		final int count = getChildCount();
		// menu item尺寸
		int childSize = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
		// menu item测量模式
		int childMode = MeasureSpec.EXACTLY;

		// 迭代测量
		for (int i = 0; i < count; i++)
		{
			final View child = getChildAt(i);

			if (child.getVisibility() == GONE)
			{
				continue;
			}

			// 计算menu item的尺寸；以及和设置好的模式，去对item进行测量
			int makeMeasureSpec = -1;

			if (child.getId() == R.id.id_circle_menu_item_center)
			{
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(
						(int) (mRadius * RADIO_DEFAULT_CENTERITEM_DIMENSION),
						childMode);
			} else
			{
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,
						childMode);
			}
			child.measure(makeMeasureSpec, makeMeasureSpec);
		}

		mPadding = RADIO_PADDING_LAYOUT * mRadius;

	}

	/**
	 * MenuItem的点击事件接�?
	 * 
	 * @author zhy
	 * 
	 */
	public interface OnMenuItemClickListener
	{
		void itemClick(View view, int pos);

		void itemCenterClick(View view);
	}

	/**
	 * MenuItem的点击事件接�?
	 */
	private OnMenuItemClickListener mOnMenuItemClickListener;

	/**
	 * 设置MenuItem的点击事件接�?
	 * 
	 * @param mOnMenuItemClickListener
	 */
	public void setOnMenuItemClickListener(
			OnMenuItemClickListener mOnMenuItemClickListener)
	{
		this.mOnMenuItemClickListener = mOnMenuItemClickListener;
	}

	/**
	 * 设置menu item的位�?
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		int layoutRadius = mRadius;

		// Laying out the child views
		final int childCount = getChildCount();

		int left, top;
		// menu item 的尺�?
		int cWidth = (int) (layoutRadius * RADIO_DEFAULT_CHILD_DIMENSION);

		// 根据menu item的个数，计算角度
		float angleDelay = 360 / (getChildCount() - 1);

		// 遍历去设置menuitem的位�?
		for (int i = 0; i < childCount; i++)
		{
			final View child = getChildAt(i);

			if (child.getId() == R.id.id_circle_menu_item_center)
				continue;

			if (child.getVisibility() == GONE)
			{
				continue;
			}

			mStartAngle %= 360;

			// 计算，中心点到menu item中心的距�?
			float tmp = layoutRadius / 2f - cWidth / 2 - mPadding;

			// tmp cosa 即menu item中心点的横坐�?
			left = layoutRadius
					/ 2
					+ (int) Math.round(tmp
							* Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f
							* cWidth);
			// tmp sina 即menu item的纵坐标
			top = layoutRadius
					/ 2
					+ (int) Math.round(tmp
							* Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f
							* cWidth);

			child.layout(left, top, left + cWidth, top + cWidth);
			// 叠加尺寸
			mStartAngle += angleDelay;
		}

		// 找到中心的view，如果存在设置onclick事件
		View cView = findViewById(R.id.id_circle_menu_item_center);
		if (cView != null)
		{
			cView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{

					if (mOnMenuItemClickListener != null)
					{
						mOnMenuItemClickListener.itemCenterClick(v);
					}
				}
			});
			// 设置center item位置
			int cl = layoutRadius / 2 - cView.getMeasuredWidth() / 2;
			int cr = cl + cView.getMeasuredWidth();
			cView.layout(cl, cl, cr, cr);
		}

	}

	/**
	 * 记录上一次的x，y坐标
	 */
	private float mLastX;
	private float mLastY;

	/**
	 * 自动滚动的Runnable
	 */
	private AutoFlingRunnable mFlingRunnable;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		float x = event.getX();
		float y = event.getY();

		// Log.e("TAG", "x = " + x + " , y = " + y);

		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:

			mLastX = x;
			mLastY = y;
			mDownTime = System.currentTimeMillis();
			mTmpAngle = 0;

			// 如果当前已经在快速滚�?
			if (isFling)
			{
				// 移除快�?滚动的回�?
				removeCallbacks(mFlingRunnable);
				isFling = false;
				return true;
			}

			break;
		case MotionEvent.ACTION_MOVE:

			/**
			 * 获得�?��的角�?
			 */
			float start = getAngle(mLastX, mLastY);
			/**
			 * 获得当前的角�?
			 */
			float end = getAngle(x, y);

			// Log.e("TAG", "start = " + start + " , end =" + end);
			// 如果是一、四象限，则直接end-start，角度�?都是正�?
			if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4)
			{
				mStartAngle += end - start;
				mTmpAngle += end - start;
			} else
			// 二�?三象限，色角度�?是付�?
			{
				mStartAngle += start - end;
				mTmpAngle += start - end;
			}
			// 重新布局
			requestLayout();

			mLastX = x;
			mLastY = y;

			break;
		case MotionEvent.ACTION_UP:

			// 计算，每秒移动的角度
			float anglePerSecond = mTmpAngle * 1000
					/ (System.currentTimeMillis() - mDownTime);

			// Log.e("TAG", anglePrMillionSecond + " , mTmpAngel = " +
			// mTmpAngle);

			// 如果达到该�?认为是快速移�?
			if (Math.abs(anglePerSecond) > mFlingableValue && !isFling)
			{
				// post�?��任务，去自动滚动
				post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));

				return true;
			}

			// 如果当前旋转角度超过NOCLICK_VALUE屏蔽点击
			if (Math.abs(mTmpAngle) > NOCLICK_VALUE)
			{
				return true;
			}

			break;
		}
		return super.dispatchTouchEvent(event);
	}

	/**
	 * 主要为了action_down时，返回true
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
	}

	/**
	 * 根据触摸的位置，计算角度
	 * 
	 * @param xTouch
	 * @param yTouch
	 * @return
	 */
	private float getAngle(float xTouch, float yTouch)
	{
		double x = xTouch - (mRadius / 2d);
		double y = yTouch - (mRadius / 2d);
		return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
	}

	/**
	 * 根据当前位置计算象限
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private int getQuadrant(float x, float y)
	{
		int tmpX = (int) (x - mRadius / 2);
		int tmpY = (int) (y - mRadius / 2);
		if (tmpX >= 0)
		{
			return tmpY >= 0 ? 4 : 1;
		} else
		{
			return tmpY >= 0 ? 3 : 2;
		}

	}

	/**
	 * 设置菜单条目的图标和文本
	 * 
	 * @param resIds
	 */
	public void setMenuItemIconsAndTexts(int[] resIds, String[] texts)
	{
		mItemImgs = resIds;
		mItemTexts = texts;

		// 参数�?��
		if (resIds == null && texts == null)
		{
			throw new IllegalArgumentException("菜单项文本和图片至少设置其一");
		}

		// 初始化mMenuCount
		mMenuItemCount = resIds == null ? texts.length : resIds.length;

		if (resIds != null && texts != null)
		{
			mMenuItemCount = Math.min(resIds.length, texts.length);
		}

		addMenuItems();

	}

	/**
	 * 设置MenuItem的布�?��件，必须在setMenuItemIconsAndTexts之前调用
	 * 
	 * @param mMenuItemLayoutId
	 */
	public void setMenuItemLayoutId(int mMenuItemLayoutId)
	{
		this.mMenuItemLayoutId = mMenuItemLayoutId;
	}

	/**
	 * 添加菜单�?
	 */
	private void addMenuItems()
	{
		LayoutInflater mInflater = LayoutInflater.from(getContext());

		/**
		 * 根据用户设置的参数，初始化view
		 */
		for (int i = 0; i < mMenuItemCount; i++)
		{
			final int j = i;
			View view = mInflater.inflate(mMenuItemLayoutId, this, false);
			ImageView iv = (ImageView) view
					.findViewById(R.id.id_circle_menu_item_image);
			TextView tv = (TextView) view
					.findViewById(R.id.id_circle_menu_item_text);

			if (iv != null)
			{
				iv.setVisibility(View.VISIBLE);
				iv.setImageResource(mItemImgs[i]);
				iv.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{

						if (mOnMenuItemClickListener != null)
						{
							mOnMenuItemClickListener.itemClick(v, j);
						}
					}
				});
			}
			if (tv != null)
			{
				tv.setVisibility(View.VISIBLE);
				tv.setText(mItemTexts[i]);
			}

			// 添加view到容器中
			addView(view);
		}
	}

	/**
	 * 如果每秒旋转角度到达该�?，则认为是自动滚�?
	 * 
	 * @param mFlingableValue
	 */
	public void setFlingableValue(int mFlingableValue)
	{
		this.mFlingableValue = mFlingableValue;
	}

	/**
	 * 设置内边距的比例
	 * 
	 * @param mPadding
	 */
	public void setPadding(float mPadding)
	{
		this.mPadding = mPadding;
	}

	/**
	 * 获得默认该layout的尺�?
	 * 
	 * @return
	 */
	private int getDefaultWidth()
	{
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
	}

	/**
	 * 自动滚动的任�?
	 * 
	 * @author zhy
	 * 
	 */
	private class AutoFlingRunnable implements Runnable
	{

		private float angelPerSecond;

		public AutoFlingRunnable(float velocity)
		{
			this.angelPerSecond = velocity;
		}

		public void run()
		{
			// 如果小于20,则停�?
			if ((int) Math.abs(angelPerSecond) < 20)
			{
				isFling = false;
				return;
			}
			isFling = true;
			// 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
			mStartAngle += (angelPerSecond / 30);
			// 逐渐减小这个�?
			angelPerSecond /= 1.0666F;
			postDelayed(this, 30);
			// 重新布局
			requestLayout();
		}
	}

}
