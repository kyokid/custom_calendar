package calendar.com.example.havh.mycustomcalendar.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import calendar.com.example.havh.mycustomcalendar.fragment.ScreenSlideWeekFragment;

/**
 * A simple pager adapter that represents 5 ScreenSlideWeekFragment objects, in
 * sequence.
 */
public class ScreenSlideWeekPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 5;
    final String TAG = "ScreenSlideWeekAdapter";

    public ScreenSlideWeekPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.i(TAG, "getItem in Week Adapter" + position);
        ScreenSlideWeekFragment screenSlidePageFragment = new ScreenSlideWeekFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ScreenSlideWeekFragment.POSITION_WEEK_KEY, position);
        screenSlidePageFragment.setArguments(bundle);
        return  screenSlidePageFragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}