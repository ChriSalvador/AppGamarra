package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BusquedaActivity extends AppCompatActivity {

    Button btnRegresoBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        btnRegresoBusqueda = findViewById(R.id.btnRegresoBusqueda);
        btnRegresoBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BusquedaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}