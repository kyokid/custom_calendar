package calendar.com.example.havh.mycustomcalendar.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import calendar.com.example.havh.mycustomcalendar.fragment.ScreenSlideDayFragment;
import calendar.com.example.havh.mycustomcalendar.fragment.ScreenSlideWeekFragment;

/**
 * A simple pager adapter that represents 5 ScreenSlideWeekFragment objects, in
 * sequence.
 */
public class ScreenSlideDayPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 7;
    final String TAG = "ScreenSlideDayAdapter";
    private int title;

    public ScreenSlideDayPagerAdapter(FragmentManager fm, int title) {
        super(fm);
        this.title = title;

    }

    @Override
    public Fragment getItem(int position) {
        //position: from 0 -> 6 (7 days in week)
        Log.i(TAG, "getItem in Day Adapter" + position);
        ScreenSlideDayFragment screenSlideDayFragment = new ScreenSlideDayFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ScreenSlideDayFragment.POSITION_DAY_KEY, title);
        screenSlideDayFragment.setArguments(bundle);
        return screenSlideDayFragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}