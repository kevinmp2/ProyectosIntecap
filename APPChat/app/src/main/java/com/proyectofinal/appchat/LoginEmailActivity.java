package com.proyectofinal.appchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.proyectofinal.appchat.databinding.ActivityLoginBinding;
import com.proyectofinal.appchat.databinding.ActivityLoginEmailBinding;

public class LoginEmailActivity extends AppCompatActivity {

    private ActivityLoginEmailBinding binding;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarInformacion();
            }
        });

        binding.tvRecuperarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginEmailActivity.this, OlvidasteContrasenha.class));
            }
        });

        binding.txtViewRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginEmailActivity.this, RegistroEmailActivity.class));
            }
        });

    }

    private String email, password;
    private void validarInformacion() {
        email = binding.emailL.getText().toString().trim();
        password = binding.password.getText().toString().trim();

        if(email.isEmpty()){
            binding.emailL.setError("Ingresar email");
            binding.emailL.requestFocus();
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailL.setError("Email invalido");
            binding.emailL.requestFocus();
        } else if(password.isEmpty()){
            binding.password.setError("Ingrese la contrasenha");
            binding.password.requestFocus();
        } else{
            loginUsuario();
        }

    }

    private void loginUsuario() {
        progressDialog.setMessage("Ingresando");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.dismiss();
                        startActivity(new Intent(LoginEmailActivity.this, MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(
                                LoginEmailActivity.this,
                                "No se pudo iniciar sesion " + e.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });


    }
}