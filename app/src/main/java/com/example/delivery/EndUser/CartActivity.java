package com.example.delivery.EndUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delivery.Model.Cart;
import com.example.delivery.Model.Prevalent;
import com.example.delivery.R;
import com.example.delivery.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager LayoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount, txtMsg1;
    private int overTotalPrice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        LayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LayoutManager);
        NextProcessBtn = findViewById(R.id.next_process_btn);
        txtTotalAmount = findViewById(R.id.total_price);
        txtMsg1 =findViewById(R.id.msg1);
        NextProcessBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent (CartActivity.this, ConfirmFinalOrderActivity.class);
                intent.putExtra("Total Price",String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkOrderState();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("User view")
                        .child(Prevalent.currentOnlineUser.getPhone())
                        .child("Products"), Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart cart) {
                  holder.txtProductQuantity.setText("Quantity:"+cart.getQuantity());
                  holder.txtProductName.setText("Product Name:"+cart.getPname());
                  holder.txtProductPrice.setText("Price"+cart.getPrice()+"Rs");
                  int oneTypeProductTPrice=((Integer.valueOf(cart.getPrice())))* Integer.valueOf(cart.getQuantity());
                  overTotalPrice = overTotalPrice + oneTypeProductTPrice;
                txtTotalAmount.setText("Total Price ="+String.valueOf(overTotalPrice));
                  holder.itemView.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          CharSequence options[] = new CharSequence[]
                                  {
                                    "Edit",
                                    "Remove"
                                  };
                          AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                          builder.setTitle("Cart Options:");
                          builder.setItems(options, new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  if(which==0) {
                                      Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                                      intent.putExtra("pid", cart.getPid());
                                      startActivity(intent);
                                  }
                                  if(which==1)
                                  {
                                      cartListRef.child("Admin view").child(Prevalent.currentOnlineUser.getPhone())
                                              .child("Products").child(cart.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                          @Override
                                          public void onComplete(@NonNull Task<Void> task) {
                                              if(task.isSuccessful())
                                              {


                                              }
                                          }
                                      });
                                      cartListRef.child("User view").child(Prevalent.currentOnlineUser.getPhone())
                                              .child("Products").child(cart.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                          @Override
                                          public void onComplete(@NonNull Task<Void> task) {
                                              if(task.isSuccessful())
                                              {
                                                  Toast.makeText(CartActivity.this,"Items removed successfully.",Toast.LENGTH_LONG).show();
                                                  Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                                                  startActivity(intent);

                                              }
                                          }
                                      });
                                      cartListRef.child("Deliverable").child(Prevalent.currentOnlineUser.getPhone()).child("Products").child(cart.getPid()).removeValue()
                                      .addOnCompleteListener(new OnCompleteListener<Void>() {
                                          @Override
                                          public void onComplete(@NonNull Task<Void> task) {

                                          }
                                      });

                                  }
                              }
                          });
                          builder.show();

                      }
                  });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()) .inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder= new CartViewHolder(view);
                return holder;
            }
        } ;
          recyclerView.setAdapter(adapter);
          adapter.startListening();
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
                    String userName = dataSnapshot.child("pname").getValue().toString();
                    if(shippingState.equals("shipped"))
                    {
                        txtTotalAmount.setText("Dear "+userName +"\n Order is shipped successfully.");
                        recyclerView.setVisibility(View.GONE);
                        txtMsg1.setVisibility(View.VISIBLE);
                        txtMsg1.setText("Congratulation your order has been shipped");
                        NextProcessBtn.setVisibility(View.GONE );
                    }
                    else if(shippingState.equals("not shipped"))
                    {
                        txtTotalAmount.setText("Shipping State = Not Shipped");
                        recyclerView.setVisibility(View.GONE);
                        txtMsg1.setVisibility(View.VISIBLE);
                        NextProcessBtn.setVisibility(View.GONE );
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}