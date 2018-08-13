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
    /**
     * 线程池
     */
    private ExecutorService mExecutorService;

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
        mExecutorService = Executors.newFixedThreadPool(mConfig.getThreadCount());
        mImageCache = config.getCachePolicy();
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

    private Bitmap downloadImage(String imgUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void submitTask(final String url, final ImageView iv) {
        iv.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                LogUtil.d("download image");
                final Bitmap bitmap = downloadImage(url);
                if (bitmap != null) {
                    mImageCache.put(url, bitmap);
                    if (url.equals(iv.getTag())) {
                        iv.post(new Runnable() {
                            @Override
                            public void run() {
                                iv.setImageBitmap(bitmap);
                            }
                        });
                    }
                } else {
                    if (errorRes != 0) {
                        iv.setImageResource(errorRes);
                    }
                }
            }
        });
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
        Bitmap bitmap = mImageCache.get(url);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
            return;
        }
        submitTask(url, iv);
    }

}
