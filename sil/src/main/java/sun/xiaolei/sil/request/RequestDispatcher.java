package sun.xiaolei.sil.request;

import java.util.concurrent.BlockingQueue;

import sun.xiaolei.sil.loader.Loader;
import sun.xiaolei.sil.loader.LoaderManager;
import sun.xiaolei.sil.util.LogUtil;

/**
 * @author sun
 * @emil xiaoleisun92@gmail.com
 * description:
 */
public class RequestDispatcher extends Thread {

    /**
     * 网络请求队列
     */
    private BlockingQueue<BitmapRequest> mRequestQueue;


    /**
     * @param queue 图片加载请求队列
     */
    public RequestDispatcher(BlockingQueue<BitmapRequest> queue) {
        mRequestQueue = queue;
    }


    @Override
    public void run() {
        try {
            while (!this.isInterrupted()) {
                final BitmapRequest request = mRequestQueue.take();
                if (request.isCancel) {
                    continue;
                }
                // 解析图片schema
                final String schema = parseSchema(request.imageUri);
                // 根据schema获取对应的Loader
                Loader imageLoader = LoaderManager.getInstance().getLoader(schema);
                // 加载图片
                imageLoader.loadImage(request);
            }
        } catch (InterruptedException e) {
            LogUtil.d("### 请求分发器退出");
        }
    }


    /**
     * 这里是解析图片uri的格式,uri格式为: schema:// + 图片路径。
     */
    private String parseSchema(String uri) {
        if (uri.contains("://")) {
            return uri.split("://")[0];
        } else {
            LogUtil.e("### wrong scheme, image uri is : " + uri);
        }


        return "";
    }

}
