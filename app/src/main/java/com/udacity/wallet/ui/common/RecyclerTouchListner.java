package com.udacity.wallet.ui.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.udacity.wallet.ui.listexpense.DataFragment;


public class RecyclerTouchListner implements RecyclerView.OnItemTouchListener{

    private final GestureDetector gestureDetector;
    private final DataFragment.ClickListner clickListner;

    public RecyclerTouchListner(Context context, final RecyclerView recyclerView, final DataFragment.ClickListner clickListner){
        this.clickListner = clickListner;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                //SLF_LOGGER.info("onSingleTapUp");
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(child !=null && clickListner!=null){
                    clickListner.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
                Log.i("","onLongPress ");
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if(child !=null && clickListner!=null && gestureDetector.onTouchEvent(e )) {
            clickListner.onClick(child, rv.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.i("","onTouchEvent ");
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
}
