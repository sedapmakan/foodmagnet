package teamkeropok.com.foodmagnet;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


import teamkeropok.com.foodmagnet.model.Kedai;
import teamkeropok.com.foodmagnet.model.Pengguna;

import java.util.HashMap;
import java.util.Map;


public class TambahKedai extends BaseActivity {

    //we will use these constants later to pass the artist name and id to another activity
    private static final String TAG = "TambahKedaiActivity";
    private static final String REQUIRED = "Required";


    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    //declare view
    //private TextView tv_nama_pengguna;
    private EditText input_nama_kedai;
    private EditText input_alamat_kedai;
    private EditText input_no_kedai;
    private Spinner input_jenis_makanan;
    private Button button_tambah_kedai;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kedai);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]


        //view
        input_nama_kedai = (EditText) findViewById(R.id.et_nama_kedai);
        input_alamat_kedai = (EditText) findViewById(R.id.et_alamat_kedai);
        input_no_kedai = (EditText) findViewById(R.id.et_no_telepon_kedai);
        input_jenis_makanan = (Spinner) findViewById(R.id.spinnerJenisMakanan);
        button_tambah_kedai = (Button) findViewById(R.id.bt_tambah_kedai);


        //listener button
        button_tambah_kedai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for already existed userId
                submitKedai();
            }
        });
    }


    private void submitKedai() {
        final String nama_kedai = input_nama_kedai.getText().toString();
        final String alamat = input_alamat_kedai.getText().toString();
        final String telefon = input_no_kedai.getText().toString();
        final String jenis_makanan = input_jenis_makanan.getSelectedItem().toString();

        //check if value is empty
        // Nama is required
        if (TextUtils.isEmpty(nama_kedai)) {
            input_nama_kedai.setError(REQUIRED);
            return;
        }
        // Alamat is required
        if (TextUtils.isEmpty(alamat)) {
            input_alamat_kedai.setError(REQUIRED);
            return;
        }
        // Tel is required
        if (TextUtils.isEmpty(telefon)) {
            input_no_kedai.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String IDpengguna = getUid();
        //creating kedai object
        mDatabase.child("Pengguna").child(IDpengguna).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get user value
                        Pengguna pengguna = dataSnapshot.getValue(Pengguna.class);

                        // [START_EXCLUDE]
                        if (pengguna == null) {
                            // Pengguna is null, error out
                            Log.e(TAG, "Pengguna" + IDpengguna + " is unexpectedly null");
                            Toast.makeText(TambahKedai.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            addKedai(IDpengguna, pengguna.nama_pengguna, nama_kedai, alamat, telefon, jenis_makanan);

                        }
                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    //Error if database unable to connect
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                }
        );
        // [END single_value_read]

    }


    //to disable button so there are not multi-posts
    public void setEditingEnabled(boolean enabled) {
        input_nama_kedai.setEnabled(enabled);
        input_alamat_kedai.setEnabled(enabled);
        input_no_kedai.setEnabled(enabled);
        input_jenis_makanan.setEnabled(enabled);
        if (enabled) {
            button_tambah_kedai.setVisibility(View.VISIBLE);
        } else {
            button_tambah_kedai.setVisibility(View.GONE);
        }
    }


    private void addKedai(String IDkedai, String nama_pengguna, String nama_kedai, String alamat, String telefon, String jenis_makanan) {
        // Create new kedai at /pengguna-addkedai/$idpengguna/$idkedai and at /addkedai/$postid simultaneously
        String key = mDatabase.child("Kedai").push().getKey();

        Kedai kedai = new Kedai(IDkedai, nama_pengguna, nama_kedai, alamat, telefon, jenis_makanan);
        Map<String, Object> postValues = kedai.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Kedai/" + key, postValues);
        childUpdates.put("/Kedai-Peniaga/" + IDkedai + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    // [END write_fan_out]
}
