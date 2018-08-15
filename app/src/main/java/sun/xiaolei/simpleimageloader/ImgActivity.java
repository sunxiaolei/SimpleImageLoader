package sun.xiaolei.simpleimageloader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;


/**
 * @author sun
 * @emil xiaoleisun92@gmail.com
 * description:
 */
public class ImgActivity extends AppCompatActivity {

    static String[] imgUrls = {

    };

    private RecyclerView mRecyclerView;
    private ImgAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ImgAdapter(this, Arrays.asList(imgUrls));
        mRecyclerView.setAdapter(mAdapter);

    }
}
