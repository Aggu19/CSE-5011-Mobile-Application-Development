package com.example.dognutritionapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ManageProductsActivity extends AppCompatActivity {

    private EditText editTextProductName;
    private EditText editTextProductDescription;
    private EditText editTextProductPrice;
    private EditText editTextProductImageUrl; // New field for image URL
    private Button buttonAddProduct;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);

        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductDescription = findViewById(R.id.editTextProductDescription);
        editTextProductPrice = findViewById(R.id.editTextProductPrice);
        editTextProductImageUrl = findViewById(R.id.editTextProductImageUrl); // Initialize new field
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        dbHelper = new DatabaseHelper(this);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = editTextProductName.getText().toString().trim();
                String productDescription = editTextProductDescription.getText().toString().trim();
                String productPrice = editTextProductPrice.getText().toString().trim();
                String productImageUrl = editTextProductImageUrl.getText().toString().trim(); // Get image URL

                if (!productName.isEmpty() && !productDescription.isEmpty() && !productPrice.isEmpty() && !productImageUrl.isEmpty()) {
                    if (dbHelper.addProduct(productName, productDescription, Double.parseDouble(productPrice), productImageUrl)) {
                        Toast.makeText(ManageProductsActivity.this, "Product added: " + productName, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ManageProductsActivity.this, "Failed to add product", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ManageProductsActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
