package com.sean.module.main.mvp.presenter;

import com.sean.base.library.base.BaseObserver;
import com.sean.base.library.mvp.BasePresenter;
import com.sean.module.main.apiservice.MainApiService;
import com.sean.module.main.bean.SearchHotKey;
import com.sean.module.main.bean.db.SearchHistory;
import com.sean.module.main.bean.db.SearchHistoryDao;
import com.sean.module.main.db.DbManager;
import com.sean.module.main.mvp.contract.SearchHistoryContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author WenPing
 * CreateTime 2019/9/14.
 * Description:
 */
public class SearchHistoryPresenter extends BasePresenter<SearchHistoryContract.View>
        implements SearchHistoryContract.Presenter {

    private Disposable disposable;

    @Override
    public void getSearchHotKey() {
        addSubscribe(create(MainApiService.class)
                .getSearchHotKey(), new BaseObserver<List<SearchHotKey>>() {
            @Override
            protected void onSuccess(List<SearchHotKey> data) {
                if (isViewAttached()) {
                    getView().onSearchHotKey(data);
                }
            }
        });
    }

    @Override
    public void getSearchHistory() {
        disposable = Observable.create(new ObservableOnSubscribe<List<SearchHistory>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SearchHistory>> emitter) throws Exception {
                DbManager dbManager = DbManager.getInstance();
                List<SearchHistory> searchHistories = dbManager.getSearchHistoryDao().queryBuilder().orderDesc(SearchHistoryDao.Properties.Time).list();
                if (searchHistories != null) {
                    emitter.onNext(searchHistories);
                }
                emitter.onComplete();

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SearchHistory>>() {
                    @Override
                    public void accept(List<SearchHistory> histories) throws Exception {
                        if (isViewAttached()) {
                            getView().onSearchHistory(histories);
                        }
                    }
                });
    }

    @Override
    public void deleteAllHistory() {
        disposable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                DbManager dbManager = DbManager.getInstance();
                dbManager.getSearchHistoryDao().deleteAll();
                emitter.onNext(true);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (isViewAttached()) {
                            getView().onDeleteAllHistory();
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
