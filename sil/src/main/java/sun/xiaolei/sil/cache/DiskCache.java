package sun.xiaolei.sil.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import sun.xiaolei.sil.ImageLoader;
import sun.xiaolei.sil.diskcache.DiskLruCache;
import sun.xiaolei.sil.request.BitmapRequest;
import sun.xiaolei.sil.util.LogUtil;
import sun.xiaolei.sil.util.Md5Util;

import static android.os.Environment.isExternalStorageRemovable;

/**
 * @author sun
 * description:磁盘缓存
 */
public class DiskCache implements ImageCache {

    private DiskLruCache mDiskCache;

    private String diskPath;

    private long cacheMaxSize;//10M;

    public DiskCache(Context mContext, long cacheMaxSize, String diskPath) {
        File cacheFile = getDiskCacheDir(mContext, diskPath);
        try {
            mDiskCache = DiskLruCache.open(cacheFile, 1, 1, cacheMaxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        //如果已存满或者外存储被移除，缓存目录=context.getCacheDir().getPath()即/data/data/package_name/cache
        //反之缓存目录=context.getExternalCacheDir().getPath()，即 /storage/emulated/0/Android/data/package_name/cache
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !isExternalStorageRemovable()
                ? context.getExternalCacheDir().getPath()
                : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        Bitmap bitmap;
        try {
            DiskLruCache.Snapshot snapshot = mDiskCache.get(request.imageUriMd5);
            if (snapshot != null) {
                LogUtil.d("get image from disk cache");
                bitmap = BitmapFactory.decodeStream(snapshot.getInputStream(0));
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        try {
            //URL经过MD5加密生成唯一的key值，避免URL中可能含有非法字符问题
            DiskLruCache.Editor editor = mDiskCache.edit(request.imageUriMd5);
            OutputStream os = editor.newOutputStream(0);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            os.write(baos.toByteArray());
            os.close();
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Builder {

        private Context context;

        private String diskPath = "il_def_cache";

        private long cacheMaxSize = 1024 * 1024 * 10;//10M;

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

        public DiskCache create() {
            DiskCache cache = new DiskCache(context, cacheMaxSize, diskPath);
            return cache;
        }
    }
}
