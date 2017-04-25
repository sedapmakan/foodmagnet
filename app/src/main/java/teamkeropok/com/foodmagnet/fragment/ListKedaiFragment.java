package teamkeropok.com.foodmagnet.fragment;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;

import teamkeropok.com.foodmagnet.KedaiDetailActivity;
import teamkeropok.com.foodmagnet.R;
import teamkeropok.com.foodmagnet.model.Kedai;
import teamkeropok.com.foodmagnet.viewholder.KedaiViewHolder;

/**
 * Created by 2016 on 25/4/2017.
 */

public abstract class ListKedaiFragment extends Fragment{

    private static final String TAG = "ListKedaiFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;



    private FirebaseRecyclerAdapter<Kedai, KedaiViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    ListKedaiFragment(){}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_kedai, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);


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

                // Determine if the current user has liked this kedai and set UI accordingly
                if (model.rating.containsKey(getUid())) {
                    viewHolder.iv_ratingView.setImageResource(R.drawable.ic_toggle_star_24);
                } else {
                    viewHolder.iv_ratingView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }
                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToKedai(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored
                        DatabaseReference globalPostRef = mDatabase.child("Kedai").child(kedaiRef.getKey());
                        DatabaseReference userPostRef = mDatabase.child("Kedai-Peniaga").child(model.IDkedai).child(kedaiRef.getKey());

                        // Run two transactions
                        onStarClicked(globalPostRef);
                        onStarClicked(userPostRef);
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    private void onStarClicked(DatabaseReference kedaiRef) {
        kedaiRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Kedai p = mutableData.getValue(Kedai.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.rating.containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    p.ratingCount = p.ratingCount - 1;
                    p.rating.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    p.ratingCount = p.ratingCount + 1;
                    p.rating.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
    // [END kedai_rating_transaction]

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}