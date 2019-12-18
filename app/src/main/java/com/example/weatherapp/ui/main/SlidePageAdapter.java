package com.example.weatherapp.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class SlidePageAdapter extends FragmentStatePagerAdapter {

    public List<Fragment> fragmentList;

    public SlidePageAdapter(FragmentManager fm, List<Fragment> fragmentList){
        super(fm);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public int getItemPosition(Object object){
        int index = this.fragmentList.indexOf(object);

        if(index == -1){
            return POSITION_NONE;
        }

        else{
            return index;
        }
    }

}
