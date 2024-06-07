package com.example.app_calculadora;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPricipal extends AppCompatActivity {


    Button FuncionAritmetica;
    Button FuncionConversion;
    Button Salir;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menupricipal);

        FuncionAritmetica = findViewById(R.id.buttonAritmeticas);
        FuncionConversion = findViewById(R.id.buttonConver);
        Salir = findViewById(R.id.buttonSalir);


        FuncionAritmetica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPricipal.this, MainActivity.class);
                startActivity(intent);
            }
        });

        FuncionConversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPricipal.this, Conversion.class);
                startActivity(intent);
            }
        });

        Salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });
    }
}
