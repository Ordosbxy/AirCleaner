package com.saiyi.aircleaner.http;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * 文件描述：Http请求图片时缓存类，主要用于导入图片时使用，开发者不需要主动调用他
 * 创建作者：机器人
 * 创建时间：16/5/8
 */
public class HttpBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache{


    public HttpBitmapCache() {
        super(getDefaultCacheSize());
    }

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public HttpBitmapCache(int maxSize) {
        super(maxSize);
    }

    public static int getDefaultCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        return cacheSize;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
