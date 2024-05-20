package com.dreamk.newapp1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
                        break;
                    }
                }

                //如果在管理用户组没有成功登录
                if (!foundUser) {
                    for (int j = 0; j < userNames.length; j++) {
                        if (userName.equals(userNames[j]) && password.equals(userPasswords[j])) {
                            ToastUtil.showTop(this, "普通用户组： " + userName + "正在登录...");
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