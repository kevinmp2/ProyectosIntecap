package com.proyectofinal.appchat;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseAuth;
import com.proyectofinal.appchat.databinding.ActivityOlvidasteContrasenhaBinding;

import java.io.FileReader;

public class OlvidasteContrasenha extends AppCompatActivity {

    private ActivityOlvidasteContrasenhaBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOlvidasteContrasenhaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.Ibregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        binding.btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarInformacion();
            }
        });
    }

    private String email = "";
    private void validarInformacion() {

        email = binding.etEmail.getText().toString().trim();

        if(email.isEmpty()){
            binding.etEmail.setError("Ingrese email");
            binding.etEmail.requestFocus();
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.setError("Email no valido");
            binding.etEmail.requestFocus();
        } else {
            enviarInformacion();
        }


    }

    private void enviarInformacion() {
        progressDialog.setMessage("Enviando las instrucciones a : " + email);
        progressDialog.show();

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(OlvidasteContrasenha.this, "Instrucciones enviadas", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(OlvidasteContrasenha.this, "Fallo el envio de instrucciones: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}