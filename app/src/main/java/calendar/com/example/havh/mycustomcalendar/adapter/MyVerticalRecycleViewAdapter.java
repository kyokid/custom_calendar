package calendar.com.example.havh.mycustomcalendar.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import calendar.com.example.havh.mycustomcalendar.model.MyEvent;
import calendar.com.example.havh.mycustomcalendar.MyEventView;
import calendar.com.example.havh.mycustomcalendar.R;
import calendar.com.example.havh.mycustomcalendar.realm.RealmController;
import io.realm.Realm;

import static android.content.ContentValues.TAG;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;

public class MyVerticalRecycleViewAdapter extends RecyclerView.Adapter<MyVerticalRecycleViewAdapter.ViewHolder> {

    private List<Integer> hourList = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private MyEventViewClickListener myEventViewClickListener;
    private List<MyEventView> myEventViewList = new ArrayList<>();

    private Paint paint = new Paint();

    private Realm realm;
    private Handler handler = new Handler();
    private boolean isScrolling;

    // data is passed into the constructor
    public MyVerticalRecycleViewAdapter(Context context, List<Integer> hours) {
        this.mInflater = LayoutInflater.from(context);
        this.hourList = hours;
        paint.setAntiAlias(false);
        realm = Realm.getDefaultInstance();
        if (realm.isEmpty()) {
            MyEvent myEvent1 = new MyEvent("event1");
            myEvent1.setStartTime(1518213600000L); //5:00
            myEvent1.setDurationTime((int) (1.5f * 60 * 60));
            myEvent1.setName("event 1");
            //myEventList.add(myEvent1);

            MyEvent myEvent2 = new MyEvent("event2");
            myEvent2.setStartTime(1518230400000L);//9:40
            myEvent2.setDurationTime((int) (2.5f * 60 * 60));
            myEvent2.setName("event 2");
            RealmController.getInstance().getMyEventList().add(myEvent2);
//            realm.beginTransaction();
//            realm.copyToRealm(myEventList);
//            realm.commitTransaction();
            RealmController.getInstance().saveMyEventList();
        }
        else{
            RealmController.getInstance().loadMyEventList();
        }

        RealmController.getInstance().addMyListEvent(new RealmController.MyListEvent() {
            @Override
            public void OnMyListChanged(List<MyEvent> myEventList) {
                updateMyEventViewList();
            }
        });
        updateMyEventViewList();

    }

    void updateMyEventViewList(){
        myEventViewList.clear();
        if (RealmController.getInstance().getMyEventList().size() > 0) {
            for (MyEvent myEvent : RealmController.getInstance().getMyEventList()) {
                myEventViewList.add(new MyEventView(myEvent));
            }
        }
    }


    //Activity result from fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) mInflater.inflate(R.layout.vertical_recyclerview_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }




    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int hour = hourList.get(position);
        holder.hourTextView.setText(hour + "");

    }


    public void onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (!isScrolling) {
                    for (MyEventView myEventView : myEventViewList) {
                        myEventView.setSelected(false);
                    }
                    for (MyEventView myEventView : myEventViewList) {
                        if (event.getX() >= myEventView.getX() && event.getY() <= myEventView.getX() + myEventView.getWidth()) {
                            if (event.getY() >= myEventView.getY() && event.getY() <= myEventView.getY() + myEventView.getHeight()) {
                                Log.i(TAG, "touched MyEvent " + myEventView);
                                myEventView.setSelected(true);
                                if (myEventViewClickListener != null) {
                                    myEventViewClickListener.onViewClick(myEventView);
                                }

//
                                break;
                            }
                        }
                    }
                }
                break;
        }
    }

    public void onDraw(Canvas c) {

    }
    public void onScrollStateChanged(int state) {
        if (state == SCROLL_STATE_DRAGGING){
            isScrolling = true;
        }else{
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isScrolling = false;

                }
            }, 200);
        }
        Log.i(TAG, "isScrolling " + isScrolling);

    }

    public void onDrawOver(Canvas canvas, RecyclerView parent, int spacing) {
        int left = parent.getPaddingLeft() + 80;
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        //Log.i("TestDecoration", "chidlCount: " + childCount);
        //canvas.save();


        if (myEventViewList.size() > 0)
            for (MyEventView myEventView : myEventViewList) {
                float startTimeHour = MyEvent.getStartTimeAsFloatHourOfDay(myEventView.getMyEvent());
                float endTimeHour = startTimeHour + MyEvent.secondToHour(myEventView.getMyEvent().getDurationTime());
                int startTimeHourInt = (int) startTimeHour;
                int endTimeHourIntUpper = (int) Math.ceil(endTimeHour);
                if (endTimeHour > startTimeHour) {
                    boolean isDrawn = false;
                    for (int j = startTimeHourInt; j <= endTimeHourIntUpper; j++) {

                        //Draw this event at currentHourView

                        for (int i = 0; i < childCount; i++) {
                            View child = parent.getChildAt(i);

                            //TODO:improve this, get the hour of viewholder faster
                            MyVerticalRecycleViewAdapter.ViewHolder viewHolder =
                                    (MyVerticalRecycleViewAdapter.ViewHolder) parent.getChildViewHolder(child);


                            // viewHolder.getItemId()


                            // if (viewHolder != null)
                            //   Log.i("TestDecoration", "viewHolder: " + viewHolder.hourTextView.getText());

                            float top = child.getTop();
                            float bottom = child.getBottom();
                            float height = bottom - top + spacing;
                            MyVerticalRecycleViewAdapter myVerticalRecycleViewAdapter = (MyVerticalRecycleViewAdapter) parent.getAdapter();
                            int currentHourView = myVerticalRecycleViewAdapter.getItem(viewHolder.getAdapterPosition());
                            //int currentHourView = Integer.parseInt(viewHolder.hourTextView.getText().toString());
                            if (j == currentHourView) {
                                top += (bottom - top) * 0.5 + height * (startTimeHour - startTimeHourInt);
                                bottom = top + (height) * (endTimeHour - j);
                                if (j > startTimeHourInt) {
                                    top -= height * (j - startTimeHourInt);
                                    //bottom+=height;
                                }
                                myEventView.setX(left);
                                myEventView.setY(top);
                                myEventView.setWidth(right - left);
                                myEventView.setHeight(bottom - top);
                                if (myEventView.isSelected()) {
                                    paint.setColor(Color.GRAY);
                                } else {
                                    paint.setColor(Color.BLACK);
                                }
                                paint.setStrokeWidth(3);
                                canvas.drawRect(left, top, right, bottom, paint);
                                //paint.setStrokeWidth(0);
                                isDrawn = true;
                                break;
                            }
                        }
                        if (isDrawn)
                            break;
                    }
                }
            }
    }


    public MyEventViewClickListener getMyEventViewClickListener() {
        return myEventViewClickListener;
    }

    public void setMyEventViewClickListener(MyEventViewClickListener myEventViewClickListener) {
        this.myEventViewClickListener = myEventViewClickListener;
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return hourList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView hourTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            hourTextView = itemView.findViewById(R.id.tvHour);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public int getItem(int id) {
        return hourList.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    public interface MyEventViewClickListener{
        void onViewClick(MyEventView myEventView);
    }
}