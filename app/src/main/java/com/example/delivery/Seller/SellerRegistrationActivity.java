package com.example.delivery.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.delivery.EndUser.MainActivity;
import com.example.delivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerRegistrationActivity extends AppCompatActivity {
    private Button sellerLoginbegin;
    private EditText nameInput,phoneInput,passwordInput,addressInput,emailInput;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);
        sellerLoginbegin= findViewById(R.id.seller_already_have_account);
        nameInput= findViewById(R.id.seller_name);
        phoneInput=findViewById(R.id.seller_phone);
        passwordInput=findViewById(R.id.seller_password);
        emailInput=findViewById(R.id.seller_email);
        addressInput=findViewById(R.id.seller_address);
        registerButton=findViewById(R.id.Register);
        loadingBar = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        sellerLoginbegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SellerRegistrationActivity.this,Seller_login_Activity.class);
                startActivity(intent);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSeller();
            }
        });
    }
    public void registerSeller()
    {

        final String name= nameInput.getText().toString();
        final String phone= phoneInput.getText().toString();
        final String email= emailInput.getText().toString();
        String password= passwordInput.getText().toString();
        final String address= addressInput.getText().toString();
        if(!name.equals("")&& !phone.equals("")&& !email.equals("")&& !password.equals("")&& !address.equals(""))
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful())
                           {
                               final DatabaseReference rootref;
                                 rootref= FirebaseDatabase.getInstance().getReference();
                                 String sid = mAuth.getCurrentUser().getUid();
                               HashMap<String, Object> sellermap= new HashMap<>();
                               sellermap.put("pid",sid);
                               sellermap.put("phone",phone);
                               sellermap.put("email",email);
                               sellermap.put("address",address);
                               sellermap.put("name",name);
                               rootref.child("Sellers").child(sid).updateChildren(sellermap)
                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               loadingBar.dismiss();
                                               Toast.makeText(SellerRegistrationActivity.this,"You are registered",Toast.LENGTH_LONG).show();
                                               Intent intent = new Intent(SellerRegistrationActivity.this, SellerHomeActivity.class);
                                               intent.addFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                               startActivity(intent);
                                               finish();
                                           }
                                       });


                           }
                        }
                    });

        }
        else
        {
            Toast.makeText(SellerRegistrationActivity.this,"All fields are mandatory ",Toast.LENGTH_LONG).show();
        }
    }
}
