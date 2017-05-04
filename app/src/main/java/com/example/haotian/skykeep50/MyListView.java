package com.example.haotian.skykeep50;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by xuehaotian on 21/03/2016.
 */
public class MyListView extends ListView {

    private boolean moveable = false;
    private boolean closed = true;
    private float mDownX,mDownY;
    private int mTouchPosition,oldPosition = 1;


    public MyListView(Context context){
        super(context);

    }

    public MyListView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }

    public MyListView(Context context,AttributeSet attrs){
        super(context,attrs);
    }

    public void init(Context context){

    }

}
