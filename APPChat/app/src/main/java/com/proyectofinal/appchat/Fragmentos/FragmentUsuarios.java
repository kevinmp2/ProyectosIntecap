package com.proyectofinal.appchat.Fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.UserHandle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyectofinal.appchat.AdaptadorUsuario;
import com.proyectofinal.appchat.R;
import com.proyectofinal.appchat.Usuario;
import com.proyectofinal.appchat.databinding.FragmentUsuariosBinding;

import java.util.ArrayList;
import java.util.List;

public class FragmentUsuarios extends Fragment {

    private FragmentUsuariosBinding binding;

    private Context Fcontext;
    private FirebaseAuth firebaseAuth;
    AdaptadorUsuario adaptadorUsuario;
    List<Usuario> usuarioList;

    @Override
    public void onAttach(@NonNull Context context) {
        Fcontext = context;
        super.onAttach(context);
    }

    public FragmentUsuarios() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsuariosBinding.inflate(LayoutInflater.from(Fcontext), container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        binding.RecyclerViewUsuarios.setHasFixedSize(true);
        binding.RecyclerViewUsuarios.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        usuarioList = new ArrayList<>();

        binding.txtBuscarUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence usuario, int start, int before, int count) {
                buscarUsuario(usuario.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listarUsuarios();


        return binding.getRoot();
    }

    private void listarUsuarios() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        reference.orderByChild("nombre").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuarioList.clear();

                if(binding.txtBuscarUsuario.getText().toString().isEmpty()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Usuario usuario = ds.getValue(Usuario.class);
                        assert usuario != null;
                        assert  firebaseUser != null;

                        if(!usuario.getUID().equals(firebaseUser.getUid())){
                            usuarioList.add(usuario);
                        }

                        adaptadorUsuario = new AdaptadorUsuario(getActivity(), usuarioList);
                        binding.RecyclerViewUsuarios.setAdapter(adaptadorUsuario);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void buscarUsuario(String usuario){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        reference.orderByChild("nombre").startAt(usuario).endAt(usuario + "\uf8ff")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        usuarioList.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            Usuario usuario = ds.getValue(Usuario.class);
                            assert  usuario != null;
                            assert firebaseUser != null;

                            if(!usuario.getUID().equals(firebaseUser.getUid())){
                                usuarioList.add(usuario);
                            }

                            adaptadorUsuario = new AdaptadorUsuario(getActivity(), usuarioList);
                            binding.RecyclerViewUsuarios.setAdapter(adaptadorUsuario);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}