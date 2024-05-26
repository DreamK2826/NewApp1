package com.dreamk.newapp1;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity implements BLESPPUtils.OnBluetoothAction{

    private final static char[] digits = "0123456789ABCDEF".toCharArray();
    EditText et_uNumber1,et_uColor1,et_uMessage;

    Button btn_uSubmit1,btn_uCancel1,btn_uExit1;

    TextView tv_u1,tv_uname;

    private BLESPPUtils mBLESPPUtils;
    private DeviceDialogCtrl mDeviceDialogCtrl;
    private ArrayList<BluetoothDevice> mDevicesList = new ArrayList<>();
    private byte[] receiveData = new byte[128];
    boolean isConnected2Device = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findView();
        setListener();

        //初始化蓝牙SPP
        mBLESPPUtils = new BLESPPUtils(this, this);
        mBLESPPUtils.enableBluetooth();
        mBLESPPUtils.setStopString("\r\n");//接收停止位
        if (!mBLESPPUtils.isBluetoothEnable()) mBLESPPUtils.enableBluetooth();
        mBLESPPUtils.onCreate();
        mDeviceDialogCtrl = new DeviceDialogCtrl(this);
        mDeviceDialogCtrl.show();

    }

    private void setListener() {
        btn_uCancel1.setOnClickListener(v -> {
            et_uColor1.setText("");
            et_uNumber1.setText("");
            et_uMessage.setText("");
        });
        btn_uSubmit1.setOnClickListener(v -> {
            ToastUtil.show(this,"正在提交...");
            submit_f1();

        });
        btn_uExit1.setOnClickListener(v -> finish());
    }
    private void submit_f1() {
        String uNum,uColorStr,uMessage;
        uNum = et_uNumber1.getText().toString();
        uColorStr = et_uColor1.getText().toString();
        uMessage = et_uMessage.getText().toString();

        if(!uNum.isEmpty()){
            SharedDataStorage1.uNumber = uNum;

        } else {
            ToastUtil.show(this,"请输入车牌号!");

        }

        if(!uColorStr.isEmpty()){
            SharedDataStorage1.setuColor(uColorStr);

        } else {
            ToastUtil.show(this,"请输入车辆颜色！");
        }

        if(!uMessage.isEmpty()){
            SharedDataStorage1.uMessage = uMessage;

        } else {
            ToastUtil.show(this,"请输入申请信息！");
        }


        if(!uNum.isEmpty() && !uColorStr.isEmpty() && !uMessage.isEmpty()){
            byte[] msg0;
//            msg0 = ("M@" + SharedDataStorage1.userName + "," + SharedDataStorage1.uNumber + ","
//                    + SharedDataStorage1.uColor + "," + "\r\n").getBytes();

            //插入SQL
            String sql = "insert into myTable1(username,uNumber,uColor,uMessage,permitted,value1) values('"
                    + SharedDataStorage1.userName + "','"
                    + SharedDataStorage1.uNumber + "','"
                    + SharedDataStorage1.uColor + "','"
                    + SharedDataStorage1.uMessage + "','"
                    + 0 +  "','"
                    + 0 +"')";

            SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(this);
            SQLiteDatabase db = helper.getWritableDatabase();

            if(db.isOpen()){
                //执行SQL语句
                db.execSQL(sql);
                helper.close();
                db.close();
            }
            msg0 = new byte[3];
            msg0[0] = (byte) 0xff;
            msg0[1] = (byte) SharedDataStorage1.uColor;
            msg0[2] = (byte) 0x7f;
            //蓝牙发送
            mBLESPPUtils.send(msg0);
        }

    }


    private void findView() {
        et_uNumber1 = findViewById(R.id.et_uNumber1);
        et_uColor1 = findViewById(R.id.et_uColor1);
        et_uMessage = findViewById(R.id.et_uMessage);
        btn_uCancel1 = findViewById(R.id.btn_uCancel1);
        btn_uExit1 = findViewById(R.id.btn_uExit1);
        btn_uSubmit1 = findViewById(R.id.btn_uSubmit1);
        tv_u1 = findViewById(R.id.tv_u1);
        tv_uname = findViewById(R.id.tv_uname);

        if(!SharedDataStorage1.userName.isEmpty()){
            tv_uname.setText(SharedDataStorage1.userName);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{

            if(isConnected2Device){
                mBLESPPUtils.onDestroy();
            }
        }catch (Exception e){
            postShowToast("Error OnDestroy");
        }

    }

    /**
     * 设备选择对话框控制
     */
    private class DeviceDialogCtrl {
        private final LinearLayout mDialogRootView;
        private final ProgressBar mProgressBar;
        private final AlertDialog mConnectDeviceDialog;

        DeviceDialogCtrl(Context context) {
            // 搜索进度条
            mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
            mProgressBar.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            50
                    )
            );

            // 根布局
            mDialogRootView = new LinearLayout(context);
            mDialogRootView.setOrientation(LinearLayout.VERTICAL);
            mDialogRootView.addView(mProgressBar);
            mDialogRootView.setMinimumHeight(700);

            // 容器布局
            ScrollView scrollView = new ScrollView(context);
            scrollView.addView(mDialogRootView,
                    new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            700
                    )
            );

            // 构建对话框
            mConnectDeviceDialog = new AlertDialog
                    .Builder(context)
                    .setNegativeButton("刷新", null)
                    .setPositiveButton("退出", null)
                    .create();
            mConnectDeviceDialog.setTitle("选择要连接的设备");
            mConnectDeviceDialog.setView(scrollView);
            mConnectDeviceDialog.setCancelable(false);
        }

        /**
         * 显示并开始搜索设备
         */
        void show() {
            mBLESPPUtils.startDiscovery();
            mConnectDeviceDialog.show();
            mConnectDeviceDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnLongClickListener(v -> {
                mConnectDeviceDialog.dismiss();
                return false;
            });
            mConnectDeviceDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                mConnectDeviceDialog.dismiss();
                finish();
            });
            mConnectDeviceDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> {
                mDialogRootView.removeAllViews();
                mDialogRootView.addView(mProgressBar);
                mDevicesList.clear();
                mBLESPPUtils.startDiscovery();
            });
        }

        /**
         * 取消对话框
         */
        void dismiss() {
            mConnectDeviceDialog.dismiss();
        }

        /**
         * 添加一个设备到列表
         * @param device 设备
         * @param onClickListener 点击回调
         */
        private void addDevice(final BluetoothDevice device, final View.OnClickListener onClickListener) {
            runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    TextView devTag = new TextView(UserActivity.this);
                    devTag.setClickable(true);
                    devTag.setPadding(20,20,20,20);
                    devTag.setBackgroundResource(R.drawable.rect_round_button_ripple);
                    devTag.setText(device.getName() + "\nMAC:" + device.getAddress());
                    devTag.setTextColor(Color.WHITE);
                    devTag.setOnClickListener(onClickListener);
                    devTag.setTag(device);
                    devTag.setLayoutParams(
                            new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                    );
                    ((LinearLayout.LayoutParams) devTag.getLayoutParams()).setMargins(
                            20, 20, 20, 20);
                    mDialogRootView.addView(devTag);
                }
            });
        }
    }


    /**
     * 在主线程弹出 Toast
     *
     * @param msg 信息
     * @param doSthAfterPost 在弹出后做点什么
     */
    private void postShowToast(final String msg, final DoSthAfterPost doSthAfterPost) {
        runOnUiThread(() -> {
            Toast.makeText(UserActivity.this, msg, Toast.LENGTH_SHORT).show();
            if (doSthAfterPost != null) doSthAfterPost.doIt();
        });
    }
    private void postShowToast(final String msg) {
        postShowToast(msg, null);
    }
    private interface DoSthAfterPost {
        void doIt();
    }


    /**
     * 发现新设备时
     * @param device 设备
     */
    @Override
    public void onFoundDevice(BluetoothDevice device) {
        Log.d("BLE", "发现设备 " + device.getName() + device.getAddress());
        try{
            //通过设备名称过滤蓝牙设备
            if(device.getName() == null){
                return;
            }
            if (device.getName().charAt(0) != 'H'){
                return;
            }
        } catch (Exception ignored){
            ToastUtil.show(this,"扫描设备发生错误!");
        }
//        通过判断MAC地址过滤蓝牙设备
//        if (!device.getAddress().substring(0,2).equals("98")){
//            return;
//        }
        
        // 判断是不是重复的
        for (int i = 0; i < mDevicesList.size(); i++) {
            if (mDevicesList.get(i).getAddress().equals(device.getAddress())) return;
        }
        // 添加，下次有就不显示了
        mDevicesList.add(device);
        // 添加条目到 UI 并设置点击事件
        mDeviceDialogCtrl.addDevice(device, new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                BluetoothDevice clickDevice = (BluetoothDevice) v.getTag();
                postShowToast("开始连接:" + clickDevice.getName());
                mBLESPPUtils.connect(clickDevice);
            }
        });
    }

    /**
     * 连接成功时
     */
    @Override
    public void onConnectSuccess(final BluetoothDevice device) {
        postShowToast("连接成功", new DoSthAfterPost() {
            @SuppressLint("SetTextI18n")
            @Override
            public void doIt() {
                isConnected2Device = true;
                postShowToast("连接成功：" + device.getName() + " | " + device.getAddress());
                mDeviceDialogCtrl.dismiss();
            }
        });
    }
    /**
     * 当连接失败
     *
     * @param msg 失败信息
     */
    @Override
    public void onConnectFailed(final String msg) {
        postShowToast("连接失败:" + msg, new DoSthAfterPost() {
            @SuppressLint("SetTextI18n")
            @Override
            public void doIt() {
                isConnected2Device = true;
                postShowToast("连接失败！");
            }
        });
    }

    /**
     * 当接收到 byte 数组
     *
     * @param bytes 内容
     */
    @Override
    public void onReceiveBytes(final byte[] bytes) {
        postShowToast("收到数据:" + bytesToHexString(bytes), new DoSthAfterPost() {
            @SuppressLint("SetTextI18n")
            @Override
            public void doIt() {
                receiveData = bytes;
            }
        });
    }

    /**
     * 当调用接口发送 byte 数组
     *
     * @param bytes 内容
     */
    @Override
    public void onSendBytes(final byte[] bytes) {
        new DoSthAfterPost() {
            @SuppressLint("SetTextI18n")
            @Override
            public void doIt() {

            }
        };
    }
    /**
     * 当结束搜索设备
     */
    @Override
    public void onFinishFoundDevice() {

    }

    /**
     * 高效写法 byte数组转成16进制字符串
     *
     * @param bytes byte数组
     * @return 16进制字符串
     */
    @NonNull
    @Contract("_ -> new")
    public static String bytesToHexString(@NonNull byte[] bytes) {
        char[] buf = new char[bytes.length * 2];
        int c = 0;
        for (byte b : bytes) {
            buf[c++] = digits[(b >> 4) & 0x0F];
            buf[c++] = digits[b & 0x0F];
        }
        return new String(buf);
    }
}