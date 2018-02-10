package calendar.com.example.havh.mycustomcalendar.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import calendar.com.example.havh.mycustomcalendar.NotEqualSpacingItemDecoration;
import calendar.com.example.havh.mycustomcalendar.adapter.MyVerticalRecycleViewAdapter;

/**
 * Created by HaVH on 1/26/18.
 */

//This is the vertical list in the calendar
//Customized for zooming
public class MyCustomRecyclerView extends RecyclerView {
    private ScaleGestureDetector mScaleDetector;
    //GestureDetector mDetector;
    private float mScaleFactor = 1.0f;
    private int space = 0;
    private NotEqualSpacingItemDecoration notEqualSpacingItemDecoration;

    public MyCustomRecyclerView(Context context) {
        this(context, null);
    }

    public MyCustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public MyCustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

        notEqualSpacingItemDecoration = new NotEqualSpacingItemDecoration(space, NotEqualSpacingItemDecoration.VERTICAL, getContext());
        addItemDecoration(notEqualSpacingItemDecoration); // 16px. In practice, you'll want to use getDimensionPixelSize


    }

    @Override
    public void onScrollStateChanged(int state) {

        ((MyVerticalRecycleViewAdapter)getAdapter()).onScrollStateChanged(state);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        super.onTouchEvent(ev);
        notEqualSpacingItemDecoration.onTouchEvent(ev);
        ((MyVerticalRecycleViewAdapter)getAdapter()).onTouchEvent(ev);
        mScaleDetector.onTouchEvent(ev);
        return true;
    }


    @Override
    public void onDraw(Canvas canvas) {
        notEqualSpacingItemDecoration.setSpacing((int) (mScaleFactor * 50));
        ((MyVerticalRecycleViewAdapter)getAdapter()).onDraw(canvas);
        //removeItemDecoration(notEqualSpacingItemDecoration);
        //addItemDecoration(notEqualSpacingItemDecoration);
        invalidateItemDecorations();
    }

    public void onDrawOver(Canvas canvas, RecyclerView parent, int spacing){
        ((MyVerticalRecycleViewAdapter)getAdapter()).onDrawOver(canvas, parent, spacing);


    }



    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            invalidate();
            return true;
        }
    }
}
