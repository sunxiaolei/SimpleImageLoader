package sun.xiaolei.sil.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import sun.xiaolei.sil.ImageLoader;
import sun.xiaolei.sil.ImageLoaderListener;
import sun.xiaolei.sil.config.DisplayConfig;
import sun.xiaolei.sil.policy.LoadPolicy;
import sun.xiaolei.sil.util.Md5Util;

/**
 * @author sun
 * @emil xiaoleisun92@gmail.com
 * description:
 */
public class BitmapRequest implements Comparable<BitmapRequest> {

    Reference<ImageView> mImageViewRef;
    public DisplayConfig displayConfig;
    public ImageLoaderListener imageListener;
    public String imageUri = "";
    public String imageUriMd5 = "";
    /**
     * 请求序列号
     */
    public int serialNum = 0;
    /**
     * 是否取消该请求
     */
    public boolean isCancel = false;

    /**
     *
     */
    public boolean justCacheInMem = false;

    /**
     * 加载策略
     */
    LoadPolicy mLoadPolicy = ImageLoader.getInstance().getConfig().getPolicy();

    /**
     * @param imageView
     * @param uri
     * @param config
     * @param listener
     */
    public BitmapRequest(ImageView imageView, String uri, DisplayConfig config, ImageLoaderListener listener) {
        mImageViewRef = new WeakReference<ImageView>(imageView);
        displayConfig = config;
        imageListener = listener;
        imageUri = uri;
        imageView.setTag(uri);
        imageUriMd5 = Md5Util.toMd5(imageUri);
    }

    /**
     * @param policy
     */
    public void setLoadPolicy(LoadPolicy policy) {
        if (policy != null) {
            mLoadPolicy = policy;
        }
    }

    /**
     * 判断imageview的tag与uri是否相等
     *
     * @return
     */
    public boolean isImageViewTagValid() {
        return mImageViewRef.get() != null ? mImageViewRef.get().getTag().equals(imageUri) : false;
    }

    public ImageView getImageView() {
        return mImageViewRef.get();
    }

    @Override
    public int compareTo(BitmapRequest another) {
        return mLoadPolicy.compare(this, another);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((imageUri == null) ? 0 : imageUri.hashCode());
        result = prime * result + ((mImageViewRef == null) ? 0 : mImageViewRef.get().hashCode());
        result = prime * result + serialNum;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BitmapRequest other = (BitmapRequest) obj;
        if (imageUri == null) {
            if (other.imageUri != null)
                return false;
        } else if (!imageUri.equals(other.imageUri))
            return false;
        if (mImageViewRef == null) {
            if (other.mImageViewRef != null)
                return false;
        } else if (!mImageViewRef.get().equals(other.mImageViewRef.get()))
            return false;
        if (serialNum != other.serialNum)
            return false;
        return true;
    }
}
