package sun.xiaolei.simpleimageloader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sun.xiaolei.sil.ImageLoader;
import sun.xiaolei.sil.cache.DoubleCache;
import sun.xiaolei.sil.config.ImageLoaderConfig;
import sun.xiaolei.sil.policy.ReversePolicy;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoader.getInstance().init(
                new ImageLoaderConfig.Builder(this)
                        .setCache(new DoubleCache.Builder(this)
                                .setDiskPath("sil_cache")
                                .create())
                        .setPolicy(new ReversePolicy())
                        .create());


        Button btn = findViewById(R.id.btn_load);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ImgActivity.class));
            }
        });
    }
}
