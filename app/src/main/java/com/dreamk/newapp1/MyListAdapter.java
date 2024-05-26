package com.dreamk.newapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.gsls.gt.GT;

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
        return mData.get(position);
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
            myViewHolder.img = convertView.findViewById(R.id.imageButton);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.tv_l_name.setText(mData.get(position).getlName());
        myViewHolder.tv_l_num.setText(mData.get(position).getlNumber());
        myViewHolder.tv_l_msg.setText(mData.get(position).getlMsg());
        myViewHolder.img.setOnClickListener(v -> {
            permitTest();
            ToastUtil.show(mContext,"imgOnClick:" + position);
        });
        return convertView;
    }

    /**
     * 用于弹出管理员页面的是否通过请求 dialog
     */
    void permitTest() {

        GT.GT_Dialog.GT_AlertDialog.ViewDialog viewDialog = new GT.GT_Dialog.GT_AlertDialog.ViewDialog()
                .initLayout(mContext, R.layout.dialog_set_permission, R.style.dialogNoBg, false, 99, 20, 20);

        final LinearLayout dialog_layout = viewDialog.getView().findViewById(R.id.dialog_layout);
        final TextView tv_dialog_tittle = viewDialog.getView().findViewById(R.id.tv_dialog_tittle);
        final TextView tv_dialog_uName = viewDialog.getView().findViewById(R.id.tv_dialog_uName);
        final TextView tv_dialog_uNumber = viewDialog.getView().findViewById(R.id.tv_dialog_uNumber);
        final TextView tv_dialog_uColor = viewDialog.getView().findViewById(R.id.tv_dialog_uColor);
        final TextView tv_dialog_uMessage = viewDialog.getView().findViewById(R.id.tv_dialog_uMessage);
        final Button btn_dialog_reject = viewDialog.getView().findViewById(R.id.btn_dialog_reject);
        final Button btn_dialog_cancel = viewDialog.getView().findViewById(R.id.btn_dialog_cancel);
        final Button btn_dialog_OK = viewDialog.getView().findViewById(R.id.btn_dialog_OK);


        btn_dialog_cancel.setOnClickListener(v -> viewDialog.getDialog().dismiss());



        viewDialog.getDialog().show();
    }

    static class MyViewHolder{
        TextView tv_l_name,tv_l_num,tv_l_msg;
        ImageButton img;
    }


}
