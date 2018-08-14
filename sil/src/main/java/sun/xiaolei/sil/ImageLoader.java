package sun.xiaolei.sil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sun.xiaolei.sil.cache.ImageCache;
import sun.xiaolei.sil.config.ImageLoaderConfig;
import sun.xiaolei.sil.request.BitmapRequest;
import sun.xiaolei.sil.request.RequestQueue;
import sun.xiaolei.sil.util.LogUtil;

/*
 * @author sun
 * description:
 */
public class ImageLoader {

    private static Context mContext;
    private static ImageLoaderConfig mConfig;
    /**
     * 图片缓存
     */
    private ImageCache mImageCache;

    private RequestQueue mRequestQueue;

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static final ImageLoader instance = new ImageLoader();
    }

    public void init(ImageLoaderConfig config) {
        mContext = config.getContext();
        mConfig = config;
        mImageCache = config.getCache();
        mRequestQueue = new RequestQueue(mConfig.getThreadCount());
        mRequestQueue.start();
    }

    public static Context getContext() {
        if (mContext == null) {
            throw new NullPointerException("should init first");
        }
        return mContext;
    }

    public static ImageLoaderConfig getConfig() {
        if (mConfig == null) {
            throw new NullPointerException("should init first");
        }
        return mConfig;
    }

    private String url;
    private int placeRes;
    private int errorRes;

    public ImageLoader load(String url) {
        this.url = url;
        return this;
    }

    public ImageLoader placeholder(int res) {
        this.placeRes = res;
        return this;
    }

    public ImageLoader error(int res) {
        this.errorRes = res;
        return this;
    }

    public void into(ImageView iv) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (placeRes != 0) {
            iv.setImageResource(placeRes);
        }
        BitmapRequest request = new BitmapRequest(iv, url, null, null);
        mRequestQueue.addRequest(request);
    }

}
