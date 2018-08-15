package sun.xiaolei.sil.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import sun.xiaolei.sil.ImageLoader;
import sun.xiaolei.sil.cache.ImageCache;
import sun.xiaolei.sil.request.BitmapRequest;
import sun.xiaolei.sil.util.LogUtil;

/**
 * @author sun
 * @emil xiaoleisun92@gmail.com
 * description:
 */
public abstract class AbsLoader implements Loader {

    private static ImageCache mCache = ImageLoader.getInstance().getConfig().getCache();


    @Override
    public void loadImage(BitmapRequest request) {
        //先从缓存取
        Bitmap bitmap = mCache.get(request);
        if (bitmap == null) {
            bitmap = onLoadImage(request);
        }
        mCache.put(request, bitmap);
        deliveryToUIThread(request, bitmap);
    }

    private void deliveryToUIThread(BitmapRequest request, final Bitmap bitmap) {
        final ImageView imageView = request.getImageView();
        if (imageView == null || bitmap == null) {
            return;
        }
        imageView.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    public abstract Bitmap onLoadImage(BitmapRequest result);
}
