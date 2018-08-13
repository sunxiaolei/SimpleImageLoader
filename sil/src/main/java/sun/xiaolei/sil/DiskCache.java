package sun.xiaolei.sil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static android.os.Environment.isExternalStorageRemovable;

/*
 * @author sun
 * description:磁盘缓存
 */
public class DiskCache implements ImageCache {

    private DiskLruCache mDiskCache;

    public DiskCache() {
        File cacheFile = getDiskCacheDir(ImageLoader.getContext(), ImageLoader.getConfig().getDiskPath());
        try {
            mDiskCache = DiskLruCache.open(cacheFile, 1, 1, ImageLoader.getConfig().getCacheMaxSize());
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
    public Bitmap get(String url) {
        Bitmap bitmap;
        try {
            DiskLruCache.Snapshot snapshot = mDiskCache.get(Util.hashKeyForDisk(url));
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
    public void put(String url, Bitmap bitmap) {
        try {
            //URL经过MD5加密生成唯一的key值，避免URL中可能含有非法字符问题
            DiskLruCache.Editor editor = mDiskCache.edit(Util.hashKeyForDisk(url));
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
}
