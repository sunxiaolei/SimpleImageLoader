package sun.xiaolei.sil.cache;

import android.graphics.Bitmap;

import sun.xiaolei.sil.request.BitmapRequest;

/**
 * @author sun
 * description:
 */
public interface ImageCache {

    Bitmap get(BitmapRequest request);

    void put(BitmapRequest request, Bitmap bitmap);
}
