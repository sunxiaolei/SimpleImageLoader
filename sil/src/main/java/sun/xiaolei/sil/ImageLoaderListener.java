package sun.xiaolei.sil;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * @author sun
 * @emil xiaoleisun92@gmail.com
 * description:
 */
public interface ImageLoaderListener {
    public void onComplete(ImageView imageView, Bitmap bitmap, String uri);
}
