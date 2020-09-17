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
import android.widget.Toast;

import com.example.delivery.Model.Cart;
import com.example.delivery.Model.listmodel;
import com.example.delivery.R;
import com.example.delivery.ViewHolder.OrderlistViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminListViewActivity extends AppCompatActivity {
    private DatabaseReference ListRef;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_view);
        ListRef= FirebaseDatabase.getInstance().getReference().child("list");
        recyclerView = findViewById(R.id.admin_order_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<listmodel> options = new FirebaseRecyclerOptions.Builder<listmodel>()
                .setQuery(ListRef,listmodel.class)
                .build();

        FirebaseRecyclerAdapter<listmodel, OrderlistViewHolder> adapter =
                 new FirebaseRecyclerAdapter<listmodel, OrderlistViewHolder>(options){
                    @Override
                    protected void onBindViewHolder(@NonNull OrderlistViewHolder orderlistViewHolder, final int i, @NonNull listmodel listmodel) {
                        orderlistViewHolder.item1.setText(listmodel.getItem1());
                        orderlistViewHolder.item3.setText(listmodel.getItem2());
                        orderlistViewHolder.item4.setText(listmodel.getItem3());
                        orderlistViewHolder.item5.setText(listmodel.getItem4());
                        orderlistViewHolder.item6.setText(listmodel.getItem5());
                        orderlistViewHolder.item7.setText(listmodel.getItem6());
                        orderlistViewHolder.item8.setText(listmodel.getItem7());
                        orderlistViewHolder.item9.setText(listmodel.getItem8());
                        orderlistViewHolder.item10.setText(listmodel.getItem9());
                        orderlistViewHolder.address.setText("Address: "+listmodel.getAddress());
                        orderlistViewHolder.phone.setText("Contact at: "+listmodel.getPhone());
                        orderlistViewHolder.state.setText("Order State: " +listmodel.getState());
                        orderlistViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminListViewActivity.this);
                                builder.setTitle("Have you shipped this order product");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        if(which==0)
                                        {
                                            Toast.makeText(AdminListViewActivity.this,"Deleting...",Toast.LENGTH_LONG).show();
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
                    public OrderlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
                        OrderlistViewHolder holder = new OrderlistViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    private void RemoveOrder(String uID) {
        ListRef.child(uID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(AdminListViewActivity.this,"Item Confirmed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
