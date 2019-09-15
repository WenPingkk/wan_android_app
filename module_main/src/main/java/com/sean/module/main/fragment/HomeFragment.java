package com.sean.module.main.fragment;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sean.base.library.base.BaseMVPFragment;
import com.sean.base.library.constants.Constants;
import com.sean.base.library.util.StatusBarUtil;
import com.sean.base.library.widget.LinearItemDecoration;
import com.sean.base.library.widget.gridviewpager.GridRecyclerAdapter;
import com.sean.base.library.widget.gridviewpager.GridViewPager;
import com.sean.base.library.widget.gridviewpager.GridViewPagerAdapter;
import com.sean.module.main.BuildConfig;
import com.sean.module.main.R;
import com.sean.module.main.activity.MainActivity;
import com.sean.module.main.adapter.HomeArticleAdapter;
import com.sean.module.main.bean.BannerResult;
import com.sean.module.main.bean.HomeArticleResult;
import com.sean.module.main.bean.WeChatAuthorResult;
import com.sean.module.main.imageloader.GlideImageLoader;
import com.sean.module.main.mvp.contract.HomeContract;
import com.sean.module.main.mvp.presenter.HomePresenter;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/8/13.
 * Description:
 */
public class HomeFragment extends BaseMVPFragment<HomePresenter> implements HomeContract.View, View.OnClickListener {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private int[] colors = {0xffec407a, 0xffab47bc, 0xff29b6f6,
            0xff7e57c2, 0xffe24073, 0xffee8360, 0xff26a69a,
            0xffef5350, 0xff2baf2b, 0xffffa726};
    private Banner banner;
    private RecyclerView recyclerView;
    private int page = 0;
    private View headerView;
    private View searchLayoutView;
    private LinearLayoutManager linearLayoutManager;
    private TextView loginTxtView;
    private TextView searchTxtView;
    private ImageView logoImgView;
    private RefreshLayout refreshLayout;
    private GridViewPager gridViewPager;
    private int bannerHeight;
    private int searchLayoutHeight;
    private HomeArticleAdapter homeArticleAdapter;
    private List<HomeArticleResult.DatasBean> dataList = new ArrayList<>();


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        refreshLayout = rootView.findViewById(R.id.srl_home);
        logoImgView = rootView.findViewById(R.id.iv_home_logo);
        recyclerView = rootView.findViewById(R.id.rv_home);
        searchLayoutView = rootView.findViewById(R.id.rl_search_header);
        loginTxtView = rootView.findViewById(R.id.tv_home_login);
        searchTxtView = rootView.findViewById(R.id.tv_home_search);
        headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_home_header, null);
        banner = headerView.findViewById(R.id.banner_home);
        gridViewPager = headerView.findViewById(R.id.gvp_viewpager);
    }

    @Override
    protected void initData() {
        linearLayoutManager = new LinearLayoutManager(mContext, linearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置ItemDecoration 作为分割线
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
                .height(0.8f)
                .color(Color.parseColor("#66dddddd"));
        recyclerView.addItemDecoration(itemDecoration);

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());

        //请求banner的数据
        presenter.getBanner();

        //请求微信公众号的列表
        presenter.getWeChatAuthors();

        //请求首页文章列表;一开始page是0
        presenter.getHomeArticles(page);

        setListener();

    }

    private void setListener() {
        loginTxtView.setOnClickListener(this);
        searchTxtView.setOnClickListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int scrollOffset = recyclerView.computeVerticalScrollOffset();
                setSearchLayoutAlpha(scrollOffset);

                refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        refreshLayout.finishRefresh(2000, false);//数据是否成功刷新 （会影响到上次更新时间的改变）
                    }
                });

                refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        presenter.getHomeArticles(page);
                    }
                });
            }
        });
    }

    /**
     * 设置搜索栏的透明度
     *
     * @param offset
     */
    private void setSearchLayoutAlpha(int offset) {
        if (searchLayoutHeight == 0) {
            searchLayoutHeight = searchLayoutView.getMeasuredHeight();
        }
        if (bannerHeight == 0) {
            bannerHeight = banner.getMeasuredHeight();
        }
        //获取最大的 差值 = banner 高度 - 状态栏高度 - 搜索栏高度
        int maxOffSet = bannerHeight - StatusBarUtil.getStatusBarHeight(mContext) - searchLayoutHeight;
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "offset : " + offset + ",maxOffsert" + maxOffSet);
        }
        //差值未达到最大
        if (offset <= maxOffSet) {
            //获取比值
            float percent = offset * 1.0f / maxOffSet;
            searchLayoutView.getBackground().mutate().setAlpha((int) (255 * percent));
            loginTxtView.setTextColor(getResources().getColor(android.R.color.white));
            logoImgView.setImageResource(R.drawable.ic_home_logo_white);
            searchTxtView.setBackground(getResources().getDrawable(R.drawable.shape_home_input));
            ((MainActivity) getActivity()).setStatusBarTranslucent((int) (255 * percent));
        } else {
            //超出了最大的差值;设置文字和图片黑色
            loginTxtView.setTextColor(getResources().getColor(R.color.colorAccent));
            logoImgView.setImageResource(R.drawable.ic_home_logo_black);
            searchTxtView.setBackground(getResources().getDrawable(R.drawable.shape_home_input_dark));
        }
    }

    @Override
    public void onBanner(final List<BannerResult> bannerResults) {
        //获取banner回调数据
        if (bannerResults == null || bannerResults.size() == 0) {
            return;
        }
        List<String> images = getImages(bannerResults);
        banner.setImages(images);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerResult bannerResult = bannerResults.get(position);
                gotoWebViewActivityFromBanner(bannerResult);
            }
        });
        banner.start();
    }

    private void gotoWebViewActivityFromBanner(BannerResult result) {
        if (result != null) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.URL, result.getUrl());
            bundle.putInt(Constants.ID, result.getId());
            bundle.putString(Constants.TITLE, result.getTitle());
            ARouter.getInstance()
                    .build("/web/WebViewActivity")
                    .with(bundle)
                    .navigation();
            getActivity().overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);
        }
    }

    private void gotoWebViewActivity(HomeArticleResult.DatasBean datasBean) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, datasBean.getLink());
        bundle.putInt(Constants.ID, datasBean.getId());
        bundle.putString(Constants.AUTHOR, datasBean.getAuthor());
        bundle.putString(Constants.TITLE, datasBean.getTitle());
        ARouter.getInstance()
                .build("/web/WebViewActivity")
                .with(bundle)
                .navigation();
        getActivity().overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);
    }

    private List<String> getImages(List<BannerResult> results) {
        List<String> list = new ArrayList<>();
        if (results != null && results.size() > 0) {
            for (BannerResult result : results) {
                list.add(result.getImagePath());
            }
        }
        return list;
    }

    /**
     * gridViewPager 使用；
     * 加载公众号 数据
     *
     * @param weChatAuthorResults
     */
    @Override
    public void onWeChatAuthors(final List<WeChatAuthorResult> weChatAuthorResults) {
        if (weChatAuthorResults == null) {
            return;
        }
        gridViewPager.setOnGridItemClickListener(new GridViewPager.OnGridItemClickListener() {
            @Override
            public void onGridItemClick(int position, View view) {
                gotoWeChatArticleListActivity(weChatAuthorResults.get(position));
            }
        });
        gridViewPager.setAdapter(new GridViewPagerAdapter<WeChatAuthorResult>(weChatAuthorResults) {
            @Override
            public void bindData(GridRecyclerAdapter.ViewHolder viewHolder, WeChatAuthorResult result, int position) {
                ShapeDrawable shapeDrawable = new ShapeDrawable();
                shapeDrawable.setShape(new OvalShape());
                shapeDrawable.getPaint().setColor(colors[position % colors.length]);
                viewHolder.setText(R.id.tv_home_author_icon, String.valueOf(result.getName().charAt(0)))
                        .setText(R.id.tv_home_author_name, result.getName())
                        .setBackground(R.id.tv_home_author_icon, shapeDrawable);
            }
        });
    }

    private void gotoWeChatArticleListActivity(WeChatAuthorResult weChatAuthorResult) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("WeChatAuthorResult", weChatAuthorResult);
        ARouter.getInstance()
                .build("/wechat/WeChatArticleListActivity")
                .withBundle("bundle", bundle)
                .navigation();
    }

    @Override
    public void onHomeArticles(HomeArticleResult result) {
        if (result != null) {
            page ++;
            final List<HomeArticleResult.DatasBean> datas = result.getDatas();
            if (datas != null && datas.size() > 0) {
                dataList.addAll(datas);
                if (homeArticleAdapter == null) {
                    homeArticleAdapter = new HomeArticleAdapter(R.layout.item_home_article, dataList);
                    homeArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            gotoWebViewActivity(dataList.get(position));
                        }
                    });
                    homeArticleAdapter.addHeaderView(headerView);
                    recyclerView.setAdapter(homeArticleAdapter);
                } else {
                    homeArticleAdapter.setNewData(dataList);
                }
            } else {
                refreshLayout.setNoMoreData(true);
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        refreshLayout.finishLoadMore();
    }

    /**
     * txtvView的点击事件
     * 阿里路由配置路径，跳转
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_home_login) {
            gotoLoginActivity();
        } else if (view.getId() == R.id.tv_home_search) {
            gotoSearchActivity();
        }
    }

    private void gotoSearchActivity() {
        ARouter.getInstance()
                .build("/search/SearchActivity")
                .navigation();
    }

    private void gotoLoginActivity() {
        ARouter.getInstance()
                .build("/user/LoginActivity")
                .navigation();
    }
}
