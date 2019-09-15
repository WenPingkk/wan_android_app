package com.sean.module.main.mvp.presenter;

import android.text.TextUtils;

import com.sean.base.library.base.BaseObserver;
import com.sean.base.library.mvp.BasePresenter;
import com.sean.module.main.apiservice.MainApiService;
import com.sean.module.main.bean.SearchResult;
import com.sean.module.main.bean.db.SearchHistory;
import com.sean.module.main.bean.db.SearchHistoryDao;
import com.sean.module.main.db.DbManager;
import com.sean.module.main.mvp.contract.SearchResultContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
public class SearchResultPresenter extends BasePresenter<SearchResultContract.View> implements SearchResultContract.Presenter {

    private Disposable disposable;


    /**
     * 保存关键字，如果数据库已经存在，则更新时间
     * @param keyword
     */
    @Override
    public void saveSearchHistory(final String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            return;
        }
        disposable = Observable.just(keyword)
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        SearchHistoryDao searchHistoryDao = DbManager.getInstance().getSearchHistoryDao();
                        List<SearchHistory> histories = searchHistoryDao.loadAll();
                        if (histories != null && histories.size() > 0) {
                            boolean flag = false;
                            for (SearchHistory history : histories) {
                                String cacheKeyword = history.getKeyword();
                                if (keyword.equals(cacheKeyword)) {
                                    //更新时间
                                    history.setTime(System.currentTimeMillis());
                                    searchHistoryDao.update(history);
                                    flag = true;
                                    break;
                                }
                            }
                            //新增
                            if (!flag) {
                                searchHistoryDao.insert(new SearchHistory(keyword, System.currentTimeMillis()));
                            }
                        } else {
                            //新增
                            searchHistoryDao.insert(new SearchHistory(keyword, System.currentTimeMillis()));
                        }
                    }
                });
    }

    @Override
    public void getSearchResult(int page, String keyword) {
        addSubscribe(create(MainApiService.class)
                        .getSearchResult(page, keyword),
                new BaseObserver<SearchResult>(getView()) {

                    @Override
                    protected void onSuccess(SearchResult data) {
                        if (isViewAttached()) {
                            getView().onSearchResult(data);
                        }
                    }
                });
    }

    /**
     * 取消订阅
     */
    @Override
    public void detachView() {
        super.detachView();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}


































