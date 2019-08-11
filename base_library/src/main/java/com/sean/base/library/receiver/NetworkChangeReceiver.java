package com.sean.base.library.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sean.base.library.util.NetworkUtil;
import com.sean.base.library.widget.NoNetworkTip;

/**
 * Author WenPing
 * CreateTime 2019/8/8.
 * Description:网络连接状态改变-广播
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkChangeReceiver.class.getSimpleName();

    private Activity mActivity;

    private NoNetworkTip mNoNetworkTip;

    public NetworkChangeReceiver(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive");
        if (mNoNetworkTip == null) {
            mNoNetworkTip = new NoNetworkTip(mActivity);
        }
        if (NetworkUtil.isNetworkAvailable(context)) {
            if (mNoNetworkTip != null && mNoNetworkTip.isShowing()) {
                mNoNetworkTip.dismiss();
            }
        } else {
            if (mNoNetworkTip != null && !mNoNetworkTip.isShowing()) {
                mNoNetworkTip.show();
            }
        }
    }

    public void onDestroy() {
        if (mNoNetworkTip != null && mNoNetworkTip.isShowing()) {
            mNoNetworkTip.dismiss();
        }
        mActivity = null;
        mNoNetworkTip = null;
    }
}
