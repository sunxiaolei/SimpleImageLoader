package sun.xiaolei.sil.policy;

import sun.xiaolei.sil.request.BitmapRequest;

/**
 * @author sun
 * @emil xiaoleisun92@gmail.com
 * description:加载策略
 */
public interface LoadPolicy {

    public int compare(BitmapRequest request1, BitmapRequest request2);
}
