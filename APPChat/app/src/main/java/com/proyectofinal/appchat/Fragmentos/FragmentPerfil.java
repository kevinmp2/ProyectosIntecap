package com.proyectofinal.appchat.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyectofinal.appchat.CambiarPassword;
import com.proyectofinal.appchat.Constantes;
import com.proyectofinal.appchat.EditarInformacion;
import com.proyectofinal.appchat.LoginActivity;
import com.proyectofinal.appchat.R;
import com.proyectofinal.appchat.databinding.FragmentPerfilBinding;

public class FragmentPerfil extends Fragment {

    private FragmentPerfilBinding binding;

    private FirebaseAuth firebaseAuth;

    private Context Fcontext;

    @Override
    public void onAttach(@NonNull Context context) {
        Fcontext = context;
        super.onAttach(context);
    }

    public FragmentPerfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPerfilBinding.inflate(LayoutInflater.from(Fcontext), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        cargarInformacion();

        binding.btActualizarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Fcontext, EditarInformacion.class));
            }
        });

        binding.cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                startActivity(new Intent(Fcontext, LoginActivity.class));
                getActivity().finishAffinity();
            }
        });

        binding.btnCambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Fcontext, CambiarPassword.class));
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
                        String email = "" + snapshot.child("email").getValue();
                        String proveedor = "" + snapshot.child("Proveedor").getValue();
                        String tiempo_registro = "" + snapshot.child("TiempoRegistro").getValue();
                        String image = "" + snapshot.child("imagen").getValue();

                        if (tiempo_registro.equals("null")){
                            tiempo_registro = "0";
                        }

                        String fecha = Constantes.formatoFecha(Long.parseLong(tiempo_registro));

                        binding.tvNombre.setText(nombre);
                        binding.tvEmail.setText(email);
                        binding.tvProveedor.setText(proveedor);
                        binding.tvRegistro.setText(fecha);

                        Glide.with(Fcontext)
                                .load(image)
                                .placeholder(R.drawable.icon_img_perfil)
                                .into(binding.ivPerfil);

                        if(proveedor.equals("Email")){
                            binding.btnCambiarPass.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}