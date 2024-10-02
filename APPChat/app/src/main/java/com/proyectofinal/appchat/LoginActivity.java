package com.proyectofinal.appchat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.credentials.Credential;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyectofinal.appchat.databinding.ActivityLoginBinding;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private GoogleSignInClient googleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_cliente_id))
                        .requestEmail()
                                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        comprobarSesion();

        binding.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, LoginEmailActivity.class));
            }
        });


        binding.google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarGoogle();
            }
        });

    }

    private void iniciarGoogle() {

        Intent googleSingInIntent = googleSignInClient.getSignInIntent();
        googleSignInARL.launch(googleSingInIntent);
    }

    private ActivityResultLauncher<Intent> googleSignInARL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult resultado) {


                    if( resultado.getResultCode() == Activity.RESULT_OK){
                        Intent data = resultado.getData();

                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {

                            GoogleSignInAccount cuenta = task.getResult(ApiException.class);
                            autenticarCuentaGoogle(cuenta.getIdToken());

                        } catch (ApiException e) {
                            Toast.makeText(
                                    LoginActivity.this,
                                    "" + e.getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    } else {
                        Toast.makeText(
                                LoginActivity.this,
                                "Cancelado",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                }
            }
    );

    private void autenticarCuentaGoogle(String idToken) {
        AuthCredential credencial = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credencial)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(authResult.getAdditionalUserInfo().isNewUser()){
                            actualizarInfoUsuario();
                        } else {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finishAffinity();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                LoginActivity.this,
                                "" + e.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

    }

    private void actualizarInfoUsuario() {
       progressDialog.setMessage("Guardando informacion");
       String uidUsuario = firebaseAuth.getUid();
       String nombreUsuario = firebaseAuth.getCurrentUser().getDisplayName();
       String emailUsuario = firebaseAuth.getCurrentUser().getEmail();
       long timeRegistro = Constantes.obtenerTiempo();

       HashMap<String, Object> datosUsuario = new HashMap<>();
       datosUsuario.put("uid", uidUsuario);
       datosUsuario.put("nombre", nombreUsuario);
       datosUsuario.put("email", emailUsuario);
       datosUsuario.put("TiempoRegistro", timeRegistro);
       datosUsuario.put("Proveedor", "Google");
       datosUsuario.put("Estado", "Online");
       datosUsuario.put("Image", "");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        reference.child(uidUsuario)
                .setValue(datosUsuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finishAffinity();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      Toast.makeText(
                    LoginActivity.this,
                      "Fallo al actualizar usuario" + e.getMessage(),
                           Toast.LENGTH_SHORT
                       ).show();
                    }
                });


    }

    private void comprobarSesion() {
        if(firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        }
    }
}