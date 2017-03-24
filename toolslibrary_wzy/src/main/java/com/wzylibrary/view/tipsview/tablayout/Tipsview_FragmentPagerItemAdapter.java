package com.wzylibrary.view.tipsview.tablayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

public class Tipsview_FragmentPagerItemAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private final List<Tipsview_FragmentPagerItem> mItems;
    private final SparseArray<Fragment> instances;
    private OnInstantiateFragmentListener mListener;

    private Tipsview_FragmentPagerItemAdapter(final Context context, final FragmentManager fm,
                                              final List<Tipsview_FragmentPagerItem> items) {
        super(fm);
        mContext = context;
        mItems = items;
        instances = new SparseArray<>();
    }

    @Override
    public Fragment getItem(final int position) {
        Fragment f =  instances.get(position);
        if(f == null){
            f = mItems.get(position).newInstance(mContext);
            instances.put(position,f);
            if(mListener!=null) mListener.onInstantiate(position,f,mItems.get(position).getArgs());
        }
        return f;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return mItems.get(position).getPagerTitle();
    }

    public int getPageIconRes(final int position) {
        return mItems.get(position).getPageIconRes();
    }

    public void setOnInstantiateFragmentListener(final OnInstantiateFragmentListener l) {
        mListener = l;
    }

    public interface OnInstantiateFragmentListener {

        void onInstantiate(final int position, final Fragment fragment, final Bundle args);

    }

    public static class Builder {

        private final FragmentActivity mActivity;
        private final List<Tipsview_FragmentPagerItem> mItems;

        public Builder(final FragmentActivity activity) {
            mActivity = activity;
            mItems = new ArrayList<>();
        }

        public Builder add(final Tipsview_FragmentPagerItem item) {
            mItems.add(item);
            return this;
        }

        public Builder add(final int iconIesId, final String title, final Class<? extends Fragment> clazz) {
            return add(Tipsview_FragmentPagerItem.create(iconIesId,title, clazz));
        }

        public Builder add(final int iconIesId,final int titleResId, final Class<? extends Fragment> clazz) {
            return add(Tipsview_FragmentPagerItem.create(iconIesId,mActivity.getString(titleResId), clazz));
        }

        public Builder add(final int iconIesId,final int titleResId, final Class<? extends Fragment> clazz,
                final Bundle args) {
            return add(Tipsview_FragmentPagerItem.create(iconIesId,mActivity.getString(titleResId), clazz, args));
        }

        public Tipsview_FragmentPagerItemAdapter build() {
            return new Tipsview_FragmentPagerItemAdapter(mActivity, mActivity.getSupportFragmentManager(),
                    mItems);
        }
    }

}
