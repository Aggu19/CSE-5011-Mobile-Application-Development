package com.example.dognutritionapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public void updateProducts(List<Product> newProductList) {
        this.productList = newProductList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_product_list, parent, false);
        }

        Product product = productList.get(position);

        ImageView productImageView = convertView.findViewById(R.id.productImageView);
        TextView productTitleTextView = convertView.findViewById(R.id.productTitleTextView);
        TextView productPriceTextView = convertView.findViewById(R.id.productPriceTextView);

        productTitleTextView.setText(product.getName());
        productPriceTextView.setText("$" + product.getPrice());
        Picasso.get().load(product.getImageUrl()).into(productImageView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDescriptionActivity.class);
                intent.putExtra("PRODUCT_ID", product.getId());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}