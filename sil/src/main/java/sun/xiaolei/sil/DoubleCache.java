package sun.xiaolei.sil;

import android.graphics.Bitmap;

/*
 * @author sun
 * description:内存+磁盘缓存
 */
public class DoubleCache implements ImageCache {

    private MemoryCache memoryCache;
    private DiskCache diskCache;

    public DoubleCache() {
        memoryCache = new MemoryCache();
        diskCache = new DiskCache();
    }

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null) {
            return bitmap;
        }
        bitmap = diskCache.get(url);
        memoryCache.put(url, bitmap);
        return bitmap;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        memoryCache.put(url, bitmap);
        diskCache.put(url, bitmap);
    }
}
