package com.proyectofinal.appchat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.camera2.CameraExtensionSession;
import android.net.Uri;
import android.os.Build;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.proyectofinal.appchat.databinding.ActivityEditarInformacionBinding;

import java.util.HashMap;

public class EditarInformacion extends AppCompatActivity {

    private ActivityEditarInformacionBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private Uri imagenUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditarInformacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        cargarInformacion();

        binding.Ibregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        binding.btActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarInformacion();
            }
        });
        
        binding.IvEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    abrirGaleria();
                } else {
                    solicitarPermisoAlmacenamiento.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }
        });

        
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galeriaARL.launch(intent);


    }

    private ActivityResultLauncher<Intent> galeriaARL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult resultado) {
                    if(resultado.getResultCode() == Activity.RESULT_OK){
                        Intent data = resultado.getData();
                        imagenUri = data.getData();
                        subirImagenStorage(imagenUri);
                    } else {
                        Toast.makeText(EditarInformacion.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private void subirImagenStorage(Uri imagenUri) {
        progressDialog.setMessage("Subiendo imagen");
        progressDialog.show();

        String rutaImagen = "imagenesPerfil/" + firebaseAuth.getUid();
        StorageReference reference = FirebaseStorage.getInstance().getReference().child(rutaImagen);
        reference.putFile(imagenUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        String urlImagenCargada = uriTask.getResult().toString();
                        if(uriTask.isSuccessful()){
                            actualziarImagenDB(urlImagenCargada);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditarInformacion.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void actualziarImagenDB(String urlImagenCargada) {
        progressDialog.setMessage("Actualizando imagen");
        progressDialog.show();

        HashMap<String, Object> hashMap = new HashMap<>();
        if(imagenUri != null) {
            hashMap.put("imagen", urlImagenCargada);
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        reference.child(firebaseAuth.getUid())
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(EditarInformacion.this, "Imagen de perfil actualizada", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(EditarInformacion.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private ActivityResultLauncher<String> solicitarPermisoAlmacenamiento = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean Concedido) {
                    if(Concedido) {
                        abrirGaleria();
                    } else {
                        Toast.makeText(EditarInformacion.this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private String nombre;
    private void validarInformacion() {
        nombre = binding.etNombre.getText().toString().trim();

        if( nombre.isEmpty()) {
            binding.etNombre.setError("Ingrese su nombre");
            binding.etNombre.requestFocus();
        } else {
            actualziarInfo();
        }
    }

    private void actualziarInfo() {

        progressDialog.setMessage("Actualizando informacion");
        progressDialog.show();

        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("nombre", nombre);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        reference.child(firebaseAuth.getUid())
                .updateChildren(hashmap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(EditarInformacion.this, "Informacion Actualizada", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(EditarInformacion.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void cargarInformacion() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        reference.child(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String nombre = "" + snapshot.child("nombre").getValue();
                        String image = "" + snapshot.child("Image").getValue();

                        binding.etNombre.setText(nombre);

                        Glide.with(getApplicationContext())
                                .load(image)
                                .placeholder(R.drawable.icon_img_perfil)
                                .into(binding.ivPerfil);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}