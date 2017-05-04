package com.example.haotian.skykeep50;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by xuehaotian on 20/03/2016.
 */

/**Author:HaotianXue u5689296**/
public class listDataAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public listDataAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler{
        TextView textView_content;
//        ImageView imageView_content;
//        VideoView videoView;
    }

    public void update(int position,Object newobject){
        list.set(position,newobject);
    }

    @Override
    public void add(Object object){
        super.add(object);
        list.add(object);
    }

    @Override
    public void remove(Object object){
        super.remove(object);
        list.remove(object);
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        View row = convertView;
        LayoutHandler layoutHandler;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            layoutHandler = new LayoutHandler();
            layoutHandler.textView_content = (TextView)row.findViewById(R.id.text_content);
            row.setTag(layoutHandler);
        }
        else {
            layoutHandler = (LayoutHandler)row.getTag();

        }
        Note note = (Note)this.getItem(position);
        layoutHandler.textView_content.setText(note.getContent());
        return row;
    }





}
