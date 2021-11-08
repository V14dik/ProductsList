package com.example.secondlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;

public class AddNewItemActivity extends AppCompatActivity {

    ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        Bundle listProducts = getIntent().getExtras();

        if (listProducts != null)
        {
            products = (ArrayList<Product>) listProducts.get("products");
        }
    }

    public void addNew(View v){
        EditText name = (EditText)findViewById(R.id.new_item_name);
        EditText numberOfUnits = (EditText)findViewById(R.id.new_item_number_of_units);
        EditText price = (EditText)findViewById(R.id.new_item_price);
        DatePicker date = (DatePicker) findViewById(R.id.new_item_date);
        StringBuilder dateString = new StringBuilder();
        if(date.getDayOfMonth() < 10){
            dateString.append(0);
        }
        dateString.append(date.getDayOfMonth());
        dateString.append('.');
        if(date.getMonth() < 9){
            dateString.append(0);
        }
        dateString.append(date.getMonth() + 1);
        dateString.append('.');
        dateString.append(date.getYear());

        Product newProduct = new Product(name.getText().toString(),
                                            numberOfUnits.getText().toString(),
                                            price.getText().toString(),
                                            dateString.toString());
        products.add(newProduct);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("products", products);
        startActivity(intent);
    }
}