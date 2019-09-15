package com.sean.module.main.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sean.base.library.base.BaseMVPFragment;
import com.sean.base.library.widget.LinearItemDecoration;
import com.sean.base.library.widget.flow.FlowAdapter;
import com.sean.base.library.widget.flow.FlowLayout;
import com.sean.module.main.R;
import com.sean.module.main.adapter.SearchHistoryAdapter;
import com.sean.module.main.bean.SearchHotKey;
import com.sean.module.main.bean.db.SearchHistory;
import com.sean.module.main.mvp.contract.SearchHistoryContract;
import com.sean.module.main.mvp.presenter.SearchHistoryPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
public class SearchHistoryFragment extends BaseMVPFragment<SearchHistoryPresenter>
        implements SearchHistoryContract.View {

    private RecyclerView recyclerView;
    private List<SearchHistory> searchHistoryList = new ArrayList<>();
    private SearchHistoryAdapter searchHistoryAdapter;
    private FlowLayout flowLayout;
    private View searchHeaderView;
    private ImageView deleteAllSearchImgView;
    private OnDataListener onDataListener;

    public static SearchHistoryFragment newInstance() {
        return new SearchHistoryFragment();
    }

    @Override
    protected SearchHistoryPresenter createPresenter() {
        return new SearchHistoryPresenter();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_history;
    }

    @Override
    protected void initView(View view) {
        searchHeaderView = LayoutInflater.from(mContext).inflate(R.layout.layout_search_history_header, null);
        deleteAllSearchImgView = searchHeaderView.findViewById(R.id.iv_search_history_del_all);
        flowLayout = searchHeaderView.findViewById(R.id.fl_search_history);
        recyclerView = view.findViewById(R.id.rv_search_history);
    }

    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
                .height(0.8f)
                .color(Color.parseColor("#aacccccc"))
                .jumpPositions(new int[]{0}); //跳过指定 position ，不设置分割线
        recyclerView.addItemDecoration(itemDecoration);
        presenter.getSearchHistory();
        presenter.getSearchHotKey();
        deleteAllSearchImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteAllHistory();
            }
        });

    }

    @Override
    public void onSearchHotKey(final List<SearchHotKey> searchHotKeys) {
        if (searchHotKeys == null) {
            return;
        }
        flowLayout.setAdapter(new FlowAdapter<SearchHotKey>(searchHotKeys) {
            @Override
            public View getView(int position, SearchHotKey searchHotKey, ViewGroup parent) {
                TextView textView = new TextView(mContext);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                textView.setBackgroundResource(R.drawable.shape_search_history_bg);
                textView.setText(searchHotKey.getName());
                textView.setTextColor(getResources().getColor(R.color.black_333));
                return textView;
            }
        });
        //item点击事件
        flowLayout.setOnItemClickListener(new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position, FlowAdapter adapter, FlowLayout parent) {
                SearchHotKey key = searchHotKeys.get(position);
                String keyword = key.getName();
                if (onDataListener != null) {
                    onDataListener.onData(keyword);
                }
            }
        });
    }

    @Override
    public void onSearchHistory(final List<SearchHistory> searchHistories) {
        if (searchHistories == null) {
            return;
        }
        searchHistoryList = searchHistories;
        if (searchHistoryAdapter == null) {
            searchHistoryAdapter = new SearchHistoryAdapter(
                    R.layout.item_search_history_content, searchHistoryList);
            searchHistoryAdapter.addHeaderView(searchHeaderView);
            searchHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    SearchHistory searchHistory = searchHistoryList.get(position);
                    String keyword = searchHistory.getKeyword();
                    // SearchHistoryFragment 通过回调方式将数据传递给 SearchActivity
                    if (onDataListener != null) {
                        onDataListener.onData(keyword);
                    }
                }
            });
            searchHistoryAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    searchHistoryList.remove(position);
                    searchHistoryAdapter.notifyDataSetChanged();
                }
            });
            recyclerView.setAdapter(searchHistoryAdapter);
        } else {
            searchHistoryAdapter.setNewData(searchHistoryList);
        }
    }

    @Override
    public void onDeleteAllHistory() {
        searchHistoryList.clear();
        if (searchHistoryAdapter != null) {
            searchHistoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 重写fragment的attacht方法；获取附属的activity；直接实现回调实例
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onDataListener = (OnDataListener) activity;
    }

    /**
     * 接口回调 传值给searchactivity
     */
    public interface OnDataListener{
        void onData(String content);
    }


}
