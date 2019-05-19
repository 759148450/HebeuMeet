package com.hebeu.meet.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hebeu.meet.fragment.LaunchFragment;
//import com.hebeu.meet.fragment.FindFragment;
import com.hebeu.meet.fragment.MeFragment;
import com.hebeu.meet.fragment.MsgFragment;

/**
 * 主界面底部菜单适配器
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {
    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new MsgFragment();
                break;
            case 1:
                fragment = new LaunchFragment();
                break;
            case 2:
                fragment = new MeFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
