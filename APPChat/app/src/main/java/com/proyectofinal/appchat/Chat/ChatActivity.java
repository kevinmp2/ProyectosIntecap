package com.proyectofinal.appchat.Chat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.proyectofinal.appchat.Adaptadores.AdaptadorChat;
import com.proyectofinal.appchat.Constantes;
import com.proyectofinal.appchat.Modelos.Chat;
import com.proyectofinal.appchat.R;
import com.proyectofinal.appchat.databinding.ActivityChatBinding;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarEntry;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    String miuid = "";

    String uid = "";

    String chatRuta = "";
    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        miuid = firebaseAuth.getUid();

        uid = getIntent().getStringExtra("uid");

        chatRuta = Constantes.chatRuta(uid, miuid);

        binding.adjuntarFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    imagenGaleria();
                }else{
                    solicitarPermisoAlmacenamiento.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }
        });

        binding.Ibregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        binding.enviarFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarMensaje();
            }
        });


        cargarInfo();
        cargarMensajes();
    }

    private void cargarMensajes() {
        ArrayList<Chat> chatArrayList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.child(chatRuta)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chatArrayList.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            try{
                                Chat chat = ds.getValue(Chat.class);
                                chatArrayList.add(chat);
                            }catch (Exception e){

                            }
                        }
                        AdaptadorChat adaptadorChat = new AdaptadorChat(ChatActivity.this, chatArrayList);
                        binding.chatsRV.setAdapter(adaptadorChat);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void validarMensaje() {
        String mensaje = binding.EtMensajeChat.getText().toString().trim();
        long tiempo = Constantes.obtenerTiempo();

        if(mensaje.equals("")){
            Toast.makeText(this, "Ingrese un mensaje", Toast.LENGTH_SHORT).show();
        }else{
            enviarMensaje(Constantes.MENSAJE_TIPO_TEXTO, mensaje, tiempo);
        }
    }


    private void cargarInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        reference.child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String nombre = "" + snapshot.child("nombre").getValue();
                        String imagen = "" + snapshot.child("imagen").getValue();

                        binding.txtNombreUsuarios.setText(nombre);

                        try {
                            Glide.with(ChatActivity.this)
                                    .load(imagen)
                                    .placeholder(R.drawable.icon_img_perfil)
                                    .into(binding.toolbarIV);

                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void imagenGaleria(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galeriaARL.launch(intent);
    }

    private ActivityResultLauncher<Intent> galeriaARL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult resultado) {
                    if(resultado.getResultCode() == ChatActivity.RESULT_OK){
                        Intent data = resultado.getData();
                        imageUri = data.getData();
                        subirImgStorage();
                    }
                    else {
                        Toast.makeText(ChatActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private ActivityResultLauncher<String> solicitarPermisoAlmacenamiento = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean esConcedido) {
                    if(esConcedido){
                        imagenGaleria();
                    }else{
                        Toast.makeText(ChatActivity.this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private void subirImgStorage(){
        progressDialog.setMessage("Subiendo imgen");
        progressDialog.show();

        long tiempo = Constantes.obtenerTiempo();
        String nombreRutaImagen = "ImagenChat/" + tiempo;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(nombreRutaImagen);
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        String urlImagen = uriTask.getResult().toString();
                        if(uriTask.isSuccessful()){
                            enviarMensaje(Constantes.MENSAJE_TIPO_IMAGEN, urlImagen, tiempo);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void enviarMensaje(String tipoMensaje, String mensaje, long tiempo){
        progressDialog.setMessage("Enviar Mensaje");
        progressDialog.show();

        DatabaseReference refChat = FirebaseDatabase.getInstance().getReference("Chats");
        String keyId = "" + refChat.push().getKey();
        HashMap<String, Object> hashMap = new HashMap<>();


        hashMap.put("idMensaje", "" + keyId);
        hashMap.put("tipoMensaje", "" + tipoMensaje);
        hashMap.put("mensaje", "" + mensaje);
        hashMap.put("emisorUid", "" + miuid);
        hashMap.put("receptorUid", "" + uid);
        hashMap.put("tiempo", tiempo);

        refChat.child(chatRuta)
                .child(keyId)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        binding.EtMensajeChat.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ChatActivity.this, "No se pudo enviar el mensaje: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}