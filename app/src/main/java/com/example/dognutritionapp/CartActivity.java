package com.example.dognutritionapp;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView cartListView;
    private TextView totalPriceTextView;
    private CartAdapter cartAdapter;
    private DatabaseHelper dbHelper;
    private List<CartItem> cartItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        dbHelper = new DatabaseHelper(this);

        cartListView = findViewById(R.id.cartListView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);

        cartItemList = loadCartItemsFromDatabase();
        cartAdapter = new CartAdapter(this, cartItemList, dbHelper);
        cartListView.setAdapter(cartAdapter);

        updateTotalPrice();
    }

    private List<CartItem> loadCartItemsFromDatabase() {
        List<CartItem> cartItemList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT c.id, p.name, p.image_url, p.price, c.quantity " +
                        "FROM cart c JOIN products p ON c.product_id = p.id " +
                        "WHERE c.user_id = ?",
                new String[]{"1"}); // Replace with actual user ID

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int cartId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("image_url"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

                cartItemList.add(new CartItem(cartId, name, imageUrl, price, quantity));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return cartItemList;
    }

    public void updateTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem item : cartItemList) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        totalPriceTextView.setText("Total: LKR" + totalPrice);
    }
}