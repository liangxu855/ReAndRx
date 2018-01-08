package com.example.administrator.reandrx.model.test;

/**
 * 说明:广告位实体类
 * 360621904@qq.com 杨阳 2017/3/1  10:21
 * <p>
 * "img": "https://hajk.image.alimmdn.com/bz/hongan.png",//图片
 * "url": "https://www.honganjk.com/",	//地址url
 * "type": 1，	//活动页类型，1-充值优惠； 2-跳转官网；3-跳转店铺
 * "bid": 8997,//店铺id，type=3时使用
 * "sort": 1		//排序顺序，默认结果已由小到大排序
 */
public class BannerData extends BannerAdData {
    private String img;
    private int bid;
    private int sort;


    public BannerData(String img, int type) {
        this.img = img;
        this.type = type;
    }

    @Override
    public String getImg_url() {
        return img_url = img;
    }

    @Override
    public int getId() {
        return id = bid;
    }

    public int getSort() {
        return sort;
    }


}
