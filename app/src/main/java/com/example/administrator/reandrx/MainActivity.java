package com.example.administrator.reandrx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.reandrx.http.listener.empty_layout.LoadingAndRetryManager;
import com.example.administrator.reandrx.presenter.TestInterface;
import com.example.administrator.reandrx.presenter.TestPresenter;
import com.example.administrator.reandrx.utils.ToastUtils;

public class MainActivity extends AppCompatActivity implements TestInterface {

    private TestPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new TestPresenter();
        presenter.onCreate(this);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHttp();
            }
        });
    }

    private void callHttp() {
        presenter.getHttpData();
    }

    @Override
    public void onHttpCallBack(String msg) {
        ToastUtils.showShort(this, "请求成功:  " + msg);
    }

    @Override
    public void showDialog(String msg, boolean isCancelable) {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void clearData() {

    }

    @Override
    public LoadingAndRetryManager getLoadingAndRetryManager() {
        return null;
    }

    @Override
    public void showErrorSnackbar(String result) {

    }

    @Override
    public void showWarningSnackbar(String result, boolean isFinish) {

    }

    @Override
    public void showWarningSnackbar(String result) {

    }

    @Override
    public void showSnackbar(String result, int type, boolean isFinish) {

    }
}
