package com.example.weatherapp.ui.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.weatherapp.fragments.Photos_frag;
import com.example.weatherapp.R;
import com.example.weatherapp.fragments.Today_frag;
import com.example.weatherapp.fragments.Weekly_frag;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private static final int[] ICONS = new int[]{R.drawable.ic_calendar_today, R.drawable.ic_trending_up, R.drawable.ic_google_photos};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case(0):
                fragment = new Today_frag();
                break;

            case(1):
                fragment = new Weekly_frag();
                break;

            case(2):
                fragment = new Photos_frag();
                break;
        }
        return fragment;
    }

    Drawable myDrawable;
    String title;
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                myDrawable = mContext.getDrawable(R.drawable.ic_calendar_today);
                title = mContext.getResources().getString(R.string.tab_text_1);
                break;
            case 1:
                myDrawable = mContext.getDrawable(R.drawable.ic_trending_up);
                title = mContext.getResources().getString(R.string.tab_text_2);
                break;
            case 2:
                myDrawable = mContext.getDrawable(R.drawable.ic_google_photos);
                title = mContext.getResources().getString(R.string.tab_text_3);
                break;
        }

        SpannableStringBuilder sb = new SpannableStringBuilder("   " + '\n' +  title);
        try{
            myDrawable.setBounds(0, 0,72, 72);
            ImageSpan imageSpan = new ImageSpan(myDrawable, DynamicDrawableSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0,1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            return sb;
        } catch(Exception e){
            return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}