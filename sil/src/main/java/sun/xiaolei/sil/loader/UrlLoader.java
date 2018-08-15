package sun.xiaolei.sil.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sun.xiaolei.sil.request.BitmapRequest;
import sun.xiaolei.sil.util.LogUtil;

/**
 * @author sun
 * @emil xiaoleisun92@gmail.com
 * description:
 */
public class UrlLoader extends AbsLoader {
    @Override
    public Bitmap onLoadImage(BitmapRequest request) {
        LogUtil.d("get image from url");
        Bitmap bitmap = null;
        try {
            URL url = new URL(request.imageUri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
