package teamkeropok.com.foodmagnet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import teamkeropok.com.foodmagnet.R;
import teamkeropok.com.foodmagnet.model.Comment;
import teamkeropok.com.foodmagnet.model.Kedai;
import teamkeropok.com.foodmagnet.model.Pengguna;

public class KedaiDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "KedaiDetailActivity";

    public static final String EXTRA_KEDAI_KEY = "kedai_key";

    private DatabaseReference mKedaiReference;
    private DatabaseReference mCommentsReference;
    private ValueEventListener mKedaiListener;
    private String mKedaiKey;
    private CommentAdapter mAdapter;

    private TextView mNamaKedaiView;
    private TextView mAlamatKedaiView;
    private TextView mJenisMakananView;
    private TextView mWaktuBukaView;
    private TextView mWaktuTutupView;
    private EditText mCommentField;
    private Button mCommentButton;
    private RecyclerView mCommentsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kedai_detail);

        // Get post key from intent
        mKedaiKey = getIntent().getStringExtra(EXTRA_KEDAI_KEY);
        if (mKedaiKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_KEDAI_KEY");
        }

        // Initialize Database
        mKedaiReference = FirebaseDatabase.getInstance().getReference()
                .child("Kedai").child(mKedaiKey);
        mCommentsReference = FirebaseDatabase.getInstance().getReference()
                .child("kedai-comments").child(mKedaiKey);

        // Initialize Views
        mNamaKedaiView = (TextView) findViewById(R.id.tv_nama_kedai);
        mAlamatKedaiView = (TextView) findViewById(R.id.tv_alamat_kedai);
        mJenisMakananView = (TextView) findViewById(R.id.tv_jenis_makanan);
        mWaktuBukaView = (TextView) findViewById(R.id.tv_waktu_operasi_buka);
        mWaktuTutupView = (TextView) findViewById(R.id.tv_waktu_operasi_tutup);
        mCommentField = (EditText) findViewById(R.id.field_comment_text);
        mCommentButton = (Button) findViewById(R.id.button_post_comment);
        mCommentsRecycler = (RecyclerView) findViewById(R.id.recycler_comments);

        // TODO: Replace star button from home page to this page
        mCommentButton.setOnClickListener(this);
        mCommentsRecycler.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the post
        // [START kedai_value_event_listener]

        ValueEventListener kedaiListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Kedai object and use the values to update the UI
                Kedai kedai = dataSnapshot.getValue(Kedai.class);
                // [START_EXCLUDE]
                mNamaKedaiView.setText(kedai.nama_kedai);
                mAlamatKedaiView.setText(kedai.alamat);
                mJenisMakananView.setText(kedai.jenis_makanan);
                mWaktuBukaView.setText(kedai.waktu_operasi_buka);
                mWaktuTutupView.setText(kedai.waktu_operasi_tutup);

                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Kedai failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(KedaiDetailActivity.this, "Failed to load kedai.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }

        };


        mKedaiReference.addValueEventListener(kedaiListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mKedaiListener = kedaiListener;

        // Listen for comments
        mAdapter = new CommentAdapter(this, mCommentsReference);
        mCommentsRecycler.setAdapter(mAdapter);

    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mKedaiListener != null) {
            mKedaiReference.removeEventListener(mKedaiListener);
        }

        // Clean up comments listener
        mAdapter.cleanupListener();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_post_comment) {
            postComment();
        }

    }

    private void postComment() {
        final String uid = getUid();
        FirebaseDatabase.getInstance().getReference().child("Pengguna").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user information
                        Pengguna pengguna = dataSnapshot.getValue(Pengguna.class);
                        String nNama_penguna = pengguna.nama_pengguna;

                        // Create new comment object
                        String commentText = mCommentField.getText().toString();
                        Comment comment = new Comment(uid, nNama_penguna, commentText);

                        // Push the comment, it will appear in the list
                        mCommentsReference.push().setValue(comment);

                        // Clear the field
                        mCommentField.setText(null);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    private static class CommentViewHolder extends RecyclerView.ViewHolder {

        public TextView penggunaView;
        public TextView commentView;

        public CommentViewHolder(View itemView) {
            super(itemView);

            penggunaView = (TextView) itemView.findViewById(R.id.comment_pengguna);
            commentView = (TextView) itemView.findViewById(R.id.comment_body);
        }
    }

    private static class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

        private Context mContext;
        private DatabaseReference mDatabaseReference;
        private ChildEventListener mChildEventListener;

        private List<String> mCommentIds = new ArrayList<>();
        private List<Comment> mComments = new ArrayList<>();

        public CommentAdapter(final Context context, DatabaseReference ref) {
            mContext = context;
            mDatabaseReference = ref;

            // Create child event listener
            // [START child_event_listener_recycler]
            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                    // A new comment has been added, add it to the displayed list
                    Comment comment = dataSnapshot.getValue(Comment.class);

                    // [START_EXCLUDE]
                    // Update RecyclerView
                    mCommentIds.add(dataSnapshot.getKey());
                    mComments.add(comment);
                    notifyItemInserted(mComments.size() - 1);
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so displayed the changed comment.
                    Comment newComment = dataSnapshot.getValue(Comment.class);
                    String commentKey = dataSnapshot.getKey();

                    // [START_EXCLUDE]
                    int commentIndex = mCommentIds.indexOf(commentKey);
                    if (commentIndex > -1) {
                        // Replace with the new data
                        mComments.set(commentIndex, newComment);

                        // Update the RecyclerView
                        notifyItemChanged(commentIndex);
                    } else {
                        Log.w(TAG, "onChildChanged:unknown_child:" + commentKey);
                    }
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so remove it.
                    String commentKey = dataSnapshot.getKey();

                    // [START_EXCLUDE]
                    int commentIndex = mCommentIds.indexOf(commentKey);
                    if (commentIndex > -1) {
                        // Remove data from the list
                        mCommentIds.remove(commentIndex);
                        mComments.remove(commentIndex);

                        // Update the RecyclerView
                        notifyItemRemoved(commentIndex);
                    } else {
                        Log.w(TAG, "onChildRemoved:unknown_child:" + commentKey);
                    }
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                    // A comment has changed position, use the key to determine if we are
                    // displaying this comment and if so move it.
                    Comment movedComment = dataSnapshot.getValue(Comment.class);
                    String commentKey = dataSnapshot.getKey();

                    // ...
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                    Toast.makeText(mContext, "Failed to load comments.",
                            Toast.LENGTH_SHORT).show();
                }
            };
            ref.addChildEventListener(childEventListener);
            // [END child_event_listener_recycler]

            // Store reference to listener so it can be removed on app stop
            mChildEventListener = childEventListener;
        }

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {
            Comment comment = mComments.get(position);
            holder.penggunaView.setText(comment.nama_pengomen);
            holder.commentView.setText(comment.text);
        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }

        public void cleanupListener() {
            if (mChildEventListener != null) {
                mDatabaseReference.removeEventListener(mChildEventListener);
            }
        }

    }

}
