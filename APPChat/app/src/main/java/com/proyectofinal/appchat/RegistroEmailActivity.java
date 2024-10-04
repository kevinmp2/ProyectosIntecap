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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;
import com.proyectofinal.appchat.databinding.ActivityRegistroEmailBinding;

import java.util.HashMap;

public class RegistroEmailActivity extends AppCompatActivity {
    private ActivityRegistroEmailBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarInformacion();
            }
        });
    }

    private String nombre, email, password, repeatPassword;

    private void validarInformacion() {
        nombre = binding.nombre.getText().toString().trim();
        email = binding.emailRegistro.getText().toString().trim();
        password = binding.passwordRegistro.getText().toString().trim();
        repeatPassword = binding.repeatPassword.getText().toString().trim();
        
        if (nombre.isEmpty()){
            binding.nombre.setError("Ingrese su nombre");
            binding.nombre.requestFocus();
            
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailRegistro.setError("Correo Invalido");
            binding.emailRegistro.requestFocus();
            
        } else if(email.isEmpty()){
            binding.emailRegistro.setError("Ingrese el correo");
            binding.emailRegistro.requestFocus();

        } else if(password.isEmpty()) {
            binding.passwordRegistro.setError("Ingrese la contrasenha");
            binding.passwordRegistro.requestFocus();

        } else if (repeatPassword.isEmpty()) {
            binding.repeatPassword.setError("Repita al contrasenha");
            binding.repeatPassword.requestFocus();

        } else if(!password.equals(repeatPassword)){
            binding.repeatPassword.setError("La contrasenha no coincide");
            binding.repeatPassword.requestFocus();
        } else {
            registrarUsuario();
        }
    }

    private void registrarUsuario() {
        progressDialog.setMessage("Creando cuenta");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        actualizarInformacion();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                RegistroEmailActivity.this,
                                "Fallo al registar usuario" + e.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void actualizarInformacion() {
        progressDialog.setMessage("Guardando informacion");

        String uidUsuario = firebaseAuth.getUid();
        String nombreUsuario = nombre;
        String emailUsuario = firebaseAuth.getCurrentUser().getEmail();
        long timeRegistro = Constantes.obtenerTiempo();


        HashMap<String, Object> datosUsuario = new HashMap<>();

        datosUsuario.put("uid", uidUsuario);
        datosUsuario.put("nombre", nombreUsuario);
        datosUsuario.put("email", emailUsuario);
        datosUsuario.put("TiempoRegistro", timeRegistro);
        datosUsuario.put("Proveedor", "Email");
        datosUsuario.put("Estado", "Online");
        datosUsuario.put("Image", "");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        reference.child(uidUsuario)
                .setValue(datosUsuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        startActivity(new Intent(RegistroEmailActivity.this, MainActivity.class));
                        finishAffinity();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                RegistroEmailActivity.this,
                                "Fallo al actualizar usuario" + e.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}


