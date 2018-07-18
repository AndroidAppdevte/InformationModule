package com.scqkzqtz.information.utils;

import android.util.Log;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.HashMap;
import java.util.List;

/**
 * 收藏工具类
 * Created by hef on 2017/6/29.
 */

public class CollectionUtil {

    public interface OnCollectionListener {
        void onCollection(List<AVObject> list, Exception e);
    }

    /**
     * 查询收藏
     *
     * @param courseId 课程ID
     */
    public static void queryCollect(String courseId, final OnCollectionListener onCollectionListener) {
        AVQuery<AVObject> query = AVQuery.getQuery("A_DxtMyCollection");
        query.whereEqualTo("userObjectId", AVUser.getCurrentUser() == null ? "" : AVUser.getCurrentUser().getObjectId());
        query.whereEqualTo("collectObjectId", courseId);
//        query.whereEqualTo("status",1);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (onCollectionListener != null) {
                    onCollectionListener.onCollection(list, e);
                }
            }
        });
    }
//
//    /**
//     * 查询服务视频收藏
//     *
//     * @param courseId 课程ID
//     */
//    public static void queryServiceVideoCollect(String courseId, final OnCollectionListener onCollectionListener) {
//        AVQuery<AVObject> query = AVQuery.getQuery("A_DxtMyCollection");
//        query.whereEqualTo("userObjectId", AVUser.getCurrentUser() == null ? "" : AVUser.getCurrentUser().getObjectId());
//        query.whereEqualTo("collectObjectId", courseId);//collectType=stockteam_video
////        query.whereEqualTo("status",1);
//        query.findInBackground(new FindCallback<AVObject>() {
//            @Override
//            public void done(List<AVObject> list, AVException e) {
//                if (onCollectionListener != null) {
//                    onCollectionListener.onCollection(list, e);
//                }
//            }
//        });
//    }



    /**
     * 添加收藏
     *
     * @param avObject
     * @param collectObjectId      课程objectId
     * @param onCollectionListener 回调
     */
    public static void addCollection(AVObject avObject, String collectObjectId, String type, final OnCollectionListener onCollectionListener) {
        if (avObject == null) {
            HashMap<String, Object> map = new HashMap<>();
//            map.put("userObjectId",AVUser.getCurrentUser()!=null ? AVUser.getCurrentUser().getObjectId():"");
            map.put("collectObjectId", collectObjectId);
            map.put("type", type);

            AVCloud.callFunctionInBackground("A_DxtMyCollection", map, new FunctionCallback<Object>() {
                @Override
                public void done(Object o, AVException e) {
                    try {
                        Log.i("addCollection", "addCollection: " + o);
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) o;
                        HashMap<String, Object> data = (HashMap<String, Object>) hashMap.get("data");
                        String objectId = data.get("objectId").toString();
                        AVObject avObject1 = AVObject.createWithoutData("A_DxtMyCollection", objectId);
                        avObject1.put("status", 1);
                        avObject1.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (onCollectionListener != null) {
                                    onCollectionListener.onCollection(null, e);
                                }
                            }
                        });
                    } catch (Exception e1) {
                        if (onCollectionListener != null) {
                            onCollectionListener.onCollection(null, e1);
                        }
                    }
                }
            });
        } else {
            avObject.put("status", 1);
            avObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (onCollectionListener != null) {
                        onCollectionListener.onCollection(null, e);
                    }
                }
            });
        }
    }

    /**
     * 添加服务视频收藏
     *
     * @param avObject
     * @param collectObjectId      该条数据的objectId
     * @param onCollectionListener 回调
     */
    public static void addServiceVideoCollection(AVObject avObject, String collectObjectId, final OnCollectionListener onCollectionListener) {
        if (avObject == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("collectObjectId", collectObjectId);
            map.put("type", "stockteam_video");

            AVCloud.callFunctionInBackground("A_DxtMyCollection", map, new FunctionCallback<Object>() {
                @Override
                public void done(Object o, AVException e) {
                    try {
                        Log.i("addCollection", "addCollection: " + o);
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) o;
                        HashMap<String, Object> data = (HashMap<String, Object>) hashMap.get("data");
                        String objectId = data.get("objectId").toString();
                        AVObject avObject1 = AVObject.createWithoutData("A_DxtMyCollection", objectId);
                        avObject1.put("status", 1);
                        avObject1.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (onCollectionListener != null) {
                                    onCollectionListener.onCollection(null, e);
                                }
                            }
                        });
                    } catch (Exception e1) {
                        if (onCollectionListener != null) {
                            onCollectionListener.onCollection(null, e1);
                        }
                    }
                }
            });
        } else {
            avObject.put("status", 1);
            avObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (onCollectionListener != null) {
                        onCollectionListener.onCollection(null, e);
                    }
                }
            });
        }
    }

    /**
     * 取消收藏
     *
     * @param avObject             已收藏返回的avObject
     * @param onCollectionListener 回调
     */
    public static void deleteCollection(AVObject avObject, final OnCollectionListener onCollectionListener) {
        avObject.put("status", -1);
        avObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (onCollectionListener != null)
                    onCollectionListener.onCollection(null, e);
            }
        });
    }

}
