package com.example.administrator.reandrx.http.base.iview;

import android.view.View;

import com.example.administrator.reandrx.http.listener.empty_layout.OnRetryClickListion;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/8/17.
 */

public interface IActivityAndFragment {
    //获取需要转化为loadingAndRetryManager的控件
    public View getSwitchRoot();

    /**
     * 显示不同的界面布局 监听器
     */
    OnRetryClickListion getOnRetryClickListion();


     boolean isLogin(boolean isToLogin);

     void addSubscription(Subscription s);

     CompositeSubscription getCompositeSubscription();


}
