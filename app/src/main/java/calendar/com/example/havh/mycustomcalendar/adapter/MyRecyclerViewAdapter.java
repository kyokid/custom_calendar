package calendar.com.example.havh.mycustomcalendar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import calendar.com.example.havh.mycustomcalendar.R;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Integer> dayInMonthList = Collections.emptyList();
    private List<String> dayInWeekNameList = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<Integer> daysInMonth, List<String> dayInWeekNames) {
        this.mInflater = LayoutInflater.from(context);
        this.dayInMonthList = daysInMonth;
        this.dayInWeekNameList = dayInWeekNames;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int day = dayInMonthList.get(position);

        String dayInWeek = dayInWeekNameList.get(position);
        holder.dayInWeekNameTextView.setText(day + "");
        holder.dayInMonthNumberTextView.setText(dayInWeek);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return dayInWeekNameList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView dayInWeekNameTextView;
        public TextView dayInMonthNumberTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            dayInWeekNameTextView = itemView.findViewById(R.id.tvDayName);
            dayInMonthNumberTextView = itemView.findViewById(R.id.tvDayInMonthNumber);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return dayInMonthList.get(id).toString();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}