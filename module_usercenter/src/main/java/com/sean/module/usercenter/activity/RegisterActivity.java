package com.sean.module.usercenter.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sean.base.library.base.BaseMVPActivity;
import com.sean.base.library.util.ToastUtil;
import com.sean.module.usercenter.R;
import com.sean.module.usercenter.bean.RegisterResult;
import com.sean.module.usercenter.contract.RegisterContract;
import com.sean.module.usercenter.presenter.RegisterPresenter;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:注册页面
 */
@Route(path = "/user/RegisterActivity")
public class RegisterActivity extends BaseMVPActivity<RegisterPresenter> implements RegisterContract.View {


    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText repasswordEditText;
    private Button registerBtn;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("注册");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        usernameEditText = findViewById(R.id.et_register_username);
        passwordEditText = findViewById(R.id.et_register_password);
        repasswordEditText = findViewById(R.id.et_register_repassword);
        registerBtn = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {
        super.initData();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String repassword = repasswordEditText.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    ToastUtil.show(mContext, "请输入用户名");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ToastUtil.show(mContext, "请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(repassword)) {
                    ToastUtil.show(mContext, "请再次输入密码");
                    return;
                }

                if (!TextUtils.equals(password, repassword)) {
                    ToastUtil.show(mContext, "两次密码不相同");
                    return;
                }
                presenter.regist(username, password, repassword);
            }
        });
    }

    @Override
    public void registerSuccess(RegisterResult result) {
        if (result != null) {
            ToastUtil.show(mContext,"regist Success");
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
