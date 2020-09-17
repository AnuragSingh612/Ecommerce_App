package com.example.delivery.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.delivery.R;
import com.example.delivery.Seller.SellerCategoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductsActivity extends AppCompatActivity {
    private Button applyChangesBtn,deletebtn;
    private EditText name,price,description;
    private ImageView imageView;
    private String productID ="";
    private DatabaseReference productsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productID= getIntent().getStringExtra("pid");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);
        setContentView(R.layout.activity_admin_maintain_products);
        applyChangesBtn=findViewById(R.id.apply_changes_btn);
        name=findViewById(R.id.product_name_maintain);
        price=findViewById(R.id.product_price_maintain);
        description=findViewById(R.id.product_description_maintain);
        imageView=findViewById(R.id.product_image_maintain);
        deletebtn=findViewById(R.id.delete_btn);
        displaySpecificProductInfo();
        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges();
            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                deleteThisProduct();
            }
        });

    }


    private void displaySpecificProductInfo() {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String pName= dataSnapshot.child("pname").getValue().toString();
                    String pPrice= dataSnapshot.child("price").getValue().toString();
                    String pDescription= dataSnapshot.child("description").getValue().toString();
                        String pImage = dataSnapshot.child("image").getValue().toString();
                        // use the same child name which is in the database
                        name.setText(pName);
                        price.setText(pPrice);
                        description.setText(pDescription);
                        Picasso.get().load(pImage).into(imageView);
                    }
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void applyChanges()
    {
        String pName= name.getText().toString();
        String pPrice= price.getText().toString();
        String pDescription=description.getText().toString();
        if(pName.equals(""))
        {
            Toast.makeText(this,"Write down product name",Toast.LENGTH_LONG).show();
        }
        else
        {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid", productID);
            productMap.put("description", pDescription);
            productMap.put("price", pPrice);
            productMap.put("pname", pName);
            productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful())
                   {
                       Toast.makeText(AdminMaintainProductsActivity.this,"Changes applied successfully",Toast.LENGTH_LONG).show();
                       Intent intent = new Intent(AdminMaintainProductsActivity.this, SellerCategoryActivity.class);
                       startActivity(intent );
                       finish();
                   }
                }
            });
        }
    }
    public void deleteThisProduct()
    {
        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AdminMaintainProductsActivity.this,"The product is deleted successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdminMaintainProductsActivity.this, SellerCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

}
