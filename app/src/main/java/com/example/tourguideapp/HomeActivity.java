package com.example.tourguideapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavController navController;
    public NavigationView navigationView;

    Button btnLogOut, btnSearch;
    EditText txtSearch;
    Intent intent;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!validateLogin()) {
            Toast.makeText(this, "Login to continue", Toast.LENGTH_SHORT).show();
            finish();
            intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        btnSearch = findViewById(R.id.btnSearch);
        btnLogOut = findViewById(R.id.btnLogOut);
        txtSearch = findViewById(R.id.txtSearch);

        btnLogOut.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        setupNavigation();
    }

    private boolean validateLogin() {
        sp = getSharedPreferences(MyVariables.cacheFile, Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean(MyVariables.keyLoginAuth, MyVariables.defaultLoginAuth);
        int userID = sp.getInt(MyVariables.keyUserID, MyVariables.defaultUserID);

        if (isLogin && userID > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogOut:
//                FirebaseAuth.getInstance().signOut();

                editor = sp.edit();
                editor.putBoolean(MyVariables.keyLoginAuth, false);
                editor.putInt(MyVariables.keyUserID, 0);
                editor.apply();

                finish();
                intent = new Intent(getApplicationContext(), MainActivity.class);
                break;

            case R.id.btnSearch:

                /**TODO - Intent for Search Page

                 intent = new Intent(MainActivity.this, SearchActivity.class);
                 temporary fix:
                 */

                finish();
                intent = new Intent(HomeActivity.this, UserProfile.class);
                break;

            default:
                Toast.makeText(this, "Invalid click operation!", Toast.LENGTH_SHORT).show();
                return;
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setupNavigation() {
        toolbar = findViewById(R.id.toolbar_custom);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        menuItem.setCheckable(true);
        drawerLayout.closeDrawers();

        int menuID = menuItem.getItemId();

        switch (menuID) {
            case R.id.menu_item_home:
//                Toast.makeText(getApplicationContext(), "Home Item Clicked!", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.homeFragment);
                break;
            case R.id.menu_item_search:
//                Toast.makeText(getApplicationContext(), "Search Item Clicked!", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.searchFragment);
                break;
            case R.id.menu_item_search_category:
//                Toast.makeText(getApplicationContext(), "Search Item Clicked!", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.categorySearchFragment);
                break;
            case R.id.menu_item_search_ingredient:
//                Toast.makeText(getApplicationContext(), "Drink Item Clicked!", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.ingredientSearchFragment);
                break;
            default:
                Toast.makeText(getApplicationContext(), "Incorrect Menu Item Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout);
    }
}
