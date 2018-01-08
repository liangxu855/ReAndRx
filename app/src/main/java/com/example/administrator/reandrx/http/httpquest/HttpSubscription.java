package com.example.administrator.reandrx.http.httpquest;

import com.example.administrator.reandrx.http.GsonHelper;
import com.example.administrator.reandrx.model.HttpCode;
import com.example.administrator.reandrx.model.httpresponse.BaseHttpResponse;
import com.example.administrator.reandrx.utils.LogUtils;
import com.example.administrator.reandrx.utils.ToastUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.example.administrator.reandrx.model.UserInfo.userData;


/**
 * **
 * 创建时间:2016/9/24　16:36
 * <p>
 * <p>
 * 功能介绍：
 */

public class HttpSubscription<T> extends Subscriber<ResponseBody> {

    private HttpCallBack<T> callBack;


    public HttpSubscription(HttpCallBack<T> callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onStart() {
        if (callBack != null) {
            callBack.onStart();
        }
    }

    @Override
    public void onCompleted() {
        if (callBack != null) {
            callBack.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (callBack != null) {
            callBack.onError(e);
        }
    }

    //在onNext中,返回了最后的信息
    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            if (callBack != null) {
                try {
                    Type type = getType();
                    if (type == ResponseBody.class) {
                        callBack.onSuccess((T) responseBody);
                    } else if (type == String.class) {
                        callBack.onSuccess((T) responseBody.string());
                    } else {
                        String str = responseBody.string();
                        LogUtils.e("net back:" + str);
                        T res = GsonHelper.getGson().fromJson(str, type);
                        //设置json原数据
                        if (res instanceof BaseHttpResponse) {
                            ((BaseHttpResponse) res).content = str;
                            if (((BaseHttpResponse) res).getStatusCodeMsg().equals(HttpCode.FAIL_C.getValuse())) {
                                userData.setLogin(false);
                                userData.save(userData);
//                                //跳转到登录页
//                                if (callBack.baseActivity != null) {
//                                    LoginActivity.startUI(callBack.baseActivity);
//                                }
//                                if (callBack.basePresenter != null && callBack.basePresenter.mvpView instanceof Activity) {
//                                    LoginActivity.startUI((Activity) callBack.basePresenter.mvpView, true);
//                                }

                                ToastUtils.getToastLong("请重新登录");
                            }
                        }
                        callBack.onSuccess(res);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        LogUtils.e(e.toString());
                        callBack.onError(new Exception(HttpSubscription.this.toString() + "  onNext 解析数据错误", e));

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (callBack != null) {
                callBack.onError(e);
            }
        }
    }


    /**
     * 获取回调里面的泛型
     */
    private Type getType() {
        //拿到type的类型,以便下一步做判断
        Type types = callBack.getClass().getGenericSuperclass();
        if (types == null) {
            new Throwable("HttpSubscription  ->>>  未获取到callBack的超级类");
        }
        List<Type> needtypes = new ArrayList<>();
        if (types instanceof ParameterizedType) {
            Type[] parentypes = ((ParameterizedType) types).getActualTypeArguments();
            if (parentypes == null || parentypes.length == 0) {
                new Throwable("HttpSubscription  ->>>  callBack回调 不能没有泛型，请查看HttpCallBack是否有泛型");
            }
            for (Type childtype : parentypes) {
                needtypes.add(childtype);
                if (childtype instanceof ParameterizedType) {
                    Type[] childtypes = ((ParameterizedType) childtype).getActualTypeArguments();
                    for (Type type : childtypes) {
                        needtypes.add(type);
                    }
                }
            }
        } else {
            new Throwable("HttpSubscription  ->>>  callBack回调 不能没有泛型，请查看HttpCallBack是否有泛型");
        }
        if (needtypes.size() > 0)
            return needtypes.get(0);
        else {
            new Throwable("HttpSubscription  ->>>  callBack回调 不能没有泛型，请查看HttpCallBack是否有泛型");
            return null;
        }
    }
}
