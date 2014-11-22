package im.after.app.ui.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerViewItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener onItemClickListener;
    private GestureDetector gestureDetector;

    public static interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    public RecyclerViewItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                View childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(childView != null && onItemClickListener != null) {
                    onItemClickListener.onItemLongClick(childView, recyclerView.getChildPosition(childView));
                }
            }
        });
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        // Nothing to do
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        View childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

        if (childView != null && this.onItemClickListener != null && this.gestureDetector.onTouchEvent(motionEvent)) {
            this.onItemClickListener.onItemClick(childView, recyclerView.getChildPosition(childView));
        }

        return false;
    }

}
