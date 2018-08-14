package sun.xiaolei.simpleimageloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import sun.xiaolei.sil.ImageLoader;
import sun.xiaolei.sil.cache.DoubleCache;
import sun.xiaolei.sil.config.ImageLoaderConfig;

public class MainActivity extends AppCompatActivity {

    public static String IMG_ANDROID_P = "https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=c7c6e5b474f0f736ccf344536b3cd87c/b7003af33a87e950598820a71c385343faf2b45a.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoader.getInstance().init(
                new ImageLoaderConfig.Builder(this)
                        .setCache(new DoubleCache.Builder(this)
                                .setDiskPath("sil_cache")
                                .create())
                        .create());


        Button btn = findViewById(R.id.btn_load);
        final ImageView ivTest = findViewById(R.id.iv_test);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.getInstance()
                        .load(IMG_ANDROID_P)
                        .into(ivTest);
            }
        });
    }
}
