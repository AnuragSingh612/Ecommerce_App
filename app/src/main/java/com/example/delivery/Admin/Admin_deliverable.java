package com.example.delivery.Admin;

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

import com.example.delivery.Model.AdminOrders;
import com.example.delivery.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Admin_deliverable extends AppCompatActivity {
    private RecyclerView todeliverlist;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_deliverable);
        ordersRef= FirebaseDatabase.getInstance().getReference().child("Orders2");
        todeliverlist=findViewById(R.id.admin_item_deliver);
        todeliverlist.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders> options = new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(ordersRef.orderByChild("state").equalTo("Deliverable"),AdminOrders.class)
                .build();
        FirebaseRecyclerAdapter<AdminOrders, AdminNewOrdersActivity.AdminOrdersViewHolder> adapter= new FirebaseRecyclerAdapter<AdminOrders, AdminNewOrdersActivity.AdminOrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminNewOrdersActivity.AdminOrdersViewHolder adminOrdersViewHolder, final int i, @NonNull final AdminOrders adminOrders) {
                adminOrdersViewHolder.userName.setText("Name:"+ adminOrders.getPname());
                adminOrdersViewHolder.userPhoneNumber.setText("Phone:"+ adminOrders.getPhone());
                adminOrdersViewHolder.userTotalPrice.setText("Total Amount:"+ adminOrders.getTotalAmount());
                adminOrdersViewHolder.userShippingAddress.setText("Address:"+ adminOrders.getAddress()+" "+adminOrders.getCity());
                adminOrdersViewHolder.userDateTime.setText("Order at:"+ adminOrders.getDate()+" "+adminOrders.getTime());
                adminOrdersViewHolder.showOrderbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        String uID = getRef(i).getKey();

                        Intent intent = new Intent(Admin_deliverable.this, Admin_deliverable_product.class);
                        intent.putExtra("uid", uID);
                        startActivity(intent);
                    }
                });
                adminOrdersViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Yes",
                                        "No"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_deliverable.this);
                        builder.setTitle("Have you shipped this order product");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if(which==0)
                                {

                                    String uID = getRef(which).getKey();
                                    RemoveOrder(uID);
                                }
                                else
                                {
                                    finish();
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public AdminNewOrdersActivity.AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                return new AdminNewOrdersActivity.AdminOrdersViewHolder(view);
            }
        };
        todeliverlist.setAdapter(adapter);
        adapter.startListening();
    }
    public static class AdminDeliverOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userName,userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress;
        public Button showOrderbtn2;
        public AdminDeliverOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            userShippingAddress= itemView.findViewById(R.id.order_address_city);
            showOrderbtn2=itemView.findViewById(R.id.show_all_products_btn);
        }
    }
    private void RemoveOrder(String uID) {
        ordersRef.child(uID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {

                }
            }
        });
    }
}
