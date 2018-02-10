package calendar.com.example.havh.mycustomcalendar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import calendar.com.example.havh.mycustomcalendar.MyEventView;
import calendar.com.example.havh.mycustomcalendar.R;
import calendar.com.example.havh.mycustomcalendar.SimpleDividerItemDecoration;
import calendar.com.example.havh.mycustomcalendar.activity.EventDetailActivity;
import calendar.com.example.havh.mycustomcalendar.adapter.MyRecyclerViewAdapter;
import calendar.com.example.havh.mycustomcalendar.adapter.MyVerticalRecycleViewAdapter;
import calendar.com.example.havh.mycustomcalendar.adapter.ScreenSlideDayPagerAdapter;
import calendar.com.example.havh.mycustomcalendar.adapter.ScreenSlideWeekPagerAdapter;
import calendar.com.example.havh.mycustomcalendar.customview.MyCustomRecyclerView;
import calendar.com.example.havh.mycustomcalendar.realm.RealmController;

import static calendar.com.example.havh.mycustomcalendar.model.MyEvent.KEY_EVENT;

public class ScreenSlideWeekFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {

    public static final String POSITION_WEEK_KEY = "POSITION_WEEK_KEY";
    final String TAG = "ScreenSlideWeekFragment";

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private MyRecyclerViewAdapter adapter;
    private MyVerticalRecycleViewAdapter adapter1;

    private int position;

    private String title;

    public ScreenSlideWeekFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_week, container, false);

        Bundle arg = getArguments();
        position = arg.getInt(POSITION_WEEK_KEY);//get current position of viewpager
        RealmController.with(this.getActivity());

        //setupGridView(rootView);
        setupDayFragments(rootView);//setup view pager contains each day fragment
        setupRecycleView(rootView);//horizontal list contains days in week

        //setupVerticalRecycleView(rootView);
        //setupRectangleViews(rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    void setupDayFragments(ViewGroup rootView) {
        //This pager contains 7 days in a week - 7 fragments
        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlideDayPagerAdapter(getChildFragmentManager(), mPager.getCurrentItem());
        mPager.setAdapter(mPagerAdapter);
    }

    void setupGridView(ViewGroup rootView) {
        /*GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    void setupRectangleViews(ViewGroup rootView) {
        //DrawView drawView = new DrawView(this.getContext());
        //drawView.setBackgroundColor(Color.WHITE);
        //rootView.addView(drawView);
    }

    void setupRecycleView(ViewGroup rootView) {

        // data to for day in month
        ArrayList<Integer> dayInMonth = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            dayInMonth.add(i + position * 7);
        }


        ArrayList<String> dayWeekNames = new ArrayList<>();
        dayWeekNames.add("MON");
        dayWeekNames.add("TUE");
        dayWeekNames.add("WED");
        dayWeekNames.add("THU");
        dayWeekNames.add("FRI");
        dayWeekNames.add("SAT");
        dayWeekNames.add("SUN");


        //set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rvDaysInWeek);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyRecyclerViewAdapter(getActivity(), dayInMonth, dayWeekNames);
        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
    }

    void setupVerticalRecycleView(ViewGroup rootView) {

        // data to populate the RecyclerView with
        ArrayList<Integer> hours = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            hours.add(i);
        }

        // set up the RecyclerView
        MyCustomRecyclerView recyclerView = (MyCustomRecyclerView) rootView.findViewById(R.id.rvHours);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        adapter1 = new MyVerticalRecycleViewAdapter(getActivity(), hours);
        adapter1.setMyEventViewClickListener(new MyVerticalRecycleViewAdapter.MyEventViewClickListener() {
            @Override
            public void onViewClick(MyEventView myEventView) {
                //click on MyEventView
                Intent detailEventIntent = new Intent(getContext(), EventDetailActivity.class);
                detailEventIntent.putExtra(KEY_EVENT, myEventView.getMyEvent().getName());
                getContext().startActivity(detailEventIntent);
            }
        });
        // adapter1.setClickListener(this);
        recyclerView.setAdapter(adapter1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter1.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
        title = adapter.getItem(position);
        mPager.setCurrentItem(position);

    }
}
