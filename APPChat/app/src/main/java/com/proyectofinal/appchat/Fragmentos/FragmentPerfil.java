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

import com.google.firebase.auth.FirebaseAuth;
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

        binding.cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                startActivity(new Intent(Fcontext, LoginActivity.class));
                getActivity().finishAffinity();
            }
        });
    }
}