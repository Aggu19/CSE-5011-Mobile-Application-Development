package com.example.dognutritionapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private List<CartItem> cartList;
    private DatabaseHelper dbHelper;

    public CartAdapter(Context context, List<CartItem> cartList, DatabaseHelper dbHelper) {
        this.context = context;
        this.cartList = cartList;
        this.dbHelper = dbHelper;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_cart, parent, false);
        }

        final CartItem item = cartList.get(position);

        ImageView productImageView = convertView.findViewById(R.id.productImageView);
        TextView productTitleTextView = convertView.findViewById(R.id.productTitleTextView);
        TextView productPriceTextView = convertView.findViewById(R.id.productPriceTextView);
        final TextView productQuantityTextView = convertView.findViewById(R.id.productQuantityTextView);
        Button increaseQuantityButton = convertView.findViewById(R.id.increaseQuantityButton);
        Button decreaseQuantityButton = convertView.findViewById(R.id.decreaseQuantityButton);
        Button removeItemButton = convertView.findViewById(R.id.removeItemButton);

        productTitleTextView.setText(item.getName());
        productPriceTextView.setText("$" + item.getPrice());
        productQuantityTextView.setText(String.valueOf(item.getQuantity()));
        Picasso.get().load(item.getImageUrl()).into(productImageView);

        increaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(item.getCartId(), item.getQuantity() + 1);
                item.setQuantity(item.getQuantity() + 1);
                productQuantityTextView.setText(String.valueOf(item.getQuantity()));
                updateTotalPrice();
            }
        });

        decreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getQuantity() > 1) {
                    updateQuantity(item.getCartId(), item.getQuantity() - 1);
                    item.setQuantity(item.getQuantity() - 1);
                    productQuantityTextView.setText(String.valueOf(item.getQuantity()));
                    updateTotalPrice();
                }
            }
        });

        removeItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(item.getCartId());
                cartList.remove(position);
                notifyDataSetChanged();
                updateTotalPrice();
            }
        });

        return convertView;
    }

    private void updateQuantity(int cartId, int quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", quantity);
        db.update("cart", values, "id=?", new String[]{String.valueOf(cartId)});
    }

    private void removeItem(int cartId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("cart", "id=?", new String[]{String.valueOf(cartId)});
        Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show();
    }

    private void updateTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem item : cartList) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        if (context instanceof CartActivity) {
            ((CartActivity) context).updateTotalPrice();
        }
    }
}