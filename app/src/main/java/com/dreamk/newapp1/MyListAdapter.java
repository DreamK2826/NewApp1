package com.dreamk.newapp1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gsls.gt.GT;

import java.util.LinkedList;
import java.util.List;

public class MyListAdapter extends BaseAdapter {
    private LinkedList<ListViewData> mData;
    private static List<DbObject> myDbObject;
    private Context mContext;
    private int mode = 1;//选择加载哪种item样式表
    ClipBoardService clipBoardService= AdminActivity.clipBoard;
//    Context context= (Context) clipBoardService.getContxt();


    /**
     *
     * @param mData 要显示的数据
     * @param mContext context
     * @param mode 选择item样式
     */
    public MyListAdapter(LinkedList<ListViewData> mData, Context mContext,int mode){
        this.mData = mData;
        this.mContext = mContext;
        this.mode = mode;
    }
    public static void setMyDbObject(List<DbObject> list){
        myDbObject = list;
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
        MyViewHolder myViewHolder;
        if(convertView == null){
            if(mode == 1){
                //mode 1 带按键的
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.list_item,parent,false);
            } else if (mode == 2){
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.list_item2,parent,false);
            }

            myViewHolder = new MyViewHolder();
            myViewHolder.tv_l_msg = convertView.findViewById(R.id.tv_l_msg);
            myViewHolder.tv_l_num = convertView.findViewById(R.id.tv_l_num);
            myViewHolder.tv_l_name = convertView.findViewById(R.id.tv_l_name);

            if(mode == 1){
                myViewHolder.img = convertView.findViewById(R.id.imageButton);

            } else if(mode == 2){
                myViewHolder.tv_pass = convertView.findViewById(R.id.tv_pass);
                myViewHolder.tv_pass.setText(mData.get(position).getPermitted());

            }
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.tv_l_name.setText(mData.get(position).getlName());
        myViewHolder.tv_l_num.setText(mData.get(position).getlNumber());
        myViewHolder.tv_l_msg.setText(mData.get(position).getlMsg());


        if(mode == 1){
            myViewHolder.img.setOnClickListener(v -> {
                permitTest(position);
//                ToastUtil.show(mContext,"imgOnClick:" + position);
            });
        }

        return convertView;
    }

    /**
     * 用于弹出管理员页面的是否通过请求 dialog
     */
    void permitTest(int i) {

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

        tv_dialog_tittle.setText("第" + myDbObject.get(i).get_id() + " 条请求：");
        tv_dialog_uName.setText(myDbObject.get(i).getUsername());
        tv_dialog_uNumber.setText(myDbObject.get(i).getuNumber());
        tv_dialog_uColor.setText(myDbObject.get(i).getuColor());
        tv_dialog_uMessage.setText(myDbObject.get(i).getuMessage());

        btn_dialog_cancel.setOnClickListener(v -> viewDialog.getDialog().dismiss());

        btn_dialog_reject.setOnClickListener(v -> {
            //更新SQL
            String sql = "update myTable1 set permitted = 2 where _id = " + myDbObject.get(i).get_id();
            SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(mContext);
            SQLiteDatabase db = helper.getWritableDatabase();

            try{
                if(db.isOpen()){
                    //执行SQL语句
                    db.execSQL(sql);
                    helper.close();
                    db.close();
                }
            } catch (SQLiteException e){
                ToastUtil.show(mContext,"SQLite Error! in MyListAdapter.java:113");
            }
            viewDialog.getDialog().dismiss();
            clipBoardService.getActivity().btn_a_refresh.callOnClick();

        });
        btn_dialog_OK.setOnClickListener(v -> {
            //更新SQL
            String sql = "update myTable1 set permitted = 1 where _id = " + myDbObject.get(i).get_id();
            SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(mContext);
            SQLiteDatabase db = helper.getWritableDatabase();

            try{
                if(db.isOpen()){
                    //执行SQL语句
                    db.execSQL(sql);
                    helper.close();
                    db.close();
                }
            } catch (SQLiteException e){
                ToastUtil.show(mContext,"SQLite Error! in MyListAdapter.java:131");
            }
            viewDialog.getDialog().dismiss();
            clipBoardService.getActivity().btn_a_refresh.callOnClick();
        });


        viewDialog.getDialog().show();
    }

    static class MyViewHolder{
        TextView tv_l_name,tv_l_num,tv_l_msg,tv_pass;
        ImageButton img;
    }


}
