package com.scqkzqtz.information.utils;


import android.util.Log;


import com.scqkzqtz.information.entity.DBOperationSite;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * 运营位工具
 * Created by Administrator on 2017/3/22.
 */
public class OperationSiteUtils {
    public interface HttpListener {
        void succeed(List<DBOperationSite> list, String[] images);
    }

    public interface HttpListListener {
        void succeed(List<DBOperationSite> list);
    }

    /**
     * 查询运营位
     */
    public static void queryOperationSite(String s, HttpListener httpListener) {
        Realm realm = Realm.getDefaultInstance();
        List<DBOperationSite> list = new ArrayList<>();

        //先查version=roleId 数据
        List<DBOperationSite> listTemp = realm.where(DBOperationSite.class)
                .equalTo("sn", s)
                .equalTo("version", "").findAll();
        if (listTemp != null && listTemp.size() > 0) {
            list.clear();
            list.addAll(listTemp);
            Log.i("log", "queryOperationSite1: "+list);
        }

        //未查到数据,查询version=0数据
        if(list.size()<=0){
            list.addAll(realm.where(DBOperationSite.class).equalTo("sn", s).equalTo("version", 0).findAll());
            Log.i("log", "queryOperationSite2: "+list);
        }

        String[] images = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            images[i] = list.get(i).getThumbnail();
        }
        httpListener.succeed(list, images);
    }
}
