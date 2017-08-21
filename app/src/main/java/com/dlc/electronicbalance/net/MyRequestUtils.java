package com.dlc.electronicbalance.net;

import com.dlc.electronicbalance.bean.GetGoodsBean;
import com.dlc.electronicbalance.bean.GetGroupBean;
import com.dlc.electronicbalance.bean.UserInfoBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by YoungeTao on 2017/8/19
 * QQ 2276559259.
 * gmail youngetao@gmail.com
 */

public class MyRequestUtils extends HttpUtil{

    private MyRequestUtils() {
    }

    private static class InstanceHolder {
        private static MyRequestUtils sProtocol = new MyRequestUtils();
    }

    public static MyRequestUtils get() {
        return InstanceHolder.sProtocol;
    }

    /**
     * 登录
     * @param type
     * @param password
     * @return
     */
    public Observable<UserInfoBean> login(String type,String password) {
        Map<String,String> httpParams = new HashMap<>();
        httpParams.put("api_name", "login");
        httpParams.put("in_text", password);
        httpParams.put("type", type);
        return rxPost(httpParams, UserInfoBean.class,"",null);
    }

    /**
     *
     * @return
     */
    public Observable<GetGroupBean> getGroup() {
        Map<String,String> httpParams = new HashMap<>();
        httpParams.put("api_name", "get_group");
        return rxPost(httpParams, GetGroupBean.class,"",null);
    }

    /**
     *
     * @return
     */
    public Observable<GetGoodsBean> get_goods(String cate_id) {
        Map<String,String> httpParams = new HashMap<>();
        httpParams.put("api_name", "get_goods");
        httpParams.put("cate_id", cate_id);
        return rxPost(httpParams, GetGoodsBean.class,"",null);
    }
}
