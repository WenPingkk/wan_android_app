package com.sean.module_gank.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sean.base.library.base.BaseActivity;
import com.sean.base.library.http.RetrofitClient;
import com.sean.base.library.util.AppUtil;
import com.sean.base.library.util.StatusBarUtil;
import com.sean.base.library.util.ToastUtil;
import com.sean.module_gank.R;
import com.sean.module_gank.adapter.ImagePreviewAdapter;
import com.sean.module_gank.apiservice.GankApiService;
import com.sean.module_gank.bean.MeiziResult;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Author WenPing
 * CreateTime 2019/10/5.
 * Description:
 * 显示妹子图,实现下载功能
 */
@Route(path = "/gank/ImagePreviewActivity")
public class ImagePreviewActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ImagePreviewAdapter adapter;
    private TextView countTxtView;
    private ArrayList<MeiziResult> dataList = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private TextView saveTxtView;
    private int curPosition;
    private Disposable disposable;

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.rv_image_preview);
        countTxtView = findViewById(R.id.tv_image_preview_count);
        saveTxtView = findViewById(R.id.tv_image_save);
    }

    /**
     * PagerSnapHelper 使用 实现 viewpager滑动效果
     */
    @Override
    protected void initData() {
        super.initData();
        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //PagerSnapHelper和recyclerview的使用
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            curPosition = bundle.getInt("position");
            dataList = bundle.getParcelableArrayList("meizis");
            adapter = new ImagePreviewAdapter(R.layout.item_image_preview, dataList);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(curPosition);
            String s = String.format(getResources().getString(R.string.position_and_count), curPosition + 1, dataList.size());
            countTxtView.setText(s);


            /**
             * 监听recyclerview 滑动效果
             */
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                //滑动状态为空闲状态时,再更改显示数据
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    //空闲状态
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        curPosition = layoutManager.findLastVisibleItemPosition();
                        countTxtView.setText((curPosition + 1) + " / " + dataList.size());
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
        }

        saveTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeiziResult meiziResult = dataList.get(curPosition);
                if (meiziResult != null) {
                    String url = meiziResult.getUrl();
                    //保存图片,下载,并提示
                    saveImage(url);
                }
            }
        });
    }

    /**
     * rxjava map关键字使用
     *
     * @param url
     */
    private void saveImage(String url) {
        GankApiService gankApiService = RetrofitClient.getInstance().getRetrofit().create(GankApiService.class);
        disposable = gankApiService.downloadImage(url)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<ResponseBody, Boolean>() {
                    @Override
                    public Boolean apply(ResponseBody responseBody) throws Exception {
                        return writeToSdcard(mContext, responseBody.byteStream());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        ToastUtil.show(mContext, result ? "保存成功" : "保存失败");
                    }
                });
    }

    /**
     * 图片写入到sdcard;
     * 图示图库更新
     *
     * @param context
     * @param inputStream
     * @return
     */
    private boolean writeToSdcard(Context context, InputStream inputStream) {
        if (inputStream == null) {
            return false;
        }
        File dirFile = new File(Environment.getExternalStorageDirectory(), AppUtil.getAppName(mContext));
        String fileName = AppUtil.getAppName(mContext) + "_" + System.currentTimeMillis() + ".jpg";
        File file = new File(dirFile, fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        byte[] buffer = new byte[1024 * 5];
        int len = 0;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            bis = new BufferedInputStream(inputStream);
            fos = new FileOutputStream(file);
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
        return true;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_image_preview;
    }

    @Override
    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, getResources().getColor(android.R.color.black), 0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面切换时,取消订阅
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
