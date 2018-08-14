package sun.xiaolei.sil.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import sun.xiaolei.sil.request.BitmapRequest;

/**
 * @author sun
 * description:内存+磁盘缓存
 */
public class DoubleCache implements ImageCache {

    private MemoryCache memoryCache;
    private DiskCache diskCache;

    public DoubleCache(Context mContext, long cacheMaxSize, String diskPath) {
        memoryCache = new MemoryCache();
        diskCache = new DiskCache(mContext, cacheMaxSize, diskPath);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        Bitmap bitmap = memoryCache.get(request);
        if (bitmap != null) {
            return bitmap;
        }
        bitmap = diskCache.get(request);
        memoryCache.put(request, bitmap);
        return bitmap;
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        memoryCache.put(request, bitmap);
        diskCache.put(request, bitmap);
    }

    public static class Builder {

        private Context context;

        private String diskPath = "il_def_cache";

        private long cacheMaxSize = 1024 * 1024 * 10;//10M;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setDiskPath(String path) {
            if (!TextUtils.isEmpty(path)) {
                this.diskPath = path;
            }
            return this;
        }

        public Builder setCacheMaxSize(long size) {
            if (size > 0) {
                this.cacheMaxSize = size;
            }
            return this;
        }

        public DoubleCache create() {
            DoubleCache cache = new DoubleCache(context, cacheMaxSize, diskPath);
            return cache;
        }
    }
}
