package sun.xiaolei.simpleimageloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import sun.xiaolei.sil.ImageLoader;

/**
 * @author sun
 * @emil xiaoleisun92@gmail.com
 * description:
 */
public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ImgHolder> {

    private List<String> imgs;

    private Context mContext;

    public ImgAdapter(Context mContext, List<String> imgs) {
        this.mContext = mContext;
        this.imgs = imgs;
    }

    @NonNull
    @Override
    public ImgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImgHolder(LayoutInflater.from(mContext).inflate(R.layout.item_img, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImgHolder holder, int position) {
        ImageLoader.getInstance()
                .load(imgs.get(position))
                .into(holder.ivImg);
    }

    @Override
    public int getItemCount() {
        return imgs == null ? 0 : imgs.size();
    }

    class ImgHolder extends RecyclerView.ViewHolder {

        public ImgHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
        }

        ImageView ivImg;
    }
}
