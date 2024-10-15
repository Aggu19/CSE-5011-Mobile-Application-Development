package com.example.dognutritionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    private EditText editTextAdminUser;
    private EditText editTextNumberPassword3;
    private Button btnLoginn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        editTextAdminUser = findViewById(R.id.editTextAdminUser);
        editTextNumberPassword3 = findViewById(R.id.editTextNumberPassword3);
        btnLoginn = findViewById(R.id.btnLoginn);

        btnLoginn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextAdminUser.getText().toString().trim();
                String password = editTextNumberPassword3.getText().toString().trim();

                if (isAdmin(username, password)) {
                    Intent intent = new Intent(AdminActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AdminActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isAdmin(String username, String password) {
        return "admin".equals(username) && "admin123".equals(password);
    }
}
