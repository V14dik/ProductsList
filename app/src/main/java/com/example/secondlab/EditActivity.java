package com.example.secondlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    ArrayList<Product> products = new ArrayList<>();
    int position;
    EditText name;
    EditText numberOfUnits;
    EditText price;
    DatePicker date;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Bundle listProducts = getIntent().getExtras();

        if (listProducts != null)
        {
            products = (ArrayList<Product>) listProducts.get("products");
            position = (int) listProducts.get("position");
        }

        name = findViewById(R.id.edit_item_name);
        numberOfUnits = findViewById(R.id.edit_item_number_of_units);
        price = findViewById(R.id.edit_item_price);
        date = findViewById(R.id.edit_item_date);
        product = products.get(position);
        initialize();
    }

    public void confirmChanges(View v){
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

        product.setName(name.getText().toString());
        product.setNumberOfUnits(numberOfUnits.getText().toString());
        product.setPrice(price.getText().toString());
        product.setDate(dateString.toString());


        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("products", products);
        startActivity(intent);
    }

    private void initialize(){
        name.setText(product.getName());
        numberOfUnits.setText(product.getNumberOfUnits());
        price.setText(product.getPrice());
        String strDate = product.getDate();
        String [] data = strDate.split("\\.");
        int day = Integer.parseInt(data[0]);
        int month = Integer.parseInt(data[1]) - 1;
        int year = Integer.parseInt(data[2]);
        date.init(year, month, day,null);
    }
}