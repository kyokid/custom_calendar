package calendar.com.example.havh.mycustomcalendar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import calendar.com.example.havh.mycustomcalendar.MyEventView;
import calendar.com.example.havh.mycustomcalendar.R;
import calendar.com.example.havh.mycustomcalendar.activity.EventDetailActivity;
import calendar.com.example.havh.mycustomcalendar.adapter.MyRecyclerViewAdapter;
import calendar.com.example.havh.mycustomcalendar.adapter.MyVerticalRecycleViewAdapter;
import calendar.com.example.havh.mycustomcalendar.customview.MyCustomRecyclerView;

import static calendar.com.example.havh.mycustomcalendar.model.MyEvent.KEY_EVENT;

public class ScreenSlideDayFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {

    public static final String POSITION_DAY_KEY = "POSITION_DAY_KEY";
    final String TAG = "ScreenSlideDayFragment";


    private MyVerticalRecycleViewAdapter adapter1;

    private int position;

    public ScreenSlideDayFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_day, container, false);

        Bundle arg = getArguments();
        this.position = arg.getInt(POSITION_DAY_KEY);//get current position of viewpager
        setupVerticalRecycleView(rootView);
//        setupRectangleViews(rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    void setupVerticalRecycleView(ViewGroup rootView) {

        TextView tvExample = rootView.findViewById(R.id.tvExampleDay);
        tvExample.setText(String.valueOf(position));

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
        //Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
    }
}
