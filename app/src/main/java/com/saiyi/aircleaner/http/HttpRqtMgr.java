package com.saiyi.aircleaner.http;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * 文件描述：http请求管理器，该类主要有发起一个http请求的方法和获取图片导入器
 * 创建作者：机器人
 * 创建时间：16/4/25
 */
public class HttpRqtMgr {

    //声明一个请求队列
    private RequestQueue mRequestQueue;
    //图片导入器
    private  UrlImageLoader mImageLoader;

    private static HttpRqtMgr ourInstance = new HttpRqtMgr();

    public static HttpRqtMgr instance() {
        return ourInstance;
    }

    private HttpRqtMgr() {
    }

    /**
     * 初始化
     * @param context
     */
    public  void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new UrlImageLoader(mRequestQueue,new HttpBitmapCache());
    }

    /**
     * 根据Url将下载完的图片直接导入到ImageView
     * @param url 图片地址
     * @param imageView 需要加载的ImageView实例
     * @param resId 下载图片失败的图片资源Id
     */
    public  void loadImageView(String url, final ImageView imageView, int resId) {
        mImageLoader.loadImageVieWithUrl(url,imageView,resId);
    }

    /**
     * 获取请求队列
     * @return 请求队列
     */
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    /**
     * 获取图片导入器
     * @return ImageLoader
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    /**
     * 添加一个请求到请求队列中并自动开始发起请求
     * @param req 请求
     * @param tag 请求对应的名字
     * @param <T> 泛型
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        mRequestQueue.add(req);
    }

    /**
     * 根据请求名来取消对应的请求
     * @param tag 请求名
     */
    public void cancelRequests(String tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
