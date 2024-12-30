package com.bansal.JewellaryApplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
    String code;

    // Fragments
    HomeFragment homeFragment = new HomeFragment();
    ChatFragment chatFragment = new ChatFragment();
    WishlistFragment wishlistFragment = new WishlistFragment();
    MyProfileFragment myprofilFragment = new MyProfileFragment();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set status bar and navigation bar colors
        getWindow().setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(HomeActivity.this, R.color.white));
         sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

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
if (item.getItemId()==R.id.gold){
    Intent i = new Intent(HomeActivity.this,GetMenOrWomenProduct.class);
    code="4001";

    i.putExtra("CategoryCode",code);

    startActivity(i);
}else  if (item.getItemId()==R.id.Diamond){
    Intent i = new Intent(HomeActivity.this,DimaondMenWOmen.class);
    code="4002";
    i.putExtra("CategoryCode",code);
    startActivity(i);
}else  if (item.getItemId()==R.id.antique){
    Intent i = new Intent(HomeActivity.this,GetCategoryWiseProduct.class);
    code="4003";
    i.putExtra("CategoryCode",code);
    i.putExtra("categoryname","Antique");
    startActivity(i);
}else  if (item.getItemId()==R.id.polki){
    Intent i = new Intent(HomeActivity.this,GetCategoryWiseProduct.class);
    code="4004";
    i.putExtra("CategoryCode",code);
    i.putExtra("categoryname","Polki");
    startActivity(i);
}else  if (item.getItemId()==R.id.baby){
    Intent i = new Intent(HomeActivity.this,GetCategoryWiseProduct.class);
    code="4005";
    i.putExtra("CategoryCode",code);
    i.putExtra("categoryname","Baby");
    startActivity(i);
}else  if (item.getItemId()==R.id.goldcoinbars) {
    Intent i = new Intent(HomeActivity.this,GetCategoryWiseProduct.class);
    code="4007";
    i.putExtra("CategoryCode",code);
    i.putExtra("categoryname","GOLD COINS AND BAR");
    startActivity(i);
}
else  if (item.getItemId()==R.id.gemstone) {
    Intent i = new Intent(HomeActivity.this,GetCategoryWiseProduct.class);
    code="4007";
    i.putExtra("CategoryCode",code);
    i.putExtra("categoryname","GEMSTONE");
    startActivity(i);
}
else  if (item.getItemId()==R.id.lossediamond) {
    Intent i = new Intent(HomeActivity.this,GetCategoryWiseProduct.class);
    code="4008";
    i.putExtra("CategoryCode",code);
    i.putExtra("categoryname","LOOSE DIAMONDS");
    startActivity(i);
}
else  if (item.getItemId()==R.id.jadau) {
    Intent i = new Intent(HomeActivity.this,GetCategoryWiseProduct.class);
    code="4004";
    i.putExtra("CategoryCode",code);
    i.putExtra("categoryname","Jadau");
    startActivity(i);
}
else if(item.getItemId()==R.id.Market) {
    Intent i = new Intent(HomeActivity.this, MarketPrice.class);
    startActivity(i);

}else if(item.getItemId()==R.id.termasandcondition){
    Intent i = new Intent(HomeActivity.this,Termasandcondition.class);
    startActivity(i);

}
        if (item.getItemId() == R.id.hoome) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flUserHomeFrameLayout, homeFragment).commit();
        } else if (item.getItemId() == R.id.chat) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flUserHomeFrameLayout, chatFragment).commit();
        } else if (item.getItemId() == R.id.wishlist) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flUserHomeFrameLayout, wishlistFragment).commit();
        } else if (item.getItemId() == R.id.profil) {
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