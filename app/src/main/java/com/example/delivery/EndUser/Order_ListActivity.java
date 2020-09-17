package com.example.delivery.EndUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.delivery.Model.Prevalent;
import com.example.delivery.R;
import com.example.delivery.Seller.SellerAddNewProductActivity;
import com.example.delivery.Seller.SellerHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Order_ListActivity extends AppCompatActivity {
    EditText item1,item3,item4,item5,item6,item7,item8,item9,item10,address,phone;
    Button place_order;
    private DatabaseReference ProductsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__list);
        item1=findViewById(R.id.item1);
        item3=findViewById(R.id.item3);
        item4=findViewById(R.id.item4);
        item5=findViewById(R.id.item5);
        item6=findViewById(R.id.item6);
        item7=findViewById(R.id.item7);
        item8=findViewById(R.id.item8);
        item10=findViewById(R.id.item10);
        item9=findViewById(R.id.item9);
        phone=findViewById(R.id.phone);
        address=findViewById(R.id.address);

        place_order=findViewById(R.id.place_order);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("list");
        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addinglist();
            }
        });
    }
    public void addinglist()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("item1", item1.getText().toString());
        productMap.put("item2", item3.getText().toString());
        productMap.put("item3", item4.getText().toString());
        productMap.put("item4", item5.getText().toString());
        productMap.put("item5", item6.getText().toString());
        productMap.put("item6", item7.getText().toString());
        productMap.put("item7", item8.getText().toString());
        productMap.put("item8", item9.getText().toString());
        productMap.put("item9",item10.getText().toString());
        productMap.put("item10",item10.getText().toString());
        productMap.put("address",address.getText().toString());
        productMap.put("phone",phone.getText().toString());
        productMap.put("state","new");
//        productMap.put("sellerAddress",sAddress);
//        productMap.put("sellerPhone",sPhone);
//        productMap.put("sID",sID);

        ProductsRef.push().updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {

//
//
                           Toast.makeText(Order_ListActivity.this, "List is added successfully..", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Order_ListActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                        else
                        {

//                            String message = task.getException().toString();
//                            Toast.makeText(SellerAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
