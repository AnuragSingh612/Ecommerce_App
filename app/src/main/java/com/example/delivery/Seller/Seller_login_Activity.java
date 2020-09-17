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

public class Seller_login_Activity extends AppCompatActivity {
private Button loginSellerBtn;
private EditText emailInput,passwordInput;
private ProgressDialog loadingBar;
private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login_);
        emailInput=findViewById(R.id.seller_login_email);
        passwordInput=findViewById(R.id.seller_login_password);
        loginSellerBtn=findViewById(R.id.seller_login_btn);

        mAuth= FirebaseAuth.getInstance();
        loadingBar= new ProgressDialog(this);
        loginSellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSeller();
            }
        });

    }
    public void loginSeller()
    {

        String email=emailInput.getText().toString();
        String password=passwordInput.getText().toString();
        if(!email.equals("") && (!password.equals("")))
        {
            loadingBar.setTitle("Logging in...");
            loadingBar.setMessage("Please wait you are logging in ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        loadingBar.dismiss();
                        Intent intent = new Intent(Seller_login_Activity.this, SellerHomeActivity.class);

                        intent.addFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this,"Please complete the login form",Toast.LENGTH_LONG).show();
        }

    }

}
