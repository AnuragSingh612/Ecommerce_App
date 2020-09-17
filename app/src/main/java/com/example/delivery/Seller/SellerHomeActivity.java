package com.example.delivery.Seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.delivery.EndUser.MainActivity;
import com.example.delivery.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class SellerHomeActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
           switch (item.getItemId())
           {
               case R.id.navigation_home:
                   mTextMessage.setText(R.string.title_home);
                   return true;

               case R.id.navigation_add:
                   mTextMessage.setText(R.string.title_dashboard);
                   Intent intent = new Intent(SellerHomeActivity.this, SellerCategoryActivity.class);
                   //intent.addFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                   startActivity(intent);

                   return true;

               case R.id.navigation_logout:
                   final FirebaseAuth mAuth;
                   mAuth=FirebaseAuth.getInstance();
                   mAuth.signOut();

                   Intent intent1 = new Intent(SellerHomeActivity.this, MainActivity.class);

                   intent1.addFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    startActivity(intent1);
                    finish();
                   return true;



           }
           return false;
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mTextMessage=findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

}
