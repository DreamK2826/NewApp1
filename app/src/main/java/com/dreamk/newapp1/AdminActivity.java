package com.dreamk.newapp1;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.LinkedList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {


    private TextView tv_aUserName;
    private Button btn_a_refresh,btn_a_exit;

    private ListView lv_a_permission;

    private List<ListViewData> mData;
    private Context mContext;
    private MyListAdapter myListAdapter;


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
        lv_a_permission = findViewById(R.id.lv_a_permission);
        mContext = AdminActivity.this;
        mData = new LinkedList<>();
        mData.add(new ListViewData("user1","吉BJL387","msg0fuser1啊啊啊"));
        mData.add(new ListViewData("user2","吉BJL388","msg0fuser2啊啊"));
        mData.add(new ListViewData("user3","吉BJL389","msg0fuser3啊")); mData.add(new ListViewData("user1","吉BJL387","msg0fuser1啊啊啊"));
        mData.add(new ListViewData("user2","吉BJL388","msg0fuser2啊啊"));
        mData.add(new ListViewData("user3","吉BJL389","msg0fuser3啊"));
        mData.add(new ListViewData("user1","吉BJL387","msg0fuser1啊啊啊"));
        mData.add(new ListViewData("user2","吉BJL388","msg0fuser2啊啊"));
        mData.add(new ListViewData("user3","吉BJL389","msg0fuser3啊"));
        mData.add(new ListViewData("user1","吉BJL387","msg0fuser1啊啊啊"));
        mData.add(new ListViewData("user2","吉BJL388","msg0fuser2啊啊"));
        mData.add(new ListViewData("user3","吉BJL389","msg0fuser3啊"));
        mData.add(new ListViewData("user1","吉BJL387","msg0fuser1啊啊啊"));
        mData.add(new ListViewData("user2","吉BJL388","msg0fuser2啊啊"));
        mData.add(new ListViewData("user3","吉BJL389","msg0fuser3啊"));

        myListAdapter = new MyListAdapter((LinkedList<ListViewData>) mData,mContext);
        lv_a_permission.setAdapter(myListAdapter);
    }
}