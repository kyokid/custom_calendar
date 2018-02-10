package calendar.com.example.havh.mycustomcalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import calendar.com.example.havh.mycustomcalendar.customview.MyCustomRecyclerView;

public class NotEqualSpacingItemDecoration extends RecyclerView.ItemDecoration {


    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int GRID = 2;

    private final String TAG = "NotEqualSpacingItem";
    private int spacing;
    private int displayMode;
    private Context context;
    public NotEqualSpacingItemDecoration(int spacing) {
        this(spacing, -1, null);
    }

    public NotEqualSpacingItemDecoration(int spacing, int displayMode, Context context) {
        this.context = context;
        this.spacing = spacing;
        this.displayMode = displayMode;

        //paint.setColorFilter(new ColorFilter().)


    }

    public void setSpacing(int space) {
        this.spacing = space;
    }

    public void onTouchEvent(MotionEvent event) {

    }


    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        // super.onDraw(c, parent, state);

    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        MyCustomRecyclerView myCustomRecyclerView = (MyCustomRecyclerView)parent;
        myCustomRecyclerView.onDrawOver(canvas, parent, spacing);


        //canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildViewHolder(view).getAdapterPosition();
        int itemCount = state.getItemCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        setSpacingForDirection(outRect, layoutManager, position, itemCount);
    }

    private void setSpacingForDirection(Rect outRect,
                                        RecyclerView.LayoutManager layoutManager,
                                        int position,
                                        int itemCount) {

        // Resolve display mode automatically
        if (displayMode == -1) {
            displayMode = resolveDisplayMode(layoutManager);
        }

        switch (displayMode) {
            case HORIZONTAL:
                outRect.left = spacing;
                outRect.right = position == itemCount - 1 ? spacing : 0;
                //outRect.top = spacing;
                //outRect.bottom = spacing;
                break;
            case VERTICAL:
                //outRect.left = spacing;
                //outRect.right = spacing;
                outRect.top = spacing;
                outRect.bottom = position == itemCount - 1 ? spacing : 0;
                break;
            case GRID:
                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    int cols = gridLayoutManager.getSpanCount();
                    int rows = itemCount / cols;

                    //outRect.left = spacing;
                    outRect.right = position % cols == cols - 1 ? spacing : 0;
                    //outRect.top = spacing;
                    outRect.bottom = position / cols == rows - 1 ? spacing : 0;
                }
                break;
        }
    }

    private int resolveDisplayMode(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) return GRID;
        if (layoutManager.canScrollHorizontally()) return HORIZONTAL;
        return VERTICAL;
    }
}