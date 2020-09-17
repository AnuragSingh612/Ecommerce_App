package com.example.delivery.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.delivery.Model.Cart;
import com.example.delivery.R;
import com.example.delivery.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_deliverable_product extends AppCompatActivity {
    private RecyclerView productsList2;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;

    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_deliverable_product);
        userID = getIntent().getStringExtra("uid");

       // Toast.makeText(Admin_deliverable_product.this, userID, Toast.LENGTH_SHORT).show();
        productsList2 = findViewById(R.id.products_list2);
        productsList2.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        cartListRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart List").child("Deliverable").child(userID).child("Products");
//        cartListRef = FirebaseDatabase.getInstance().getReference()
//                .child("Orders");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef, Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model)
            {
                holder.txtProductQuantity.setText("Quantity = " + model.getQuantity());
                holder.txtProductPrice.setText("Price " + model.getPrice() + " Rs");
                holder.txtProductName.setText(model.getPname());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        productsList2.setAdapter(adapter);
        adapter.startListening();
    }
}
