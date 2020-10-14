package com.exademy.activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.exademy.sidenav.Account;
import com.exademy.sidenav.ChatWithEducator;
import com.exademy.sidenav.Contact;
import com.exademy.sidenav.Faqs;
import com.exademy.sidenav.FreeCourse;
import com.exademy.sidenav.LegalTerms;
import com.exademy.sidenav.LiveClasses;
import com.exademy.sidenav.Logout;
import com.exademy.sidenav.PremiumCourse;
import com.exademy.sidenav.ReferAndEarn;
import com.exademy.sidenav.TermsAndCondition;
import com.exademy.sidenav.TestSeries;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.exademy.*;
import com.exademy.fragment.HomeFragment;
import com.exademy.fragment.MyLibraryFragment;
import com.exademy.fragment.PlusFragment;
import com.exademy.fragment.ProfileFragment;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    FrameLayout fl_container;
    BottomNavigationView bnv_menu;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    static public TextView tv_goal_type;
    Menu nav_menu;
    Button draweropener;
    Spinner spinner;
    public static SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.exademy.fragment", Context.MODE_PRIVATE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);


//        spinner
        spinner = findViewById(R.id.Spinnercourse);
//       spinner.setOnItemSelectedListener(this);


        fl_container = findViewById(R.id.fl_container);
        bnv_menu = findViewById(R.id.bnv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        draweropener = (Button) findViewById(R.id.draweropener);

        nav_menu = navigationView.getMenu();


// drawer code
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        bnv_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_my_library:
                        fragment = new MyLibraryFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_plus:
                        fragment = new PlusFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_profile:
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                        return true;
                }

                return false;
            }
        });

        bnv_menu.setSelectedItemId(R.id.navigation_home);
        loadFragment(new HomeFragment());


    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void draweropen(View view) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        drawerLayout.startAnimation(animation);
        drawerLayout.openDrawer(GravityCompat.START);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.freecourse:
                startActivity(new Intent(getApplicationContext(), FreeCourse.class));
                return true;
            case R.id.premiumcourse:
                startActivity(new Intent(getApplicationContext(), PremiumCourse.class));
                return true;
            case R.id.testseries:
                startActivity(new Intent(getApplicationContext(), TestSeries.class));
                return true;
            case R.id.liveclass:
                startActivity(new Intent(getApplicationContext(), LiveClasses.class));
                return true;
            case R.id.chatwitheducator:
                startActivity(new Intent(getApplicationContext(), ChatWithEducator.class));
                return true;
            case R.id.settings:
                if (nav_menu.findItem(R.id.accounts).isVisible() == true) {
                    nav_menu.findItem(R.id.accounts).setVisible(false);
                    nav_menu.findItem(R.id.faqs).setVisible(false);
                    nav_menu.findItem(R.id.termsandcondition).setVisible(false);
                    nav_menu.findItem(R.id.legalterms).setVisible(false);
                    nav_menu.findItem(R.id.referandearn).setVisible(false);
                    nav_menu.findItem(R.id.logout).setVisible(false);
                    nav_menu.findItem(R.id.settings).setIcon(R.drawable.gearicon);
                }else{
                    nav_menu.findItem(R.id.accounts).setVisible(true);
                    nav_menu.findItem(R.id.faqs).setVisible(true);
                    nav_menu.findItem(R.id.termsandcondition).setVisible(true);
                    nav_menu.findItem(R.id.legalterms).setVisible(true);
                    nav_menu.findItem(R.id.referandearn).setVisible(true);
                    nav_menu.findItem(R.id.logout).setVisible(true);
                    nav_menu.findItem(R.id.settings).setIcon(R.drawable.downarrow);
                }   return true;
            case R.id.accounts:
                startActivity(new Intent(getApplicationContext(), Account.class));
                return true;
            case R.id.faqs:
                startActivity(new Intent(getApplicationContext(), Faqs.class));
                return true;
            case R.id.termsandcondition:
                startActivity(new Intent(getApplicationContext(), TermsAndCondition.class));
                return true;
            case R.id.legalterms:
                startActivity(new Intent(getApplicationContext(), LegalTerms.class));
                return true;
            case R.id.referandearn:
                startActivity(new Intent(getApplicationContext(), ReferAndEarn.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(getApplicationContext(), Logout.class));
                return true;

            case R.id.contactus: if(nav_menu.findItem(R.id.contactemail).isVisible() == true){
                nav_menu.findItem(R.id.contactemail).setVisible(false);
                nav_menu.findItem(R.id.contactnumber).setVisible(false);
                nav_menu.findItem(R.id.contactus).setIcon(R.drawable.contactinfo);
            }else{
                nav_menu.findItem(R.id.contactemail).setVisible(true);
                nav_menu.findItem(R.id.contactnumber).setVisible(true);
                nav_menu.findItem(R.id.contactus).setIcon(R.drawable.downarrow);
            } return true;
            case R.id.contactemail:
                startActivity(new Intent(getApplicationContext(), Contact.class));
                return true;
            case R.id.contactnumber:
                startActivity(new Intent(getApplicationContext(), Contact.class));
                return true;

            default:
                return false;

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(), parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(), parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }


}
