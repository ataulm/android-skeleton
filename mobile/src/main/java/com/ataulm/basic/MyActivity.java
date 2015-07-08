package com.ataulm.basic;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyActivity extends Activity {

    private Products products;
    private BaseAdapter productsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        products = createInitialProductList();
        productsAdapter = new ProductsAdapter(products, productClickListener, getLayoutInflater());

        ListView productsListView = (ListView) findViewById(R.id.products_list);
        productsListView.setAdapter(productsAdapter);
    }

    private Products createInitialProductList() {
        return new Products(new ArrayList<>(Arrays.asList(
                new Product("Toothpaste", 0),
                new Product("Detergent", 0),
                new Product("Tissues", 0),
                new Product("Apples", 0),
                new Product("Forklift", 0),
                new Product("Guitar", 0),
                new Product("Paint", 0),
                new Product("Pencils", 0),
                new Product("Scissors", 0),
                new Product("Teddy bear", 0),
                new Product("Thingy", 0)
        )));
    }

    private final ProductClickListener productClickListener = new ProductClickListener() {
        @Override
        public void onMinusClick(Product product) {
            products.removeOneFrom(product);
            productsAdapter.notifyDataSetChanged();
        }

        @Override
        public void onPlusClick(Product product) {
            products.addOneTo(product);
            productsAdapter.notifyDataSetChanged();
        }
    };

    private static class Products implements ProductDataSet {

        private final List<Product> productList;

        Products(List<Product> productList) {
            this.productList = productList;
        }

        @Override
        public int size() {
            return productList.size();
        }

        @Override
        public Product get(int position) {
            return productList.get(position);
        }

        @Override
        public long getId(int position) {
            return position;
        }

        public void removeOneFrom(Product product) {
            int i = productList.indexOf(product);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            Product updatedProduct = new Product(product.name, (product.quantity - 1));
            productList.remove(product);
            productList.add(i, updatedProduct);
        }

        public void addOneTo(Product product) {
            int i = productList.indexOf(product);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            Product updatedProduct = new Product(product.name, (product.quantity + 1));
            productList.remove(product);
            productList.add(i, updatedProduct);
        }
    }

}
