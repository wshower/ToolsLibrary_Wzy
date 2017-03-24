package com.wzylibrary.view.tipsview.tablayout;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wzylibrary.view.tipsview.tabview.TabItemView;
import com.wzylibrary.view.tipsview.tipsview.TipsView;


/**
 * 底部的导航栏  （图标上的小圆点，可拖拽；仿微信的滑动渐变；可动态添加fragment）
 * noted by wzy (2016/6/16  15:08)
 *
 * 用法：
 *   viewPager = (ViewPager)this.findViewById(R.id.view_pager);
     bottom_tabLayout = (Bottom_TabLayout)findViewById(R.id.tablayout);

     viewPager.setAdapter(new Tipsview_FragmentPagerItemAdapter.Builder(this)
         .add(R.drawable.m11, "首页", Main1_Fragment.class)
         .add(R.drawable.m22, "订单", Main4_Fragment.class)
         .build());
     bottom_tabLayout.setViewPager(viewPager);
     viewPager.setOffscreenPageLimit(3);

     bottom_tabLayout.setTabtips(0,0);
     bottom_tabLayout.setTabtips(1,0);
*/
public class Bottom_TabLayout extends LinearLayout implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private ViewPager mViewPager;

    public Bottom_TabLayout(Context context) {
        super(context);
    }

    public Bottom_TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sets the associated view pager. Note that the assumption here is that the pager content
     * (number of tabs and tab titles) does not change after this call has been made.
     */
    public void setViewPager(ViewPager viewPager) {
        removeAllViews();

        mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.addOnPageChangeListener(this);
            populateTabItem();
        }
    }

    private void populateTabItem() {
        final PagerAdapter adapter = mViewPager.getAdapter();

        if(adapter instanceof Tipsview_FragmentPagerItemAdapter) {
            for (int i = 0; i < adapter.getCount(); i++) {
                TabItemView tabView = new TabItemView(getContext(),
                        ((Tipsview_FragmentPagerItemAdapter) adapter).getPageIconRes(i),
                        adapter.getPageTitle(i).toString());

                tabView.setOnClickListener(this);
                TipsView.create((Activity) getContext()).attach(tabView.getDotView());   //

                LayoutParams llp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                llp.weight = 1;
                llp.topMargin = llp.bottomMargin = 10;
                addView(tabView,llp);
            }
            ((TabItemView)getChildAt(0)).setIconAlpha(1f);
        }
    }


    /**
     * 设置底部导航栏的小圆点
     * noted by wzy (2016/6/16  14:57)
     * @param position  底部导航栏中的第几个，0开始
     * @param tipNumber 要显示的小圆点数字（0不显示，为负显示红点）
     */
    public void setTabtips(int position,int tipNumber){
        if(position < getChildCount()){
            ((TabItemView)getChildAt(position)).setNotifyNum(tipNumber);
        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < getChildCount(); i++) {
            if (v == getChildAt(i)) {
                mViewPager.setCurrentItem(i);
                ((TabItemView) getChildAt(i)).setIconAlpha(1);
            } else {
                ((TabItemView)getChildAt(i)).setIconAlpha(0);   //直接点击按钮时，清除其他按钮的透明度
            }
        }
    }

    private int mScrollState;
    /**
     * 用于判定是否是由手指直接点击下面的按钮导致的viewpager滑动  （手指滑动时state是1——2——0    点击时sate是2——0）
     * create by wzy (2016/6/16  14:47)
    */
    private boolean tag;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (tag) {  //添加这个判断为了解决 直接点击第四个按钮时，第一个按钮的透明度没有完全变为0，配合onclick方法
            if (positionOffset > 0){
                ((TabItemView)getChildAt(position)).setIconAlpha(1 - positionOffset);
                ((TabItemView)getChildAt(position + 1)).setIconAlpha(positionOffset);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;

        if (state == 1) {
            tag = true;
        }
        if (state == 0) {  //每次为0 后清除这个状态
            tag = false;
        }

    }

    @Override
    public void onPageSelected(int position) {
        if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
        }
    }

}
