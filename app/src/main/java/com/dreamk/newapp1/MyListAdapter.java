package com.dreamk.newapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class MyListAdapter extends BaseAdapter {
    private LinkedList<ListViewData> mData;
    private Context mContext;
    public MyListAdapter(LinkedList<ListViewData> mData, Context mContext){
        this.mData = mData;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item,parent,false);
            myViewHolder = new MyViewHolder();
            myViewHolder.tv_l_msg = convertView.findViewById(R.id.tv_l_msg);
            myViewHolder.tv_l_num = convertView.findViewById(R.id.tv_l_num);
            myViewHolder.tv_l_name = convertView.findViewById(R.id.tv_l_name);
            myViewHolder.img = convertView.findViewById(R.id.imageView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.tv_l_name.setText(mData.get(position).getlName());
        myViewHolder.tv_l_num.setText(mData.get(position).getlNumber());
        myViewHolder.tv_l_msg.setText(mData.get(position).getlMsg());
        return convertView;
    }

    static class MyViewHolder{
        TextView tv_l_name,tv_l_num,tv_l_msg;
        ImageView img;
    }


}
