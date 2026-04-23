package com.example.demoappit6;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.demoappit6.databinding.ActivityPagewithnavigationBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.net.ssl.HostnameVerifier;

public class pagewithnavigation extends AppCompatActivity {

    ActivityPagewithnavigationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_pagewithnavigation);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityPagewithnavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fragmentFrame.setBackgroundColor(android.graphics.Color.RED);
        repFragment(new homeFragment());


        binding.bottomNavView.setOnItemSelectedListener(Item -> {
            int itemid = Item.getItemId();

            System.out.println(itemid);
            if(itemid == R.id.home){
                repFragment(new homeFragment());
            }else if(itemid == R.id.profile){
                repFragment(new profileFragment());
            }else if(itemid == R.id.settings){
                repFragment(new settingsFragment());
            }
        return true;

        });
    }

    private void repFragment(Fragment fragment){
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        fragTransaction.replace(R.id.fragmentFrame, fragment);
        fragTransaction.commit();
    }
}