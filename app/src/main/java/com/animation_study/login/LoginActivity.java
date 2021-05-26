package com.animation_study.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.animation_study.R;

/**
 * String name = reName.getText().toString();
 * String pwd = rePwd.getText().toString();
 * String email = reEmail.getText().toString();
 * String namer = "[a-zA-Z]{6,12}";
 * if (!Pattern.matches(namer, name)) {
 * Toast.makeText(getApplicationContext(), "用户名格式错误", Toast.LENGTH_SHORT).show();
 * } else if (!Pattern.matches("[a-zA-Z0-9]{3,6}", pwd)) {
 * Toast.makeText(getApplicationContext(), "密码格式错误", Toast.LENGTH_SHORT).show();
 * } else if (!Pattern.matches("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$", email)) {
 * Toast.makeText(getApplicationContext(), "邮箱格式错误", Toast.LENGTH_SHORT).show();
 * }
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvName;
    private TextView tvPwd;
    private EditText etName;
    private EditText etPwd;
    private Button btLogin;
    private Button btRegister;
    private CheckBox cbRememberPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-11-28 08:58:05 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPwd = (TextView) findViewById(R.id.tv_pwd);
        etName = (EditText) findViewById(R.id.et_name);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        btLogin = (Button) findViewById(R.id.bt_login);
        btRegister = (Button) findViewById(R.id.bt_register);
        cbRememberPsw = (CheckBox) findViewById(R.id.cb_remember_psw);

        btLogin.setOnClickListener(this);
        btRegister.setOnClickListener(this);

    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-11-28 08:58:05 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == btLogin) {
            // Handle clicks for btLogin
            login();
        } else if (v == btRegister) {
            // Handle clicks for btRegister
            register();
        }
    }

    private void register() {

    }

    private void login() {

    }

}
