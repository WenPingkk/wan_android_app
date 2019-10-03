package com.sean.module.main.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sean.module.main.annotation.UserLoginTrace;
import com.sean.base.library.base.BaseMVPActivity;
import com.sean.base.library.constants.Constants;
import com.sean.base.library.util.ToastUtil;
import com.sean.base.library.widget.ProgressWebView;
import com.sean.module.main.R;
import com.sean.module.main.mvp.contract.WebContract;
import com.sean.module.main.mvp.presenter.WebPresenter;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:webViewActivity 加载网页
 */
@Route(path = "/web/WebViewActivity")
public class WebViewActivity extends BaseMVPActivity<WebPresenter> implements WebContract.View {

    private ProgressWebView webView;
    private String url;
    private Toolbar toolbar;
    private int id;
    private String title;
    private String author;
    private boolean hadFavorited = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = findViewById(R.id.wv_web);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleWebViewBack();
            }
        });
        webView.setWebViewCallback(new ProgressWebView.WebViewCallback() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                toolbar.setTitle(title);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public void onLoadResource(WebView view, String url) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }

            @Override
            public void onPageLoadComplete() {

            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                return false;
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {

            }
        });
    }

    /**
     * 处理webView的点击事件
     */
    private void handleWebViewBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra(Constants.URL);
            id = intent.getIntExtra(Constants.ID, -1);
            title = intent.getStringExtra(Constants.TITLE);
            author = intent.getStringExtra(Constants.AUTHOR);
            webView.loadUrl(url);
        }
    }

    /**
     * 菜单选择
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_web_favorite) {
            //添加到收藏
            addArticleFavorite();
        } else if (item.getItemId() == R.id.item_web_share) {
            //进行分享
            shareArticle();
        }
        return true;
    }

    /**
     * 分享，未集成
     */
    private void shareArticle() {

    }

    /**
     * 设置推出动画
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_alpha, R.anim.anim_web_exit);
    }

    @UserLoginTrace
    private void addArticleFavorite() {
        presenter.addArticleFavorite(id, title, author, url);
    }

    @Override
    protected WebPresenter createPresenter() {
        return new WebPresenter();
    }

    @Override
    public void onFavoriteAdded() {
        hadFavorited = true;
        ToastUtil.show(mContext,R.string.add_favorite_success);
    }

    @Override
    public void onFavoriteDeleted() {
        hadFavorited = false;
        ToastUtil.show(mContext,R.string.delete_favorite_success);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
