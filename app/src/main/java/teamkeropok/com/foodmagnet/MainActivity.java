package teamkeropok.com.foodmagnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;

import teamkeropok.com.foodmagnet.AccountSetting;
import teamkeropok.com.foodmagnet.R;
import teamkeropok.com.foodmagnet.SignInActivity;
import teamkeropok.com.foodmagnet.fragment.CarianKedai;
import teamkeropok.com.foodmagnet.fragment.MenuUtama;
import teamkeropok.com.foodmagnet.fragment.PromosiKedai;
import teamkeropok.com.foodmagnet.model.Kedai;
import teamkeropok.com.foodmagnet.model.Pengguna;


public class MainActivity extends BaseActivity {


    private static final String TAG = "MainActivity";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new MenuUtama(),
                    new CarianKedai(),
                    new PromosiKedai(),
            };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.tab_menu_utama),
                    getString(R.string.tab_carian_kedai),
                    getString(R.string.tab_promosi_kedai)
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        // Button launches NewKedai
        findViewById(R.id.fab_new_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahKedai.class));
            }
        });

    }
    

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