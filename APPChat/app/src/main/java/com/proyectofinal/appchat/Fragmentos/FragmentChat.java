package com.proyectofinal.appchat.Fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyectofinal.appchat.Adaptadores.AdaptadorChat;
import com.proyectofinal.appchat.Adaptadores.AdaptadorChats;
import com.proyectofinal.appchat.Modelos.Chats;
import com.proyectofinal.appchat.R;
import com.proyectofinal.appchat.databinding.FragmentChatBinding;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;


public class FragmentChat extends Fragment {

    private FragmentChatBinding binding;
    private FirebaseAuth firebaseAuth;
    private String miUid;
    private Context context;
    private ArrayList<Chats> chatsArrayList;
    private AdaptadorChats adaptadorChats;

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

    public FragmentChat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        miUid = firebaseAuth.getUid();

        binding.etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence filtro, int start, int before, int count) {
                try {
                    String consulta = filtro.toString();
                    adaptadorChats.getFilter().filter(consulta);
                }catch (Exception e) {
                    
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        
        cargarChats();
        return binding.getRoot();
    }

    private void cargarChats() {

        chatsArrayList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatsArrayList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    String chatKey = "" + ds.getKey();

                    if(chatKey.contains(miUid)){
                        Chats modelChats = new Chats();
                        modelChats.setKeyChat(chatKey);
                        chatsArrayList.add(modelChats);
                    }

                }

                adaptadorChats = new AdaptadorChats(context, chatsArrayList);
                binding.chatsRv.setAdapter(adaptadorChats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}