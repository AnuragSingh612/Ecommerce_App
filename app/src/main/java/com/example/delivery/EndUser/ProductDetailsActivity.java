package com.example.delivery.EndUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.delivery.Model.Prevalent;
import com.example.delivery.Model.Products;
import com.example.delivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {
  //  private FloatingActionButton addToCartBtn;
    private Button addToCartBtn;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice,productDescription,productName;
    private String productId="",state ="Normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productId = getIntent().getStringExtra("pid");
        addToCartBtn=findViewById(R.id.pd_add_tocart_button);
        numberButton=findViewById(R.id.number_btn);
        productImage=findViewById(R.id.product_image_details);
        productName=findViewById(R.id.product_name_details);
        productDescription=findViewById(R.id.product_description_details);
        productPrice=findViewById(R.id.product_price_details);
        getProductDetails(productId);
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(state.equals("Order Placed") || state.equals("Order Shipped"))
                {
                    Toast.makeText(ProductDetailsActivity.this,"You can add purchase more products, once your order is shipped or confirmed",Toast.LENGTH_LONG).show();
                }
                else
                {
                    addingToCartList();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       checkOrderState();
    }

    public void addingToCartList()
    {

        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate= Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final DatabaseReference cartListRef2 = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final String id=cartListRef2.push().getKey();
        final HashMap<String,Object> cartMap= new HashMap<>();
        cartMap.put("pid",productId);
        cartMap.put("pname",productName.getText().toString());
        cartMap.put("price",productPrice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("discount","");
        cartMap.put("pid2",id);
        cartListRef.child("User view").child(Prevalent.currentOnlineUser.getPhone()).child("Products").
                child(productId).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    cartListRef.child("Admin view").child(Prevalent.currentOnlineUser.getPhone()).child("Products").
                            child(productId).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {

                                cartListRef2.child("Deliverable").child(Prevalent.currentOnlineUser.getPhone()).child("Products").
                                        child(productId).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                           cartListRef2.child("Deliverable").addValueEventListener(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(DataSnapshot dataSnapshot) {
                                                  if(dataSnapshot.exists())
                                                  {
                                                        Toast.makeText(ProductDetailsActivity.this,dataSnapshot.child(id).child("Products").getKey(),Toast.LENGTH_LONG).show();
                                                  }
                                               }

                                               @Override
                                               public void onCancelled(DatabaseError databaseError) {

                                               }
                                           });

                                        }

                                    }
                                });
                                Toast.makeText(ProductDetailsActivity.this,"Your item is added", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });


    }

    public void getProductDetails(String productId)
    {
        DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Products products= dataSnapshot.getValue(Products.class);
                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void checkOrderState()
    {
        DatabaseReference orderRef;
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String shippingState = dataSnapshot.child("state").getValue().toString();
                   // String userName = dataSnapshot.child("name").getValue().toString();
                    if(shippingState.equals("shipped"))
                    {
                        state="Order Shipped";
                    }
                    else if(shippingState.equals("not shipped"))
                    {
                       state ="Order Placed";
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
