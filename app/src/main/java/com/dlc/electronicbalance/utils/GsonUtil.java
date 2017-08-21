package com.dlc.electronicbalance.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * @date 2017/5/6
 * @autor KevinChung
 * @email KevinChung0769@gmail.com
 * @Description  gson 工具
 */

public class GsonUtil {

    private final static Gson mGson = new Gson();

    public static String gsonToString(Object object) {

        String gsonString = mGson.toJson(object);
        return gsonString;
    }

    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = mGson.fromJson(gsonString, cls);
        return t;
    }

    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list = mGson.fromJson(gsonString, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }


    public static <T> List<Map<String, T>> gsonToListMaps(
            String gsonString) {
        List<Map<String, T>> list = null;
        list = mGson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
        }.getType());
        return list;
    }

    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map = null;
        map = mGson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }


}
