package com.example.secondlab;

import android.view.LayoutInflater;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private LayoutInflater inflater;
    private int layout;
    private List<Product> products;

    public ProductAdapter(Context context, int resource, List<Product> products){
        super(context, resource, products);
        this.layout = resource;
        this.products = products;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = inflater.inflate(this.layout, parent, false);

        TextView nameView = view.findViewById(R.id.name);
        TextView numberOfUnitsView = view.findViewById(R.id.numberOfUnits);
        TextView dateView = view.findViewById(R.id.date);
        TextView priceView = view.findViewById(R.id.price);

        Product product = products.get(position);

        nameView.setText(product.getName());
        numberOfUnitsView.setText(product.getNumberOfUnits());
        dateView.setText(product.getDate());
        priceView.setText(product.getPrice());
        return view;
    }
}
