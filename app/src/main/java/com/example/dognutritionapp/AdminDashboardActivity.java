package com.example.dognutritionapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity {

    private ListView listViewUsers;
    private List<String> users;
    private DatabaseHelper dbHelper;

    private Button btnManageProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        listViewUsers = findViewById(R.id.listViewUsers);
        users = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);

        loadUsers();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        listViewUsers.setAdapter(adapter);

        btnManageProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this,ManageProductsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadUsers() {
        Cursor cursor = dbHelper.getAllUsers();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String userName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String userEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));

                List<String> orders = new ArrayList<>();
                Cursor orderCursor = dbHelper.getOrdersByUserId(userId);
                if (orderCursor != null && orderCursor.moveToFirst()) {
                    do {
                        String orderDetails = orderCursor.getString(orderCursor.getColumnIndexOrThrow("details"));
                        orders.add(orderDetails);
                    } while (orderCursor.moveToNext());
                }
                if (orderCursor != null) {
                    orderCursor.close();
                }

                users.add(userName + " (" + userEmail + ") - Orders: " + orders.toString());
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
    }
}
