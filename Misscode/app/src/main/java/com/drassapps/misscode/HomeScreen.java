package com.drassapps.misscode;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button more;

    private int[] tabIcons = {
            R.drawable.pc_icon,
            R.drawable.icon_we,
            R.drawable.estrella
    };

    private SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    private DrawerLayout drawerLayout;
    Boolean buttonStateOpen;

    private Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.home_screen);

        prefs = getSharedPreferences(MyPREFERENCES, this.MODE_PRIVATE);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        more = (Button) findViewById(R.id.fab);
        menu = (Button) findViewById(R.id.menu);
        drawerLayout  = (DrawerLayout)findViewById(R.id.drawer_layout);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this, Registro.class);
                overridePendingTransition(R.anim.zoom_foward_in, R.anim.zoom_foward_out);
                startActivity(i);
                finish();
            }
        });

        buttonStateOpen = false;

        NavigationView bundle;

        bundle = (NavigationView) findViewById(R.id.navview);

        if (bundle != null)
        {
            setupDrawerContent(bundle);
        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttonStateOpen) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                    buttonStateOpen = true;

                } else if (buttonStateOpen) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    buttonStateOpen = false;
                }
            }
        });

    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }


    public String clase(){

        final String cat = prefs.getString("categoria_save1", "");

        return cat;

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "");
        adapter.addFragment(new TwoFragment(), "");
        adapter.addFragment(new ThreeFragment(), "");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setupDrawerContent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        menuItem.getItemId();
                        return true;
                    }
                });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        // finish();

        if (buttonStateOpen){
            drawerLayout.closeDrawer(Gravity.LEFT);
            buttonStateOpen = false;
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            //finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Presione otra vez para salir", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);


    }
}
