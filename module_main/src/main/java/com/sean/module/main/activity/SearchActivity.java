package com.sean.module.main.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sean.base.library.base.BaseActivity;
import com.sean.base.library.util.SoftKeyboardUtil;
import com.sean.module.main.R;
import com.sean.module.main.fragment.SearchHistoryFragment;
import com.sean.module.main.fragment.SearchResultFragment;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 * 搜索页面
 * 1.显示热词
 * 2.显示历史记录
 * 搜索记录存储到数据库中，获取的时候进行读取；
 * 删除分为全删和单条删除，具体实现看 adapter和adapter的child的点击事件。
 */
@Route(path = "/search/SearchActivity")
public class SearchActivity extends BaseActivity implements SearchHistoryFragment.OnDataListener, View.OnClickListener {

    private TextView searchTxtView;
    private ImageView searchBackView;
    private EditText keywordEditText;
    @Override
    protected void initView() {
        searchTxtView = findViewById(R.id.tv_search_text);
        searchBackView = findViewById(R.id.iv_search_back);
        keywordEditText = findViewById(R.id.et_keyword);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {
        super.initData();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_search_container, SearchHistoryFragment.newInstance())
                .commit();
        setListener();
    }

    private void setListener() {
        searchTxtView.setOnClickListener(this);
        searchBackView.setOnClickListener(this);
        keywordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString().trim();
                if (TextUtils.isEmpty(keyword)) {
                    showSearchHistoryFragment();
                }
            }
        });
    }

    private void showSearchHistoryFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_search_container, SearchHistoryFragment.newInstance())
                .commit();
    }

    @Override
    public void onData(String content) {
        keywordEditText.setText(content);
        keywordEditText.setSelection(content.length());
        showSearchResultFragment(content);
    }

    private void showSearchResultFragment(String content) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_search_container, SearchResultFragment.newInstance(content))
                .commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search_text) {
            String keyword = keywordEditText.getText().toString().trim();
            showSearchResultFragment(keyword);
            hideSoftKeyboard();
        } else if (v.getId() == R.id.iv_search_back) {
            finish();
        }
    }

    private void hideSoftKeyboard() {
        SoftKeyboardUtil.hideSoftKeyboard(searchTxtView);
    }
}
