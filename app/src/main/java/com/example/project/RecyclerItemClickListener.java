package com.example.project;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

// RecyclerItemClickListener.java
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    Context context;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    final GestureDetectorCompat gestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }
    });

    private OnItemClickListener mListener;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        this.context=context;

        recyclerView.addOnItemTouchListener(this);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && mListener != null && gestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(child, rv.getChildAdapterPosition(child));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        // Unused, but required to implement RecyclerView.OnItemTouchListener
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // Unused, but required to implement RecyclerView.OnItemTouchListener
    }
}
