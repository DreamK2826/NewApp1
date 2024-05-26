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


    private TextView tv_aUserName;
    private Button btn_a_refresh, btn_a_exit;
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

    }

    private void findView() {
        lv_a_permission = findViewById(R.id.lv_a_permission);
        tv_aUserName = findViewById(R.id.tv_aUserName);
        btn_a_refresh = findViewById(R.id.btn_a_refresh);
        btn_a_exit = findViewById(R.id.btn_a_exit);


    }

    private void setListener() {
        btn_a_exit.setOnClickListener(view -> finish());
        btn_a_refresh.setOnClickListener(view -> {

            try {
                readDatabase();
                setListView();
            } catch (Exception ignored) {
                ToastUtil.showTop(this,"发生了错误！");
            }
        });
    }


    private void readDatabase() {
        SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        mRequests = new ArrayList<>();

        if (db.isOpen()) {
            int _id, uColor2, permitted2, value1;
            String username2, uNumber2, uMessage2;
            Cursor cursor = db.rawQuery("select * from myTable1 where permitted = 0", null);
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
            }
//            tv_aUserName.setText(String.valueOf(mRequests.size()));
            cursor.close();
            db.close();
        }
    }
    void setListView() {
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
        myListAdapter = new MyListAdapter((LinkedList<ListViewData>) mData, mContext);
        lv_a_permission.setAdapter(myListAdapter);
    }
}

class DbObject {
    int _id, uColor, permitted, value1;
    String username, uNumber, uMessage;

    /**
     *
     * @param _id  数据库自增数值,主键,integer
     * @param username 用户名,text
     * @param uNumber 用户车牌号,text
     * @param uColor 颜色,integer
     * @param uMessage 用户申请信息,text
     * @param permitted 是否已经许可,integer
     * @param value1 预留数据,integer
     */
    public DbObject(int _id, String username, String uNumber, int uColor,
                    String uMessage, int permitted, int value1) {
        this._id = _id;
        this.uColor = uColor;
        this.permitted = permitted;
        this.value1 = value1;
        this.username = username;
        this.uNumber = uNumber;
        this.uMessage = uMessage;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getuColor() {
        return uColor;
    }

    public void setuColor(int uColor) {
        this.uColor = uColor;
    }

    public int getPermitted() {
        return permitted;
    }

    public void setPermitted(int permitted) {
        this.permitted = permitted;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getuNumber() {
        return uNumber;
    }

    public void setuNumber(String uNumber) {
        this.uNumber = uNumber;
    }

    public String getuMessage() {
        return uMessage;
    }

    public void setuMessage(String uMessage) {
        this.uMessage = uMessage;
    }
}