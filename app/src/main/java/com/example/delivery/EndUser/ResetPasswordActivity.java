package com.example.delivery.EndUser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.delivery.R;

public class ResetPasswordActivity extends AppCompatActivity {
private String check="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        check = getIntent().getStringExtra("check");
    }
}
