package com.example.dognutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserProfileActivity extends AppCompatActivity {

    private EditText editTextNewUName, editTextNewAddress, editTextNewPMethod;
    private Button btnSave, btnBack;
    private DatabaseHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userId = getIntent().getIntExtra("USER_ID", -1);
        loadUserProfile();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
    }

    private void loadUserProfile() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "users",
                new String[]{"name", "address", "payment_methods"},
                "id=?",
                new String[]{String.valueOf(userId)},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            editTextNewUName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            editTextNewAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow("address")));
            editTextNewPMethod.setText(cursor.getString(cursor.getColumnIndexOrThrow("payment_methods")));
            cursor.close();
        }
    }
    private void updateUserProfile() {
        String name = editTextNewUName.getText().toString().trim();
        String address = editTextNewAddress.getText().toString().trim();
        String paymentMethods = editTextNewPMethod.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty() || paymentMethods.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);
        values.put("payment_methods", paymentMethods);

        int rowsUpdated = db.update("users", values, "id=?", new String[]{String.valueOf(userId)});
        if (rowsUpdated > 0) {
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Profile update failed", Toast.LENGTH_SHORT).show();
        }
    }
    private void logoutUser() {
        Intent intent = new Intent(UserProfileActivity.this, UserHomeActivity.class);
        startActivity(intent);
        finish();
    }
}
