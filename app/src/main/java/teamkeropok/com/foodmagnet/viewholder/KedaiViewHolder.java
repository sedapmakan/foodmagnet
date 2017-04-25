package teamkeropok.com.foodmagnet.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import teamkeropok.com.foodmagnet.R;
import teamkeropok.com.foodmagnet.model.Kedai;

/**
 * Created by 2016 on 25/4/2017.
 */




public class KedaiViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_nama_kedai;
    public TextView tv_jenis_makanan;
    public ImageView iv_ratingView;
    public TextView tv_ratingView;



    public KedaiViewHolder(View itemView) {
        super(itemView);
        tv_nama_kedai = (TextView) itemView.findViewById(R.id.tv_nama_kedai);
        tv_jenis_makanan = (TextView) itemView.findViewById(R.id.tv_jenis_makanan);
        iv_ratingView = (ImageView) itemView.findViewById(R.id.iv_rating);
        tv_ratingView = (TextView) itemView.findViewById(R.id.kedai_num_rating);


    }

    public void bindToKedai(Kedai kedai, View.OnClickListener starClickListener) {
        tv_nama_kedai.setText(kedai.nama_kedai);
        tv_jenis_makanan.setText(kedai.jenis_makanan);
        tv_ratingView.setText(String.valueOf(kedai.ratingCount));

        iv_ratingView.setOnClickListener(starClickListener);

    }
}
