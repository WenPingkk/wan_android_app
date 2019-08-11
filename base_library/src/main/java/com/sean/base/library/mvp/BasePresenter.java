package com.sean.base.library.mvp;

import android.content.Context;

import com.sean.base.library.base.BaseObserver;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author WenPing
 * CreateTime 2019/8/11.
 * Description:
 */
public class BasePresenter<V extends IView> implements IPresenter<V> {

    protected WeakReference<V> viewRef;

    protected CompositeDisposable compositeDisposable;

    protected Context mContext;

    public BasePresenter() {

    }

    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<>(view);
        V v = viewRef.get();
    }

    @Override
    public void detachView() {
        this.viewRef = null;
        unsubscribe();
    }

    public void addSubscribe(Observable<?> observable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        BaseObserver baseObserver = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
        compositeDisposable.add(baseObserver);
    }

    private void unsubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public boolean isViewAttached() {
        return viewRef.get() != null;
    }

    @Override
    public V getView() {
        return viewRef.get();
    }
}
