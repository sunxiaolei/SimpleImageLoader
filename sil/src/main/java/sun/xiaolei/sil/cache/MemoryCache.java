package sun.xiaolei.sil.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import sun.xiaolei.sil.request.BitmapRequest;
import sun.xiaolei.sil.util.LogUtil;

/**
 * @author sun
 * description:内存缓存
 */
public class MemoryCache implements ImageCache {

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCache() {
        //最大内存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //取四分之一作为缓存
        int cacheSize = maxMemory / 4;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            //重写sizeOf方法，计算出要缓存的每张图片的大小。
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        if (request != null) {
            Bitmap bitmap = mMemoryCache.get(request.imageUri);
            if (bitmap != null) {
                LogUtil.d("get image from memory cache");
            }
            return bitmap;
        }
        return null;
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        if (request == null || bitmap == null) {
            return;
        }
        mMemoryCache.put(request.imageUri, bitmap);
    }
}
