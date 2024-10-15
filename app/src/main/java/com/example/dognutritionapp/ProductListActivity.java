package com.example.dognutritionapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private GridView productGridView;
    private Spinner filterBrandSpinner, filterTypeSpinner, filterAgeSpinner, sortPriceSpinner;
    private ProductAdapter productAdapter;
    private DatabaseHelper dbHelper;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        dbHelper = new DatabaseHelper(this);

        productGridView = findViewById(R.id.productGridView);
        filterBrandSpinner = findViewById(R.id.filterBrandSpinner);
        filterTypeSpinner = findViewById(R.id.filterTypeSpinner);
        filterAgeSpinner = findViewById(R.id.filterAgeSpinner);
        sortPriceSpinner = findViewById(R.id.sortPriceSpinner);

        productList = loadProductsFromDatabase();
        productAdapter = new ProductAdapter(this, productList);
        productGridView.setAdapter(productAdapter);

        setupFiltersAndSorting();
    }

    private void setupFiltersAndSorting() {
        // Set up brand filter
        List<String> brands = getUniqueValues("brand");
        brands.add(0, "All Brands");
        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, brands);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterBrandSpinner.setAdapter(brandAdapter);
        filterBrandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                filterAndSortProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Set up type filter
        List<String> types = getUniqueValues("type");
        types.add(0, "All Types");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterTypeSpinner.setAdapter(typeAdapter);
        filterTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                filterAndSortProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Set up age filter
        List<String> ages = getUniqueValues("age");
        ages.add(0, "All Ages");
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ages);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterAgeSpinner.setAdapter(ageAdapter);
        filterAgeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                filterAndSortProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Set up price sort
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Price: Low to High", "Price: High to Low"});
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortPriceSpinner.setAdapter(sortAdapter);
        sortPriceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                filterAndSortProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private List<String> getUniqueValues(String column) {
        List<String> values = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + column + " FROM products", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                values.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return values;
    }

    private void filterAndSortProducts() {
        String selectedBrand = filterBrandSpinner.getSelectedItem().toString();
        String selectedType = filterTypeSpinner.getSelectedItem().toString();
        String selectedAge = filterAgeSpinner.getSelectedItem().toString();
        boolean sortAscending = sortPriceSpinner.getSelectedItemPosition() == 0;

        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            if ((selectedBrand.equals("All Brands") || product.getBrand().equals(selectedBrand)) &&
                    (selectedType.equals("All Types") || product.getType().equals(selectedType)) &&
                    (selectedAge.equals("All Ages") || product.getAge().equals(selectedAge))) {
                filteredList.add(product);
            }
        }

        Collections.sort(filteredList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return sortAscending ? Double.compare(p1.getPrice(), p2.getPrice()) : Double.compare(p2.getPrice(), p1.getPrice());
            }
        });

        productAdapter.updateProducts(filteredList);
    }

    private List<Product> loadProductsFromDatabase() {
        List<Product> productList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("products", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String brand = cursor.getString(cursor.getColumnIndexOrThrow("brand"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                String age = cursor.getString(cursor.getColumnIndexOrThrow("age"));
                String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("image_url"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String reviews = cursor.getString(cursor.getColumnIndexOrThrow("reviews"));

                productList.add(new Product(id, name, brand, type, age, imageUrl, price, description, reviews));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return productList;
    }
}