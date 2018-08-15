package sun.xiaolei.sil.policy;

import sun.xiaolei.sil.request.BitmapRequest;

/**
 * @author sun
 * @emil xiaoleisun92@gmail.com
 * description:顺序加载
 */
public class SerialPolicy implements LoadPolicy {
    @Override
    public int compare(BitmapRequest request1, BitmapRequest request2) {
        return request1.serialNum - request2.serialNum;
    }
}
