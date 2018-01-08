package com.example.administrator.reandrx.http.httphead;


import com.example.administrator.reandrx.http.httpquest.HttpCallBack;
import com.example.administrator.reandrx.http.httpquest.HttpRequestParam;
import com.example.administrator.reandrx.http.mode.HttpRequest;
import com.example.administrator.reandrx.model.HeadType;
import com.example.administrator.reandrx.utils.DateUtils;
import com.example.administrator.reandrx.utils.StringUtils;
import com.example.administrator.reandrx.utils.ToastUtils;

import static com.example.administrator.reandrx.model.UserInfo.userData;

/**
 * 、获取ticket
 * 接口：/common/getTicket.action
 * 使用方: WEB、APP
 * 请求方式: http—get/post
 * 请求地址:
 * 环境	地址
 * 开发	https://ur.honganjk.com/common/getTicket.action
 * 测试
 * 生产	https://urapi.honganjk.com/common/getTicket.action
 * header说明
 * 无
 * 参数说明 (默认字符串类型)
 * 参数 	约束	说明
 * mac	必选	客户端的唯一码， APP可用手机mac，微信企业号可用微信ID，轻应用自己构建唯一码
 * 参数示例：
 * “mac”：“88:e3:ab:43:28:21”
 * 返回说明
 * 正常时的返回JSON数据包示例：
 * {
 * "msg": "OK",
 * "code": "A00000",
 * "data": "bb5467e939b68d8be4a42a16abf64db5" //该mac对应的ticket，无需登录接口的安全头，有效期120秒，失效需重新获取
 * Created by admin on 2017/2/20.
 */

public abstract class HttpAddTicketHead {
    private static long mTime = 0;

    public HttpAddTicketHead() {
        getTicket();
    }

    public  void getTicket() {

        //ticked的有效时间为120秒，如果超时则重新获取
        if (DateUtils.getDateLong() - mTime < 100000&&DateUtils.getDateLong() - mTime >1) {
            succeed(true);
            return;
        }

        HttpCallBack.Builder buider = new HttpCallBack.Builder();
        buider.setHttpHead(HeadType.NULL_HEAD);
        HttpCallBack httpCallBack = new HttpCallBack<TicketData>(buider) {
            @Override
            public void onSuccess(TicketData result) {
                super.onSuccess(result);
                if ( !StringUtils.isBlank(result.getData())) {
                    userData.setTicket(result.getData());
                    userData.setSecret();
                    mTime = DateUtils.getDateLong();
                    succeed(true);
                } else {
                    ToastUtils.getToastLong("请检查网络设置");
                }
            }

            @Override
            public void onError(Throwable error) {
                super.onError(error);
                ToastUtils.getToastLong("请检查网络设置");
                succeed(false);
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("mac", userData.getMac());
        HttpRequest.executePost(httpCallBack, "/common/getTicket.action", param);
    }

  public   abstract void succeed(boolean isSucceed);

}
