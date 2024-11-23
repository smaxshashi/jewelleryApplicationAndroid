package com.bansal.JewellaryApplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView userBottomnevigation;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    // Fragments
    HomeFragment homeFragment = new HomeFragment();
    ChatFragment chatFragment = new ChatFragment();
    WishlistFragment wishlistFragment = new WishlistFragment();
    MyProfileFragment myprofilFragment = new MyProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set status bar and navigation bar colors
        getWindow().setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(HomeActivity.this, R.color.white));

        // Setup Toolbar (Optional)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        // Setup ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set listeners for NavigationView and BottomNavigationView
        navigationView.setNavigationItemSelectedListener(this);  // for the Drawer items
        userBottomnevigation = findViewById(R.id.bottomnevigatiomuserhome);
        userBottomnevigation.setOnNavigationItemSelectedListener(this);  // for the BottomNavigation items

        // Set default selection for BottomNavigationView
        userBottomnevigation.setSelectedItemId(R.id.menuUserhomebottomnavigationHome);

        // Default fragment for BottomNavigationView
        getSupportFragmentManager().beginTransaction().replace(R.id.flUserHomeFrameLayout, homeFragment).commit();
    }

    // Handle both Navigation Drawer and Bottom Navigation item selection
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle Navigation Drawer items
        if (item.getItemId() == R.id.menuhome) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flUserHomeFrameLayout, homeFragment).commit();
        } else if (item.getItemId() == R.id.menuchat) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flUserHomeFrameLayout, chatFragment).commit();
        } else if (item.getItemId() == R.id.menuorders) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flUserHomeFrameLayout, wishlistFragment).commit();
        } else if (item.getItemId() == R.id.menuprofile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flUserHomeFrameLayout, myprofilFragment).commit();
        }

        // Handle Bottom Navigation items
        if (item.getItemId() == R.id.menuUserhomebottomnavigationHome) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flUserHomeFrameLayout, homeFragment).commit();
        } else if (item.getItemId() == R.id.menuUserhomebottomnavigationChat) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flUserHomeFrameLayout, chatFragment).commit();
        } else if (item.getItemId() == R.id.menuUserhomebottomnavigatiowishlist) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flUserHomeFrameLayout, wishlistFragment).commit();
        } else if (item.getItemId() == R.id.menuUserhomebottomnavigationMyprofil) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flUserHomeFrameLayout, myprofilFragment).commit();
        }

        // Close the drawer after selection
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        return true;
    }


    // Handle back press to close navigation drawer or exit app
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finishAffinity(); // Close all activities and exit the app
        }
    }
}