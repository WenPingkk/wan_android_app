package com.sean.base.library.base;

import com.sean.base.library.mvp.IPresenter;
import com.sean.base.library.mvp.IView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author WenPing
 * CreateTime 2019/8/11.
 * Description:
 * BaseMVPActivity 泛型
 */
public abstract class BaseMVPActivity<P extends IPresenter> extends BaseActivity implements IView {

    protected P presenter;
    private Unbinder unbinder;

    @Override
    protected void initData() {
        super.initData();
        presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
        unbinder = ButterKnife.bind(this);
    }

    protected abstract P createPresenter();

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity 销毁时,取消所有的订阅
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
