package com.example.app_calculadora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    double numeroUno;
    String operacion;
    TextView resultadoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnUno = findViewById(R.id.buttonUno);
        Button btnDos = findViewById(R.id.buttonDos);
        Button btnTres = findViewById(R.id.buttonTres);
        Button btnCuantro = findViewById(R.id.buttonCuatro);
        Button btnCinco = findViewById(R.id.buttonCinco);
        Button btnSeis = findViewById(R.id.buttonSeis);
        Button btnSiete = findViewById(R.id.buttonSiete);
        Button btnOcho = findViewById(R.id.buttonOcho);
        Button btnNueve = findViewById(R.id.buttonNueve);
        Button btnCero = findViewById(R.id.buttonCero);


        Button potencia = findViewById(R.id.buttonPotencia);
        Button raiz = findViewById(R.id.buttonRaiz);
        Button division = findViewById(R.id.buttonDiv);
        Button multiplicacion = findViewById(R.id.buttonMulti);
        Button suma = findViewById(R.id.buttonSuma);
        Button resta = findViewById(R.id.buttonResta);
        Button del = findViewById(R.id.buttonDel);
        Button punto = findViewById(R.id.buttonPunto);
        Button igual = findViewById(R.id.buttonIgual);
        Button conver = findViewById(R.id.buttonConversion);

        ArrayList<Button> numeros = new ArrayList<>();
        numeros.add(btnUno);
        numeros.add(btnDos);
        numeros.add(btnTres);
        numeros.add(btnCuantro);
        numeros.add(btnCinco);
        numeros.add(btnSeis);
        numeros.add(btnSiete);
        numeros.add(btnOcho);
        numeros.add(btnNueve);
        numeros.add(btnCero);

        resultadoView = findViewById(R.id.txtCalculadora);

        for(Button button:numeros){
            button.setOnClickListener(view -> {
                String buttonText = button.getText().toString();
                if(!resultadoView.getText().toString().equals("0")){
                    resultadoView.setText(resultadoView.getText().toString() + buttonText);
                }else{
                    resultadoView.setText(buttonText);
                }
            });
        }

        ArrayList<Button> operaciones = new ArrayList<>();
        operaciones.add(potencia);
        operaciones.add(raiz);
        operaciones.add(division);
        operaciones.add(multiplicacion);
        operaciones.add(suma);
        operaciones.add(resta);

        for (Button button: operaciones) {
            button.setOnClickListener(view -> {
              numeroUno = Double.parseDouble(resultadoView.getText().toString());
              operacion = button.getText().toString();
              resultadoView.setText("0");
            });
        }

        del.setOnClickListener(view -> {
            String num = resultadoView.getText().toString();
            if(num.length() > 1){
                resultadoView.setText(num.substring(0, num.length()-1));
            }else if(num.length() == 1 && !num.equals("0")){
                resultadoView.setText("0");
            }
        });

        punto.setOnClickListener(view -> {
            if(!resultadoView.getText().toString().contains(".")){
                resultadoView.setText(resultadoView.getText().toString() + ".");
            }
        });

        igual.setOnClickListener(view -> {
            double numeroDos = Double.parseDouble(resultadoView.getText().toString());
            double result = 0;
            switch(operacion){
                case "/":
                    if(numeroDos != 0){
                        result = numeroUno / numeroDos;
                    }else{
                        System.out.println("ERROR DIVISION ENTRE 0");
                    }
                    break;
                case "**":
                    result = Math.pow(numeroUno, numeroDos);
                    break;
                case "1/2":
                    result = Math.sqrt(numeroUno);
                    break;
                case"*":
                    result = numeroUno * numeroDos;
                    break;
                case "+":
                    result = numeroUno + numeroDos;
                    break;
                case "-":
                    result = numeroUno - numeroDos;
                    break;
                default:
                    result = numeroUno + numeroDos;
            }

            resultadoView.setText((String.valueOf(result)));
            numeroUno = result;
        });

        conver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Conversion.class);
                startActivity(intent);
            }
        });

    }
}