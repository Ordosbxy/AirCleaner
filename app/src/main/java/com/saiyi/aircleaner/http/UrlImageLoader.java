package com.saiyi.aircleaner.http;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

/**
 * 文件描述：根据Url来下载图片的图片导入器，继承于ImageLoader
 * 创建作者：机器人
 * 创建时间：16/5/8
 */
public class UrlImageLoader extends ImageLoader{


    public UrlImageLoader(RequestQueue queue, ImageCache imageCache) {
        super(queue, imageCache);
    }

    /**
     * 根据Url将下载完的图片直接导入到ImageView
     * @param url 图片地址
     * @param imageView 需要加载的ImageView实例
     * @param resId 下载图片失败的图片资源Id
     */
    public void loadImageVieWithUrl(String url,final ImageView imageView,int resId) {
        get(url,new DefaultImageListener(imageView,resId));
    }

    /**
     * 默认的图片加载监听器
     */
    class DefaultImageListener implements ImageListener {

        //加载图片失败的默认图片资源Id
        private int mFailResId;
        //需要加载的ImageView实例
        private ImageView mImageView;

        public DefaultImageListener(ImageView imageView,int failResId) {
            mFailResId = failResId;
            mImageView = imageView;
        }

        @Override
        public void onResponse(ImageContainer imageContainer, boolean b) {
            final Bitmap bitmap = imageContainer.getBitmap();
            if(bitmap != null) {
                mImageView.setImageBitmap(bitmap);
            } else {
                mImageView.setBackgroundResource(mFailResId);
            }
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            mImageView.setBackgroundResource(mFailResId);
        }
    }
}
