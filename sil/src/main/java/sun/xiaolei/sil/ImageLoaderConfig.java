package sun.xiaolei.sil;

import android.content.Context;
import android.text.TextUtils;

/*
 * @author sun
 * description:配置
 */
public class ImageLoaderConfig {

    private Context context;

    private int threadCount;

    private ImageCache cachePolicy;

    private String diskPath;

    private long cacheMaxSize;

    public ImageLoaderConfig(Context context, int threads, ImageCache policy, String diskPath, long cacheMaxSize) {
        this.context = context;
        this.diskPath = diskPath;
        this.cachePolicy = policy;
        this.threadCount = threads;
        this.cacheMaxSize = cacheMaxSize;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getDiskPath() {
        return diskPath;
    }

    public void setDiskPath(String diskPath) {
        this.diskPath = diskPath;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public long getCacheMaxSize() {
        return cacheMaxSize;
    }

    public void setCacheMaxSize(long cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
    }

    public ImageCache getCachePolicy() {
        return cachePolicy == null ? new DoubleCache() : cachePolicy;
    }

    public void setCachePolicy(ImageCache cachePolicy) {
        this.cachePolicy = cachePolicy;
    }

    public static class Builder {

        private Context context;

        private int threadCount = Runtime.getRuntime().availableProcessors();//线程池数量

        private ImageCache cachePolicy;

        private String diskPath = "il_def_cache";

        private long cacheMaxSize = 1024 * 1024 * 10;//10M;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setThreadCount(int num) {
            if (num > 0) {
                this.threadCount = num;
            }
            return this;
        }

        public Builder setCachePolicy(ImageCache cachePolicy) {
            this.cachePolicy = cachePolicy;
            return this;
        }

        public Builder setDiskPath(String path) {
            if (!TextUtils.isEmpty(path)) {
                this.diskPath = path;
            }
            return this;
        }

        public Builder setCacheMaxSize(long cacheMaxSize) {
            if (cacheMaxSize > 0) {
                this.cacheMaxSize = cacheMaxSize;
            }
            return this;
        }

        public ImageLoaderConfig create() {
            ImageLoaderConfig config = new ImageLoaderConfig(context, threadCount, cachePolicy, diskPath, cacheMaxSize);
            return config;
        }

    }
}
