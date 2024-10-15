package com.example.dognutritionapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextEmailAddress, editTextNumberPassword, editTextNumberPassword2, editTextAddress, editTextPayment;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextNumberPassword = findViewById(R.id.editTextNumberPassword);
        editTextNumberPassword2 = findViewById(R.id.editTextNumberPassword2);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPayment = findViewById(R.id.editTextPayment);

        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name = editTextUsername.getText().toString().trim();
        String email = editTextEmailAddress.getText().toString().trim();
        String password = editTextNumberPassword.getText().toString().trim();
        String confirmPassword = editTextNumberPassword2.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String paymentMethods = editTextPayment.getText().toString().trim();

        String validationResult = validateInputs(name, email, password, confirmPassword, address, paymentMethods);
        if (!validationResult.equals("VALID")) {
            Toast.makeText(this, validationResult, Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", name);
        values.put("email", email);
        values.put("password", hashPassword(password));
        values.put("address", address);
        values.put("paymentMethods", paymentMethods);

        long newRowId = database.insert("users", null, values);
        //database.close();

        if (newRowId != -1) {
            Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(RegisterActivity.this, "Registration Failure!", Toast.LENGTH_SHORT).show();
        }
    }

    public String validateInputs(String name, String email, String password, String confirmPassword, String address, String paymentMethods) {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || address.isEmpty() || paymentMethods.isEmpty() || confirmPassword.isEmpty()) {
            return "Please fill all fields";
        } else if (!isValidEmail(email)) {
            return "Please enter a valid email";
        } else if (password.length() < 5) {
            return "Password must be at least 5 characters";
        } else if (!password.equals(confirmPassword)) {
            return "Passwords do not match";
        } else {
            return "VALID";
        }
    }

    public boolean isValidEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        return emailPattern.matcher(email).matches();
    }

    private String hashPassword(String password) {
        // Implement a hashing function like SHA-256 or bcrypt
        // For simplicity, we'll just return the password itself in this example
        return password; // Replace with actual hashing logic
    }
}
