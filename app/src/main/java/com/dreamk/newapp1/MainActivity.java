package com.dreamk.newapp1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gsls.gt.GT;

public class MainActivity extends AppCompatActivity {
    //模拟登录功能用到的用户名和密码
    private final String[] adminUserNames = {"admin", "root"};
    private final String[] adminUserPasswords = {"admin", "root"};
    private final String[] userNames = {"user1", "user2", "user3"};
    private final String[] userPasswords = {"123456", "123456", "123456"};
    EditText et_user1, et_pwd1;
    Button btn_login1, btn_quit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getViews();
        setListener();

    }

    /**
     * 通过GT库申请String[]中的权限
     */

    private void GT_getPermission(boolean userFlag){


        GT.AppAuthorityManagement.Permission.init(
                this, new String[]{
                        android.Manifest.permission.BLUETOOTH_SCAN,
                        android.Manifest.permission.BLUETOOTH_CONNECT,
                        android.Manifest.permission.BLUETOOTH,
                        android.Manifest.permission.BLUETOOTH_ADMIN,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                }).permissions(new GT.AppAuthorityManagement.Permission.OnPermissionListener() {
            @Override
            public void onExplainRequestReason(GT.AppAuthorityManagement.Permission.PermissionDescription onPDListener) {

                new GT.GT_Dialog.GT_AlertDialog(MainActivity.this).dialogTwoButton(
                        android.R.drawable.ic_input_add,
                        "授予权限",
                        "为了更好的让本应用程序服务您，\n" +
                                "请授予以下权限：\n" +
                                "1.蓝牙权限用于连接设备\n" +
                                "2.定位权限用于扫描设备\n",
                        false,
                        "拒绝授权", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GT.log("拒绝");
                                onPDListener.setAcceptAdvice(false);//核心，设置拒绝授权
                            }
                        },
                        "同意授权", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GT.log("同意");
                                onPDListener.setAcceptAdvice(true);//核心，设置同意授权
                            }
                        }
                ).show();
            }

            @Override
            public boolean onForwardToSettings() {
                return true;
            }

            @Override
            public void request(boolean allGranted, String[] grantedList, String[] deniedList, String message) {
                GT.logt("allGranted:" + allGranted);
                GT.log("message", message);
                if (allGranted) {
                    //全部授权
                    GT.log("全部授权");
                    Toast.makeText(MainActivity.this, "全部授权成功", Toast.LENGTH_SHORT).show();

                    if(userFlag){
                        //管理用户页面
                        SharedDataStorage1.isAdminUser = true;
                        GotoA();
                    } else {
                        //普通用户页面
                        SharedDataStorage1.isAdminUser = false;
                        GotoB();
                    }

                } else {
                    //未全部授权
                    GT.log("grantedList:" + grantedList.length);
                    GT.log("deniedList:" + deniedList.length);
                }
            }
        });
    }

    /**
     * User页面
     */
    private void GotoB() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);

    }

    /**
     * AdminUser页面
     */
    private void GotoA() {
//        SharedDataStorage1
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }

    private void getViews() {
        btn_login1 = findViewById(R.id.btn_login1);
        btn_quit1 = findViewById(R.id.btn_quit1);
        et_pwd1 = findViewById(R.id.et_pwd1);
        et_user1 = findViewById(R.id.et_user1);
    }

    private void setListener() {

        btn_login1.setOnClickListener(v -> {
            //从用户的输入获取用户名和密码
            String userName = et_user1.getText().toString();

            String password = et_pwd1.getText().toString();
            boolean foundUser = false;
            if (userName.isEmpty()) {
                ToastUtil.show(this, "请输入用户名和密码！");
            } else {
                if (password.isEmpty()) {
                    ToastUtil.show(this, "请输入密码！");
                    return;
                }

                //遍历管理用户组
                for (int i = 0; i < adminUserNames.length; i++) {
                    if (userName.equals(adminUserNames[i]) && password.equals(adminUserPasswords[i])) {
                        ToastUtil.showTop(this, "管理用户组： " + userName + " 正在登录...");
                        foundUser = true;
                        SharedDataStorage1.userName = userName;
                        GT_getPermission(true);
                        break;
                    }
                }

                //如果在管理用户组没有成功登录
                if (!foundUser) {
                    for (int j = 0; j < userNames.length; j++) {
                        if (userName.equals(userNames[j]) && password.equals(userPasswords[j])) {
                            ToastUtil.showTop(this, "普通用户组： " + userName + "正在登录...");
                            SharedDataStorage1.userName = userName;
                            GT_getPermission(false);
                            break;
                        } else {
                            //遍历完普通用户组还没有正常登录
                            if (j == (userNames.length - 1)) {
                                ToastUtil.showTop(this, "用户名或密码有误，请检查输入或联系管理人员！");
                            }
                        }
                    }
                }
            }
        });
        btn_quit1.setOnClickListener(v -> {
            ToastUtil.show(this, "点击了退出");
            finish();
        });
    }
}