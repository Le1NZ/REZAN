package com.example.rezan.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.rezan.R;
import com.example.rezan.databinding.ActivityMainBinding;
import com.example.rezan.ui.fragments.AccountFragment;
import com.example.rezan.ui.fragments.HomeFragment;
import com.example.rezan.ui.fragments.MapFragment;
import com.example.rezan.ui.fragments.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yandex.mapkit.MapKitFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    final private String MAPKIT_API_KEY = "865002f7-f96a-4741-9aef-ba4f857527fe";
    final private Integer REQUEST_LOCATION_PERMISSION = 0;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MapKitFactory.setApiKey(MAPKIT_API_KEY);

        // For bottom navigation
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        bottomNavigationView = binding.navView;
        bottomNavigationView.setOnItemSelectedListener( item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    navController.navigate(R.id.navigation_home);
                    break;
                case R.id.navigation_map:
                    navController.navigate(R.id.navigation_map);
                    break;
                case R.id.navigation_shop:
                    navController.navigate(R.id.navigation_shop);
                    break;
                case R.id.navigation_account:
                    navController.navigate(R.id.navigation_account);
            }
            return true;
        });

        // Check for permission of location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);

        }

    }

}