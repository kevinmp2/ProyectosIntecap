package com.proyectofinal.appchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.proyectofinal.appchat.databinding.ActivityCambiarPasswordBinding;

public class CambiarPassword extends AppCompatActivity {

    private ActivityCambiarPasswordBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCambiarPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.Ibregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        
        binding.btnCambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarInformacion();   
            }
        });



    }

    private String password_actual = "";
    private String password_nueva = "";
    private String repetir_password = "";
    private void validarInformacion() {

        password_actual = binding.etPasswordActual.getText().toString().trim();
        password_nueva = binding.etPasswordNuevo.getText().toString().trim();
        repetir_password = binding.txtRepetirContrasenha.getText().toString().trim();

        if(password_actual.isEmpty()){
            binding.etPasswordActual.setError("Ingrese contrasenha actual");
            binding.etPasswordActual.requestFocus();
        } else if(password_nueva.isEmpty()) {
            binding.etPasswordNuevo.setError("Ingrese la contrasenha nueva");
            binding.etPasswordNuevo.requestFocus();
        } else if(repetir_password.isEmpty()) {
            binding.txtRepetirContrasenha.setError("Repita la contrasenha");
            binding.txtRepetirContrasenha.requestFocus();
        } else if(!password_nueva.equals(repetir_password)){
            binding.txtRepetirContrasenha.setError("La contrasenha no coincide");
            binding.txtRepetirContrasenha.requestFocus();
        } else {
            auntenticarUsuario();
        }


    }

    private void auntenticarUsuario() {
        progressDialog.setMessage("Autenticando usuario");
        progressDialog.show();

        AuthCredential authCredential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), password_actual);
        firebaseUser.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        actualizarInformacion();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(CambiarPassword.this, "Error en la autenticacion" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void actualizarInformacion() {
        progressDialog.setMessage("Actualizando contrasenha");
        progressDialog.show();

        firebaseUser.updatePassword(password_nueva)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(CambiarPassword.this, "La contrasenha se actualizo correctamente", Toast.LENGTH_SHORT).show();
                        cerrarSesion();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(CambiarPassword.this, "Error en la actualizacion de la contrasenha " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



    }

    private void cerrarSesion() {
        firebaseAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity();
    }
}