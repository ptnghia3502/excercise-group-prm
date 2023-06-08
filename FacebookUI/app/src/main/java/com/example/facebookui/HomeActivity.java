package com.example.facebookui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    TextView txtEmailHome;
    DatabaseHelper dbHelper;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        txtEmailHome = findViewById(R.id.txtWelcome);
        dbHelper = new DatabaseHelper(this);

        if (getIntent() != null) {
            String username = getIntent().getStringExtra("username");
            txtEmailHome.setText("Welcome " + username + "!");

            Button btnAddProduct = findViewById(R.id.btnAddProduct);
            Button btnRetrieveProducts = findViewById(R.id.btnRetrieveProducts);
            Button btnUpdateProduct = findViewById(R.id.btnUpdateProduct);
            Button btnDeleteProduct = findViewById(R.id.btnDeleteProduct);

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            productAdapter = new ProductAdapter();
            recyclerView.setAdapter(productAdapter);

            productList = new ArrayList<>();

            btnAddProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Add a product
                    Product product = new Product();
                    product.setName("Sample Product");
                    product.setDescription("This is a sample product");
                    product.setPrice(10.99);

                    addProduct(product);
                    updateProductList();

                    Toast.makeText(HomeActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                }
            });

            btnRetrieveProducts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProductList();
                    Toast.makeText(HomeActivity.this, "Refresh successfully", Toast.LENGTH_SHORT).show();
                }
            });

            btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Update a product
                    if (!productList.isEmpty()) {
                        int index = 0; // Set the appropriate index
                        Product updatedProduct = productList.get(index);
                        updatedProduct.setName("Updated Product");
                        updatedProduct.setDescription("Product is updated");
                        updatedProduct.setPrice(19.99);

                        updateProduct(index, updatedProduct);
                        updateProductList();

                        Toast.makeText(HomeActivity.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Delete a product
                    if (!productList.isEmpty()) {
                        int index = 1; // Set the appropriate index

                        deleteProduct(index);
                        updateProductList();

                        Toast.makeText(HomeActivity.this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void addProduct(Product product) {
        productList.add(product);
    }

    private void updateProduct(int index, Product updatedProduct) {
        if (index >= 0 && index < productList.size()) {
            productList.set(index, updatedProduct);
            productAdapter.notifyItemChanged(index);
        }
    }


    private void deleteProduct(int index) {
        if (index >= 0 && index < productList.size()) {
            productList.remove(index);
        }
    }

    private void updateProductList() {
        productAdapter.setProducts(productList);
        productAdapter.notifyDataSetChanged();
    }
}