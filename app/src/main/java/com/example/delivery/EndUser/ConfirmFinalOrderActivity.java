package com.example.delivery.EndUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.delivery.Admin.AdminNewOrdersActivity;
import com.example.delivery.Model.Cart;
import com.example.delivery.Model.Prevalent;
import com.example.delivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    private EditText nameEditText,phoneEditText,addressEditText,cityEditText;
    private Button confirmOrderBtn;
    String totalAmount=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        totalAmount=getIntent().getStringExtra("Total Price");
        setContentView(R.layout.activity_confirm_final_order);
        confirmOrderBtn = findViewById(R.id.confirm_final_order_btn);
        nameEditText=findViewById(R.id.shippment_name);
        phoneEditText=findViewById(R.id.shippment_phone_number);
        addressEditText=findViewById(R.id.shippment_address);
        cityEditText=findViewById(R.id.shippment_city);
        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

    }
    private void check()
    {
        if(TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(ConfirmFinalOrderActivity.this,"Please Provide your full name",Toast.LENGTH_LONG).show();
        }
       else if(TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(ConfirmFinalOrderActivity.this,"Please Provide your phone number",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(ConfirmFinalOrderActivity.this,"Please Provide your address",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            Toast.makeText(ConfirmFinalOrderActivity.this,"Please Provide your city name",Toast.LENGTH_LONG).show();
        }
        else
        {
            ConfirmOrder();
        }
    }
    private void ConfirmOrder()
    {
        final  String saveCurrentDate,saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentTime.format(calForDate.getTime());
        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());
        final DatabaseReference orderRef2 = FirebaseDatabase.getInstance().getReference().child("Orders2")
                .child(Prevalent.currentOnlineUser.getPhone());






        final HashMap<String,Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount",totalAmount);
        ordersMap.put("pname",nameEditText.getText().toString());
        ordersMap.put("phone",phoneEditText.getText().toString());
        ordersMap.put("date",saveCurrentDate);
        ordersMap.put("time",saveCurrentTime);
        ordersMap.put("address",addressEditText.getText().toString());
        ordersMap.put("city",cityEditText.getText().toString());
        ordersMap.put("state","not shipped");
        ordersMap.put("Signedinphone",Prevalent.currentOnlineUser.getPhone());
        orderRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {

//                           creatingorderhistory();
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User view")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(ConfirmFinalOrderActivity.this,"Your final order is placed",Toast.LENGTH_LONG).show();
                                        Intent intent= new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


                                        orderRef2.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {

                                                }
                                            }
                                        });
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }

//    private void creatingorderhistory() {
//        final DatabaseReference orderhistory = FirebaseDatabase.getInstance().getReference().child("Cart List")
//                .child("Admin view").child(Prevalent.currentOnlineUser.getPhone())
//                .child("Products").child("Apr 03, 202012:28:14 PM");
//        final DatabaseReference putorderhistory =FirebaseDatabase.getInstance().getReference().child("Order History").child(Prevalent.currentOnlineUser.getPhone());
//        orderhistory.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists())
//                {
//
////                    String Ordername= dataSnapshot.child("pname").getValue().toString();
//                    String date =dataSnapshot.child("date").getValue().toString();
//                    String orderid= dataSnapshot.child("pid").getValue().toString();
//                    String time=dataSnapshot.child("date").getValue().toString();
//                    String quantity= dataSnapshot.child("quantity").getValue().toString();
//                    String price= dataSnapshot.child("price").getValue().toString();
//
//                    HashMap<String ,Object> orderhistorymp = new HashMap<>();
//                    orderhistorymp.put("pid",orderid);
////                    orderhistorymp.put("pname",Ordername);
//                    orderhistorymp.put("price",price);
//                    orderhistorymp.put("date",date);
//                    orderhistorymp.put("time",time);
//                    orderhistorymp.put("quantity",quantity);
//                    putorderhistory.push().updateChildren(orderhistorymp)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if(task.isSuccessful())
//                                    {
//
//
//                                    }
//                                    else
//                                    {
//
//                                    }
//                                }
//                            });
//
//                    //Toast.makeText(testActivity.this,pid,Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
}



