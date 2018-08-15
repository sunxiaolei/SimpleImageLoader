package sun.xiaolei.sil.policy;

import sun.xiaolei.sil.request.BitmapRequest;

/**
 * @author sun
 * @emil xiaoleisun92@gmail.com
 * description:逆序加载
 */
public class ReversePolicy implements LoadPolicy {
    @Override
    public int compare(BitmapRequest request1, BitmapRequest request2) {
        return request2.serialNum - request1.serialNum;
    }
}
