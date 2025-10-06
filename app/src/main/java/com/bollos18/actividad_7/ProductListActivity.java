package com.bollos18.actividad_7;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerProducts;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerProducts = findViewById(R.id.recyclerProducts);
        setupProducts();
        setupRecyclerView();
    }

    private void setupProducts() {
        productList = new ArrayList<>();
        productList.add(new Product("🎧 Auriculares Premium", "Sonido de alta calidad con cancelación de ruido", "", "https://www.amazon.com/headphones"));
        productList.add(new Product("📱 Smartphone", "Última tecnología en telefonía móvil", "", "https://www.samsung.com/smartphones"));
        productList.add(new Product("💻 Laptop Gaming", "Para gamers profesionales y entusiastas", "", "https://www.asus.com/laptops"));
        productList.add(new Product("⌚ Smartwatch", "Fitness, salud y conectividad en tu muñeca", "", "https://www.apple.com/watch"));
        productList.add(new Product("📷 Cámara Digital", "Fotografía profesional para capturar momentos", "", "https://www.canon.com/cameras"));
    }

    private void setupRecyclerView() {
        productAdapter = new ProductAdapter(this, productList);
        recyclerProducts.setLayoutManager(new LinearLayoutManager(this));
        recyclerProducts.setAdapter(productAdapter);
    }
}
