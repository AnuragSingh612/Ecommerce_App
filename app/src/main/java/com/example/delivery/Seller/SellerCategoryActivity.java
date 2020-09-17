package com.example.delivery.Seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.delivery.R;

public class SellerCategoryActivity extends AppCompatActivity {
    private ImageView tshirts,sportsTshirts,femaleDresses,sweaters;
  //  private ImageView glasses,hatCaps,walletBagsPurses,shoes;
    private ImageView headPhoneHandFree,Laptops,watches,mobilePhones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_category);

        tshirts=findViewById(R.id.t_shirts);

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SellerCategoryActivity.this, SellerAddNewProductActivity.class);
               intent.putExtra("category","tshirts");
                startActivity(intent);
            }
        });
        sportsTshirts=findViewById(R.id.sports_t_shirts);
        sportsTshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SellerCategoryActivity.this, SellerAddNewProductActivity.class );
                intent.putExtra("category","sportsTshirt");
                startActivity(intent);
            }
        });

    }
}
