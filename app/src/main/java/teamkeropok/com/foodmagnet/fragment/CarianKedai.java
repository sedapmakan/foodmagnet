package teamkeropok.com.foodmagnet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import teamkeropok.com.foodmagnet.KedaiDetailActivity;
import teamkeropok.com.foodmagnet.R;
import teamkeropok.com.foodmagnet.model.Kedai;
import teamkeropok.com.foodmagnet.fragment.CarianKedaiFragment;
import teamkeropok.com.foodmagnet.viewholder.KedaiViewHolder;


/**
 * Created by 2016 on 23/4/2017.
 *
 * Kedai query based on jenis makanan
 * Kedai query based on lokasi kedai
 *
 *
 */
// TODO: Siapkan query kedai based
public class CarianKedai extends ListKedaiFragment {



    public CarianKedai() {
    }




/*

    @Override
    public View onCreateView (final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View searchView = inflater.inflate(R.layout.fragment_carian_kedai, container, false);


        public Spinner input_jenis_makanan;
        public Button button_carian_kedai;
        input_jenis_makanan = (Spinner) input_jenis_makanan.findViewById(R.id.spinnerJenisMakanan);
        button_carian_kedai = (Button) button_carian_kedai.findViewById(R.id.bt_carian_kedai);

        //button_carian_kedai.setOnClickListener(this);


        return searchView;

    }
*/



    public Query getQuery (DatabaseReference databaseReference){
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys

        Query jenismakananQuery = databaseReference.child("Kedai").orderByChild("jenis_makanan").equalTo("Western");


        return jenismakananQuery;

    }




/*
    @Override
    public void onClick(View v) {
        int i = v.getId();
            if (i == R.id.bt_carian_kedai) {
                cariKedai();
            }
        }



    private void cariKedai()
    {

        updateUI();

    }

    private void updateUI(){
        // Set up FirebaseRecyclerAdapter with the Query
        Query kedaiQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Kedai, KedaiViewHolder>(Kedai.class, R.layout.item_kedai,
                KedaiViewHolder.class, kedaiQuery) {
            @Override
            protected void populateViewHolder(final KedaiViewHolder viewHolder, final Kedai model, final int position) {
                final DatabaseReference kedaiRef = getRef(position);

                // Set click listener for the whole kedai view
                final String kedaiKey = kedaiRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch KedaiDetailActivity
                        Intent intent = new Intent(getActivity(), KedaiDetailActivity.class);
                        intent.putExtra(KedaiDetailActivity.EXTRA_KEDAI_KEY, kedaiKey);
                        startActivity(intent);
                    }
                });

            }
        };
        mRecycler.setAdapter(mAdapter);

    }
*/

}



