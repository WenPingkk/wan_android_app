package com.sean.module.usercenter.activity;

import android.graphics.Paint;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sean.base.library.base.BaseMVPActivity;
import com.sean.base.library.constants.Constants;
import com.sean.base.library.manager.UserLoginManager;
import com.sean.base.library.util.SoftKeyboardUtil;
import com.sean.module.usercenter.R;
import com.sean.module.usercenter.bean.LoginResult;
import com.sean.module.usercenter.contract.LoginContract;
import com.sean.module.usercenter.presenter.LoginPresenter;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:登录页面
 */
@Route(path = "/user/LoginActivity")
public class LoginActivity extends BaseMVPActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {


    private Button loginBtn;
    private TextView registerTxtView;
    private CheckBox pwdVisibleCheckBox;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        loginBtn = findViewById(R.id.btn_login);
        registerTxtView = findViewById(R.id.tv_register);
        pwdVisibleCheckBox = findViewById(R.id.cb_login_pwd_visible);
        usernameEditText = findViewById(R.id.et_login_username);
        passwordEditText = findViewById(R.id.et_login_password);
    }

    @Override
    protected void initData() {
        super.initData();
        //添加下划线
        registerTxtView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        String cachedUserName = presenter.readUsernamePassword(Constants.USERNAME);
        String cachedPassword = presenter.readUsernamePassword(Constants.PASSWORD);

        usernameEditText.setText(cachedUserName);
        usernameEditText.setSelection(cachedUserName.length());
        passwordEditText.setText(cachedPassword);
        passwordEditText.setSelection(cachedPassword.length());


        loginBtn.setOnClickListener(this);
        registerTxtView.setOnClickListener(this);
        /**
         * 这里是显示/隐藏(用星号) 密码
         */
        pwdVisibleCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordEditText.setSelection(passwordEditText.getText().length());
                } else {
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordEditText.setSelection(passwordEditText.getText().length());
                }
            }
        });

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    SoftKeyboardUtil.hideSoftKeyboard(passwordEditText);
                    login();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            login();
        } else if (v.getId() == R.id.tv_register) {
            gotoRegisterActivity();
        }
    }

    private void login() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(mContext, R.string.please_input_username, Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, R.string.please_input_password, Toast.LENGTH_LONG).show();
            return;
        }
        presenter.login(username, password);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    /**
     * 登录成功
     * @param result
     */
    @Override
    public void loginSuccess(LoginResult result) {
        UserLoginManager.getInstance().setLoggedin(true);
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        presenter.saveUsernamePassword(username,password);
        gotoMainActivity();
    }

    private void gotoMainActivity() {
        ARouter.getInstance().build("/main/MainActivity").navigation();
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void gotoRegisterActivity() {
        ARouter.getInstance().build("/user/RegisterActivity").navigation();
    }
}
