package com.proyectofinal.appchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.proyectofinal.appchat.Fragmentos.FragmentChat;
import com.proyectofinal.appchat.Fragmentos.FragmentPerfil;
import com.proyectofinal.appchat.Fragmentos.FragmentUsuarios;
import com.proyectofinal.appchat.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        firebaseAuth = FirebaseAuth.getInstance();
        
        if( firebaseAuth.getCurrentUser() == null) {
            OpcionesLogin();
        }

        verPerfil();


        binding.bottomNV.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if(itemId == R.id.item_perfil){
                    // visualizar perfil
                    verPerfil();
                    return true;

                } else if (itemId == R.id.item_usuarios) {
                    // visualizar usuarios
                    verUsuarios();
                    return true;

                } else if (itemId == R.id.item_chats) {
                    // visualizar chats
                    verChats();
                    return true;

                } else {

                    return false;
                }
            }
        });
    }

    private void OpcionesLogin() {

        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity();
    }

    private void verPerfil(){

        binding.tvTitulo.setText("Perfil");

        FragmentPerfil perfil = new FragmentPerfil();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentosFL.getId(), perfil, "FragmentoPerfil");
        fragmentTransaction.commit();
    }

    private void verUsuarios(){

        binding.tvTitulo.setText("Perfil");

        FragmentUsuarios usuarios = new FragmentUsuarios();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentosFL.getId(), usuarios, "FragmentoUsuarios");
        fragmentTransaction.commit();

    }

    private void verChats(){

        binding.tvTitulo.setText("Perfil");

        FragmentChat chat = new FragmentChat();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentosFL.getId(), chat, "FragmentoChats");
        fragmentTransaction.commit();

    }
}