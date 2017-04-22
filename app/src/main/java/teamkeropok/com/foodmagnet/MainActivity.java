package teamkeropok.com.foodmagnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import teamkeropok.com.foodmagnet.AccountSetting;
import teamkeropok.com.foodmagnet.R;
import teamkeropok.com.foodmagnet.SignInActivity;
import teamkeropok.com.foodmagnet.model.Kedai;
import teamkeropok.com.foodmagnet.model.Pengguna;

public class MainActivity extends AppCompatActivity{

    private Button bttambahkedai;
    private TextView mDetailTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bttambahkedai = (Button)findViewById(R.id.bttambahkedai);

        bttambahkedai.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, TambahKedai.class);
                startActivity(myIntent);

            }

        });
        }
    

    /** Called when the user taps the Send button
    public void tambahKedai(View view) {
        Intent intent = new Intent(this, TambahKedai.class);
        String message = editText.getUid().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

     */



    /*
       //This is our tablayout
       private TabLayout tabLayout;

       //This is our viewPager
       private ViewPager viewPager;

       private Button bt_tambah_kedai;


       public static class ViewKedaiHolder extends RecyclerView.ViewHolder{

          public TextView str_nama_kedai;
           public TextView str_alamat_kedai;
           public TextView str_no_kedai;
           public TextView str_jenis_makanan;

           public ViewKedaiHolder(View v) {
               super(v);
               str_nama_kedai = (TextView)itemView.findViewById(R.id.tv_nama_kedai);
               str_alamat_kedai = (TextView)itemView.findViewById(R.id.tv_alamat_kedai);
               str_no_kedai = (TextView)itemView.findViewById(R.id.tv_no_telefon);
               str_jenis_makanan = (TextView)itemView.findViewById(R.id.tv_jenis_makanan);

           }
       }

       public static final String KEDAI = "kedai";

       public static final String TAG = "MainActivity";

       private RecyclerView mKedaiRecycleView;

       private LinearLayoutManager mLinearLayoutManager;

       private DatabaseReference mFirebaseDatabaseReference;
       private FirebaseRecyclerAdapter<Kedai, ViewKedaiHolder> mFirebaseAdapter;


       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_main);

           //Initialize RecycleView
           mKedaiRecycleView = (RecyclerView) findViewById(R.id.kedaiRecycleView);
           mLinearLayoutManager = new LinearLayoutManager(this);
           mLinearLayoutManager.setStackFromEnd(true);
           mKedaiRecycleView.setLayoutManager(mLinearLayoutManager);

       }



       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_tambah_kedai);
           Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
           setSupportActionBar(toolbar);
       }






       //Database Initialize
       mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
       mFirebaseAdapter = new FirebaseRecyclerAdapter<Kedai, ViewKedaiHolder>
       {
           Kedai.class,
           R.layout.activity_view_kedai,
           ViewKedaiHolder.class,
                   mFirebaseDatabaseReference.child(KEDAI)){
           @Override
                   protected void populateViewHolder (ViewKedaiHolder viewHolder, Kedai model, int position)
           {
               viewHolder.str_nama_kedai.set
           }
       }
       }


   */
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return true;
        }
        if (i == R.id.button_setting) {
            startActivity(new Intent(this, AccountSetting.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }




}