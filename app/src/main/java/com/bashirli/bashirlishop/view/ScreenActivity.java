package com.bashirli.bashirlishop.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bashirli.bashirlishop.LoginRegister.LogRegActivity;
import com.bashirli.bashirlishop.R;
import com.bashirli.bashirlishop.databinding.ActivityScreenBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ScreenActivity extends AppCompatActivity {
    private ActivityScreenBinding binding;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityScreenBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        auth=FirebaseAuth.getInstance();
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,binding.drawer, R.string.nav_open,R.string.nav_close);
        binding.drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    binding.navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()==R.id.exit){
                finish();
            }else if(item.getItemId()==R.id.hesabdancix){
                auth.signOut();
                Intent intent =new Intent(ScreenActivity.this, LogRegActivity.class);
                startActivity(intent);
                finish();
            }else if(item.getItemId()==R.id.elaveet){
                String email=auth.getCurrentUser().getEmail().toString();
                if(!email.equals("admin@admin.com")){
                    Toast.makeText(ScreenActivity.this, "Bura icazəniz yoxdur!", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        NavDirections navDirections=ShopFragmentDirections.actionShopFragmentToFeedFragment();
                        Navigation.findNavController(ScreenActivity.this,R.id.fragmentContainerView).navigate(navDirections);



                    }catch (Exception e){
                        e.getLocalizedMessage();
                    }


                    NavDirections navDirections=FeedFragmentDirections.actionFeedFragmentToShopFragment();
                    Navigation.findNavController(ScreenActivity.this,R.id.fragmentContainerView).navigate(navDirections);
                }
            }else if(R.id.haqqinda==item.getItemId()){
                Toast.makeText(ScreenActivity.this,"Bu funksiya yaxın zamanda aktiv ediləcək.",Toast.LENGTH_LONG).show();
            }else if(R.id.anasehife==item.getItemId()){
                try {
                    NavDirections navDirections=ShopFragmentDirections.actionShopFragmentToFeedFragment();
                    Navigation.findNavController(ScreenActivity.this,R.id.fragmentContainerView).navigate(navDirections);



                }catch (Exception e){
                    e.getLocalizedMessage();
                }


            }
            return false;
        }
    });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}