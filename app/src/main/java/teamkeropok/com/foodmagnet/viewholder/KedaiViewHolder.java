package teamkeropok.com.foodmagnet.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import teamkeropok.com.foodmagnet.R;
import teamkeropok.com.foodmagnet.model.Kedai;

/**
 * Created by 2016 on 25/4/2017.
 *
 *
 * Kedai view holder , hold view on home page. please take note on putting any data
 */




public class KedaiViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_nama_kedai;
    public TextView tv_jenis_makanan;
    public TextView tv_waktu_operasi_buka;
    public TextView tv_waktu_operasi_tutup;
    public ImageView iv_ratingView;
    public TextView tv_ratingView;



    public KedaiViewHolder(View itemView) {
        super(itemView);
        tv_nama_kedai = (TextView) itemView.findViewById(R.id.tv_nama_kedai);
        tv_jenis_makanan = (TextView) itemView.findViewById(R.id.tv_jenis_makanan);
        tv_waktu_operasi_buka = (TextView) itemView.findViewById(R.id.tv_waktu_operasi_buka);
        tv_waktu_operasi_tutup = (TextView) itemView.findViewById(R.id.tv_waktu_operasi_tutup);
        iv_ratingView = (ImageView) itemView.findViewById(R.id.iv_rating);
        tv_ratingView = (TextView) itemView.findViewById(R.id.kedai_num_rating);

    }


    public void bindToKedai(Kedai kedai, View.OnClickListener starClickListener) {
        tv_nama_kedai.setText(kedai.nama_kedai);
        tv_jenis_makanan.setText(kedai.jenis_makanan);
        tv_waktu_operasi_buka.setText(kedai.waktu_operasi_buka);
        tv_waktu_operasi_tutup.setText(kedai.waktu_operasi_tutup);
        // TODO: Gambar kedai kena bind sekali untuk prompt output kat menu utama
        tv_ratingView.setText(String.valueOf(kedai.ratingCount));
        iv_ratingView.setOnClickListener(starClickListener);

    }
}
