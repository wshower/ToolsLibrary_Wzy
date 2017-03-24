package com.wzylibrary.view.tipsview.tablayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class Tipsview_FragmentPagerItem {

    private final int iconId;
    private final String mTitle;
    private final Class<? extends Fragment> mFragmentClass;
    private final Bundle mArgs;

    protected Tipsview_FragmentPagerItem(final int resId, final String title, final Class<? extends Fragment> f,
                                         final Bundle args) {
        iconId = resId;
        mTitle = title;
        mFragmentClass = f;
        mArgs = args;
    }

    public static Tipsview_FragmentPagerItem create(final int resId, final String title,
                                                    final Class<? extends Fragment> fragmentClass) {
        return create(resId,title, fragmentClass, new Bundle());
    }

    public static Tipsview_FragmentPagerItem create(final int resId, final String title,
                                                    final Class<? extends Fragment> fragmentClass,
                                                    final Bundle args) {
        return new Tipsview_FragmentPagerItem(resId,title, fragmentClass, args);
    }

    public int getPageIconRes(){
        return iconId;
    }

    public CharSequence getPagerTitle() {
        return mTitle;
    }

    public Fragment newInstance(final Context context) {
        return Fragment.instantiate(context, mFragmentClass.getName(), mArgs);
    }

    public Bundle getArgs() {
        return mArgs;
    }


}
