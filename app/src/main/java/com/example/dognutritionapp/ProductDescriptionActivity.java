package com.example.dognutritionapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class ProductDescriptionActivity extends AppCompatActivity {

    private ImageView productImageView;
    private TextView productTitleTextView;
    private TextView productPriceTextView;
    private TextView productDescriptionTextView;
    private TextView productReviewsTextView;
    private Button addToCartButton;
    private Button manageCartButton;
    private DatabaseHelper dbHelper;
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        dbHelper = new DatabaseHelper(this);

        productImageView = findViewById(R.id.productImageView);
        productTitleTextView = findViewById(R.id.productTitleTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView);
        productReviewsTextView = findViewById(R.id.productReviewsTextView);
        addToCartButton = findViewById(R.id.addToCartButton);
        manageCartButton = findViewById(R.id.manageCartButton);

        Intent intent = getIntent();
        productId = intent.getIntExtra("PRODUCT_ID", -1);

        loadProductDetails(productId);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(productId);
            }
        });

        manageCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDescriptionActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadProductDetails(int productId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("products", null, "id=?", new String[]{String.valueOf(productId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("image_url"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            String reviews = cursor.getString(cursor.getColumnIndexOrThrow("reviews"));

            productTitleTextView.setText(name);
            productPriceTextView.setText("$" + price);
            productDescriptionTextView.setText(description);
            productReviewsTextView.setText(reviews);
            Picasso.get().load(imageUrl).into(productImageView);

            cursor.close();
        }
    }

    private void addToCart(int productId) {
        int userId = 1; // Replace with actual user ID
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Check if the product is already in the cart
        Cursor cursor = db.query("cart", new String[]{"id", "quantity"}, "user_id=? AND product_id=?", new String[]{String.valueOf(userId), String.valueOf(productId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            // Update quantity
            int cartId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
            ContentValues values = new ContentValues();
            values.put("quantity", quantity + 1);
            db.update("cart", values, "id=?", new String[]{String.valueOf(cartId)});
        } else {
            // Add new entry
            ContentValues values = new ContentValues();
            values.put("user_id", userId);
            values.put("product_id", productId);
            values.put("quantity", 1);
            db.insert("cart", null, values);
        }

        if (cursor != null) {
            cursor.close();
        }

        Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
    }
}