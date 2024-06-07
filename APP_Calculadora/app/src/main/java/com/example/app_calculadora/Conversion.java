package com.example.app_calculadora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Conversion extends AppCompatActivity {

    EditText txtGb;
    EditText txtMb;
    EditText txtKb;
    EditText txtBy;
    Button buttonConvertir;
    Button buttonLimpiar;
    Button buttonCalculadora;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversion);

        txtGb = findViewById(R.id.txtGB);
        txtMb = findViewById(R.id.txtMB);
        txtKb = findViewById(R.id.txtKB);
        txtBy = findViewById(R.id.txtBYTES);
        buttonConvertir = findViewById(R.id.buttonConvertir);
        buttonLimpiar = findViewById(R.id.buttonLimpiar);
        buttonCalculadora = findViewById(R.id.buttonCalculadora);


        buttonConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });

        buttonLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtGb.setText("");
                txtMb.setText("");
                txtKb.setText("");
                txtBy.setText("");
            }
        });

        buttonCalculadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Conversion.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    private void performConversion() {
        String GbText = txtGb.getText().toString();
        String MbText = txtMb.getText().toString();
        String KbText = txtKb.getText().toString();
        String ByText = txtBy.getText().toString();

        if (!GbText.isEmpty()) {
            double gb = Double.parseDouble(GbText);
            txtMb.setText(String.valueOf(gb * 1024));
            txtKb.setText(String.valueOf(gb * 1024 * 1024));
            txtBy.setText(String.valueOf(gb * 1024 * 1024 * 1024));
        } else if (!MbText.isEmpty()) {
            double mb = Double.parseDouble(MbText);
            txtGb.setText(String.valueOf(mb / 1024));
            txtKb.setText(String.valueOf(mb * 1024));
            txtBy.setText(String.valueOf(mb * 1024 * 1024));
        } else if (!KbText.isEmpty()) {
            double kb = Double.parseDouble(KbText);
            txtGb.setText(String.valueOf(kb / (1024 * 1024)));
            txtMb.setText(String.valueOf(kb / 1024));
            txtBy.setText(String.valueOf(kb * 1024));
        } else if (!ByText.isEmpty()) {
            double bytes = Double.parseDouble(ByText);
            txtGb.setText(String.valueOf(bytes / (1024 * 1024 * 1024)));
            txtMb.setText(String.valueOf(bytes / (1024 * 1024)));
            txtKb.setText(String.valueOf(bytes / 1024));
        }
    }
}



