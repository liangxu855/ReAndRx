package com.example.administrator.reandrx.http.base.iview;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.reandrx.http.listener.empty_layout.OnRetryClickListion;
import com.example.administrator.reandrx.model.UserInfo;
import com.example.administrator.reandrx.model.httpresponse.BaseHttpResponse;
import com.example.administrator.reandrx.utils.ToastUtils;
import com.example.administrator.reandrx.utils.UiUtils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity implements IActivityAndFragment {

    public Activity mActivity;
    /**
     * **
     * 创建时间: 2016/9/22 11:13
     * <p>
     * 方法功能：订阅的集合，防止内存泄漏
     */
    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=this;

        //初始化布局文件
        setContentView(getContentView());
        //初始化意图
        parseIntent(getIntent());

        //初始化view
        initView();

    }

    /**
     * **
     * 创建时间: 2016/9/22 11:06
     * <p>
     * 方法功能：获取布局id
     */
    public abstract int getContentView();

    /**
     * **
     * 创建时间: 2016/9/22 11:16
     * <p>
     * 方法功能：初始化view
     */
    public abstract void initView();

    /**
     * **
     * 创建时间: 2016/9/22 11:16
     * <p>
     * 方法功能：解析意图
     */
    public abstract void parseIntent(Intent intent);



    /**
     * **
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取状态栏高度
     */


    public int getStatusBarPaddingTop() {
        return UiUtils.getStatusHeight() - 2;
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取状态栏是否开启
     */


    public boolean getStatusBarEnable() {
        return true;
    }


    /**
     * **
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：显示不同的界面布局 监听器
     */
    public OnRetryClickListion getOnRetryClickListion() {
        if (this instanceof OnRetryClickListion) {
            return (OnRetryClickListion) this;
        } else {
            return null;
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        UiUtils.applyFont(UiUtils.getRootView(this));
        setRootViewPadding(getStatusBarPaddingTop());
    }

    public void setRootViewPadding(int top) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && getStatusBarEnable()) {
            UiUtils.getRootView(this).setPadding(0, top, 0, 0);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 销毁网络访问的订阅
         */
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        /**
         * 销毁网络访问的订阅
         */
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:09
     * <p>
     * 方法功能：是否登录
     *
     * @param isToLogin : 是否跳转到登录界面
     */

    @Override
    public boolean isLogin(boolean isToLogin) {
        if (!UserInfo.isSLogin()) {
            if (isToLogin) {
                ToastUtils.showShort(this,"跳转到登录界面");
            }
            return false;
        } else {
            return true;
        }
    }


    /**
     * **
     * 创建时间: 2016/9/22 11:09
     * <p>
     * 方法功能：RXJava 的订阅集合  用于销毁
     */

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        return this.mCompositeSubscription;
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：添加一个订阅
     */
    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }


    /**
     * 方法功能：显示对话框，用于网络请求
     *
     * @param msg
     * @param isCancelable
     */
    public void showDialog(String msg, boolean isCancelable) {
       ToastUtils.showShort(this,"显示对话框; msh ="+msg +"   isCancelable ="+isCancelable);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：显示对话框，用于网络请求
     */
    public void showDialog(String msg) {
        showDialog(msg, false);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：显示对话框，用于网络请求
     */
    public void showDialog() {
        showDialog(null);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：销毁对话框
     */
    public void dismissDialog() {
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：显示错误信息
     */
    public void httpError(String errString) {

    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能： Snackbar 显示信息
     */
    public void showMessage(BaseHttpResponse result) {

    }

    /**
     * **
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示错误信息
     */

    public void showErrorMessage(String result) {

    }

    /**
     * **
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示警告信息
     */
    public void showWarningMessage(String result) {

    }

    /**
     * **
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示信息 并自动班的是否是toast
     */
    public void showMessageFinish(BaseHttpResponse result) {

    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示错误信息
     */
    public void showErrorSnackbar(String result) {

    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示错误信息
     */
    public void showErrorSnackbar(String result, boolean isFinish) {

    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示警告信息
     */
    public void showWarningSnackbar(String result) {

    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示警告信息
     */
    public void showWarningSnackbar(String result, boolean isFinish) {

    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示提示信息
     */
    public void showInforSnackbar(String result, boolean isFinish) {

    }


    /**
     * 显示正确提示信息
     *
     * @param result
     */
    public void showInforSnackbar(String result) {
        showInforSnackbar(result, false);
    }

}
