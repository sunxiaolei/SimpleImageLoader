package sun.xiaolei.sil.config;

import android.content.Context;
import android.text.TextUtils;

import sun.xiaolei.sil.cache.DoubleCache;
import sun.xiaolei.sil.cache.ImageCache;
import sun.xiaolei.sil.policy.LoadPolicy;

/*
 * @author sun
 * description:配置
 */
public class ImageLoaderConfig {

    private Context context;

    private int threadCount;

    private ImageCache cache;

    private String diskPath;

    private long cacheMaxSize;

    private LoadPolicy policy;

    public ImageLoaderConfig(Context context, int threads, ImageCache cache, LoadPolicy policy) {
        this.context = context;
        this.cache = cache;
        this.policy = policy;
        this.threadCount = threads;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public ImageCache getCache() {
        return cache;
    }

    public void setCache(ImageCache cache) {
        this.cache = cache;
    }

    public LoadPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(LoadPolicy policy) {
        this.policy = policy;
    }

    public static class Builder {

        private Context context;

        private int threadCount = Runtime.getRuntime().availableProcessors();//线程池数量

        private ImageCache cache;

        private LoadPolicy policy;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setThreadCount(int num) {
            if (num > 0) {
                this.threadCount = num;
            }
            return this;
        }

        public Builder setCache(ImageCache cache) {
            this.cache = cache;
            return this;
        }

        public Builder setPolicy(LoadPolicy policy) {
            this.policy = policy;
            return this;
        }

        public ImageLoaderConfig create() {
            ImageLoaderConfig config = new ImageLoaderConfig(context, threadCount, cache, policy);
            return config;
        }

    }
}
