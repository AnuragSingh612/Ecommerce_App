package com.example.delivery.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.delivery.EndUser.HomeActivity;
import com.example.delivery.EndUser.MainActivity;
import com.example.delivery.R;

public class AdminHomeActivity extends AppCompatActivity {
    private Button LogoutBtn,checkOrdersBtn,maintainProductsBtn,checkApproveBtn,checklist,deliverable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        LogoutBtn=findViewById(R.id.admin_logout_btn);
        checkOrdersBtn=findViewById(R.id.check_orders_btn);
        maintainProductsBtn=findViewById(R.id.maintain_btn);
        checkApproveBtn=findViewById(R.id.check_approve_btn);
        checklist= findViewById(R.id.check_list);
        deliverable=findViewById(R.id.Order_to_deliver);
        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, HomeActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);



            }
        });
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        checkOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminHomeActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });
        checkApproveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(AdminHomeActivity.this,checkNewProducts.class);
            startActivity(intent);
            }
        });
        checklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminHomeActivity.this,AdminListViewActivity.class);
                startActivity(intent);
            }
        });
        deliverable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminHomeActivity.this,Admin_deliverable.class);
                startActivity(intent);
            }
        });
    }
}
