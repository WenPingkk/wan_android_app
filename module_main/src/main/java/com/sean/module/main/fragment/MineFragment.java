package com.sean.module.main.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sean.base.library.base.BaseFragment;
import com.sean.base.library.util.BlurUtil;
import com.sean.base.library.widget.ItemView;
import com.sean.base.library.widget.ZoomScrollView;
import com.sean.module.main.R;
import com.sean.module.main.annotation.UserLoginTrace;

/**
 * Author WenPing
 * CreateTime 2019/8/14.
 * Description:
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private ImageView backImgView;
    private ZoomScrollView scrollView;

    private ItemView favoriteItemView;
    private ItemView meiziItemView;
    private ItemView aboutItemView;

    @Override
    protected void initData() {
        scrollView.setZoomView(backImgView);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        backImgView.setImageBitmap(BlurUtil.blur(mContext, bitmap, 18));


        favoriteItemView.setOnClickListener(this);
        meiziItemView.setOnClickListener(this);
        aboutItemView.setOnClickListener(this);


    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View rootView) {
        backImgView = rootView.findViewById(R.id.iv_avatar_background);
        scrollView = rootView.findViewById(R.id.sv_scroll);
        favoriteItemView = rootView.findViewById(R.id.iv_mine_favorite);
        meiziItemView = rootView.findViewById(R.id.iv_mine_meizi);
        aboutItemView = rootView.findViewById(R.id.iv_mine_about);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_mine_about) {
            gotoAboutActivity();
        } else if (v.getId() == R.id.iv_mine_favorite) {
            gotoFavoriteActivity();
        } else if (v.getId() == R.id.iv_mine_meizi) {
            gotoMeiziActivity();
        }
    }
    private void gotoMeiziActivity() {
        ARouter.getInstance()
                .build("/gank/ImageMeiziActivity")
                .navigation();
    }

    /**
     * aop 拦截
     */
    @UserLoginTrace(value = 0)
    private void gotoFavoriteActivity() {
        ARouter.getInstance()
                .build("/main/FavoriteActivity")
                .navigation();
    }

    private void gotoAboutActivity() {
        ARouter.getInstance()
                .build("/main/AboutActivity")
                .navigation();
    }
}
