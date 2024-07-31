package com.mastercoding.themovieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registrar extends AppCompatActivity {

    private EditText nameuser;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private Button registrar;
    private Button ingresar;
    private MyDatabaseHelper myDatabaseHelper;



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);

        nameuser = findViewById(R.id.IdNombre);
        lastName = findViewById(R.id.IdApellido);
        email = findViewById(R.id.CorreoId);
        password = findViewById(R.id.Password);
        registrar = findViewById(R.id.BtRegistro);
        ingresar = findViewById(R.id.BtLogin);
        myDatabaseHelper = new MyDatabaseHelper(this);


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUsers();
            }
        });

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registrar.this, Login.class);
                startActivity(intent);
            }
        });
    }


    public void saveUsers(){


        String name = nameuser.getText().toString().trim();
        String lastnm = lastName.getText().toString().trim();
        String em = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        // Validación de correo electrónico
        String emailPattern = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        if (!em.matches(emailPattern)) {
            Toast.makeText(this, "Correo electrónico no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validación de contraseña (mínimo 8 caracteres)
        if (pass.length() < 8) {
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!name.isEmpty() && !lastnm.isEmpty() && !em.isEmpty() && !pass.isEmpty()) {
            if (myDatabaseHelper.checkUserExists(em)) {
                Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
            } else {
                SQLiteDatabase db = null;
                try {
                    db = myDatabaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("name", name);
                    values.put("lastname", lastnm);
                    values.put("email", em);
                    values.put("password", pass);
                    long result = db.insert("users", null, values);
                    if (result != -1) {
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        nameuser.setText("");
                        lastName.setText("");
                        email.setText("");
                        password.setText("");
                    } else {
                        Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                    Log.e("Registrar", "Error al insertar en la base de datos", e);
                    Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show();
                } finally {
                    if (db != null && db.isOpen()) {
                        db.close();
                    }
                }
            }
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
