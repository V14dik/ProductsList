package com.example.secondlab;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    ArrayList<Product> products = new ArrayList<>();
    ListView productsList;
    int requestCode = 1;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle listProducts = getIntent().getExtras();

        if (listProducts != null)
        {
            products = (ArrayList<Product>) listProducts.get("products");
        }
        else{
            setInitialData();
        }
        update(products);
        productsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(view, position);
                return true;
            }
        });
    }

    private void showPopupMenu(View v, int position) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu1:
                        products.remove(position);
                        update(products);
                        return true;
                    case R.id.menu2:
                        Intent intent = new Intent( MainActivity.this, EditActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("products", products);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    public void addNewItem(View v){
        Intent intent = new Intent(this, AddNewItemActivity.class);
        intent.putExtra("products", products);
        startActivity(intent);
    }

    public void openFileChooser(View view)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, requestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        products.clear();
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode && resultCode == Activity.RESULT_OK)
        {
            if (data == null)
            {
                return;
            }

            Uri uri = data.getData();
            setInitialData(uri);

            update(products);
        }
    }

    private void update(ArrayList<Product> products){
        productsList = findViewById(R.id.product_list);
        ProductAdapter productAdapter = new ProductAdapter(this, R.layout.list_item, products);
        productsList.setAdapter(productAdapter);
    }

    private void setInitialData(Uri uri){
        try {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(this.getContentResolver().openInputStream(uri)));
            String line = bufferedReader.readLine();
            while (line != null) {
                Product product = new Product(line);
                products.add(product);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sort(View v){
        ArrayList<Product> sortProducts = new ArrayList<>();
        for (int i = 0; i < products.size(); i ++){
            product = products.get(i);
            String strDate = product.getDate();
            String [] data = strDate.split("\\.");
            int day = Integer.parseInt(data[0]);
            int month = Integer.parseInt(data[1]);
            int year = Integer.parseInt(data[2]);

            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();
            int currentMonth = currentDate.getMonthValue();
            int currentDay = currentDate.getDayOfMonth() + 1;

            if (currentYear - year <= 0){
                if(currentMonth - month <= 1){
                    if(currentMonth - month != 0){
                        if(currentDay > day){
                            if(Integer.parseInt(product.getPrice().replace("руб.", "")) > 1000000)
                                sortProducts.add(product);
                        }
                    }
                }
                else {
                    if(Integer.parseInt(product.getPrice().replace("руб.", "")) > 1000000)
                        sortProducts.add(product);
                }
            }
            else {
                if(Integer.parseInt(product.getPrice().replace("руб.", "")) > 1000000)
                    sortProducts.add(product);
            }
        }
        Collections.sort(sortProducts, new Comparator<Product>() {
            public int compare(Product p1, Product p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
        update(sortProducts);
    }

    private void setInitialData(){
        products.add(new Product("Cort AF510", "10000", "1000001", "10.02.2021"));
        products.add(new Product("Cort AD810", "2130", "170", "11.05.2021"));
        products.add(new Product("Fender CD60", "8000", "2000000", "06.10.2021"));
        products.add(new Product("Randon RGI-500", "189", "810", "12.02.2021"));
    }
}