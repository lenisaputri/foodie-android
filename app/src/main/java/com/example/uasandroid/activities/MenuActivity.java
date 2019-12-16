package com.example.uasandroid.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.uasandroid.R;
import com.example.uasandroid.isi_menu.AccountFragment;
import com.example.uasandroid.isi_menu.RecipesFragment;
import com.example.uasandroid.isi_menu.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Bundle;

public class MenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        openFragment(new RecipesFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_recipes:
                openFragment(new RecipesFragment());
                return true;
            case R.id.action_search:
                openFragment(new SearchFragment());
                return true;
            case R.id.action_account:
                openFragment(new AccountFragment());
                return true;
        }
        return false;
    }

    private void openFragment(Fragment fragment) {
        openFragment(fragment, false);
    }

    private void openFragment(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        if (addToBackstack)
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
