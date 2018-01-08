package com.example.administrator.reandrx.presenter;

import com.example.administrator.reandrx.http.base.iview.BasePresenter;
import com.example.administrator.reandrx.http.httpquest.HttpCallBack;
import com.example.administrator.reandrx.http.httpquest.HttpRequestParam;
import com.example.administrator.reandrx.http.mode.HttpRequest;
import com.example.administrator.reandrx.model.HeadType;
import com.example.administrator.reandrx.model.httpresponse.HttpResult;
import com.example.administrator.reandrx.model.test.BannerData;

import java.util.List;

/**
 * Created by Administrator on 2018-01-08.
 */

public class TestPresenter extends BasePresenter<TestInterface> {

    public void getHttpData(){
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.NULL_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<BannerData>>>(builder) {
            @Override
            public void onSuccess(HttpResult<List<BannerData>> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                  mvpView.onHttpCallBack(result.toString());
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("site", 2);
        HttpRequest.executePost(httpCallBack, "/nurse/activities.action", param);
    }
}
