package com.dreamk.newapp1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    public static ClipBoardService clipBoard;

    private TextView tv_aUserName;
    public Button btn_a_refresh, btn_a_exit,btn_a_refAll;
    private ListView lv_a_permission;


    private List<ListViewData> mData;
    private Context mContext;
    private MyListAdapter myListAdapter;

    private List<DbObject> mRequests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        findView();
        tv_aUserName.setText(SharedDataStorage1.userName);
        setListener();
        clipBoard = new ClipBoardService(this,AdminActivity.this);

    }

    private void findView() {
        lv_a_permission = findViewById(R.id.lv_a_permission);
        tv_aUserName = findViewById(R.id.tv_aUserName);
        btn_a_refresh = findViewById(R.id.btn_a_refresh);
        btn_a_refAll = findViewById(R.id.btn_a_refAll);
        btn_a_exit = findViewById(R.id.btn_a_exit);


    }

    private void setListener() {
        btn_a_refAll.setOnClickListener(v -> {
            try {
                readDatabase("select * from myTable1");
                setListView(2);
            } catch (Exception ignored) {
//                ToastUtil.showTop(this,"发生了错误！");
            }
        });
        btn_a_exit.setOnClickListener(view -> finish());
        btn_a_refresh.setOnClickListener(view -> {
            try {
                readDatabase("select * from myTable1 where permitted = 0");
                setListView(1);
            } catch (Exception ignored) {
//                ToastUtil.showTop(this,"发生了错误！");
            }
        });

        lv_a_permission.setOnItemClickListener((parent, view, position, id) -> ToastUtil.show(AdminActivity.this,"ListViewItem:" + position));
    }


    public void readDatabase(String sql) {
        SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        mRequests = new ArrayList<>();

        if (db.isOpen()) {
            boolean haveDatas = false;
            int _id, uColor2, permitted2, value1;
            String username2, uNumber2, uMessage2;
            Cursor cursor = db.rawQuery(sql, null);
            //从数据库 -> myTable1 中获取所有未通过请求
            while (cursor.moveToNext()) {
                _id = cursor.getInt(0);
                username2 = cursor.getString(1);
                uNumber2 = cursor.getString(2);
                uColor2 = cursor.getInt(3);
                uMessage2 = cursor.getString(4);
                permitted2 = cursor.getInt(5);
                value1 = cursor.getInt(6);
                mRequests.add(new DbObject(_id, username2, uNumber2, uColor2, uMessage2, permitted2, value1));
                haveDatas = true;
            }
//            tv_aUserName.setText(String.valueOf(mRequests.size()));
            cursor.close();
            db.close();
            if(!haveDatas){
                ToastUtil.show(this,"没有待处理申请！");
            }
        }

    }
    public void setListView(int mode) {
        mContext = AdminActivity.this;
        mData = new LinkedList<>();
        String userName, userNumber, userMsg;
        //从DbObject List里面获取每一个申请的需要显示在ListView的信息
        for(int i = 0; i < mRequests.size(); i++){
            userName = mRequests.get(i).getUsername();
            userNumber = mRequests.get(i).getuNumber();
            userMsg = mRequests.get(i).getuMessage();
            mData.add(new ListViewData(userName,userNumber,userMsg));
        }
        myListAdapter = new MyListAdapter((LinkedList<ListViewData>) mData, mContext,mode);
        MyListAdapter.setMyDbObject(mRequests);
        lv_a_permission.setAdapter(myListAdapter);
    }
}

