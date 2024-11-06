package com.proyectofinal.appchat.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyectofinal.appchat.BuscarChat;
import com.proyectofinal.appchat.Chat.ChatActivity;
import com.proyectofinal.appchat.Constantes;
import com.proyectofinal.appchat.Modelos.Chats;
import com.proyectofinal.appchat.R;
import com.proyectofinal.appchat.databinding.ItemsChatsBinding;

import java.util.ArrayList;

public class AdaptadorChats extends RecyclerView.Adapter<AdaptadorChats.HolderChats> implements Filterable {

    private ItemsChatsBinding binding;
    private Context context;
    public ArrayList<Chats> chatsArrayList;
    private ArrayList<Chats> filtroList;
    private BuscarChat filtro;
    private FirebaseAuth firebaseAuth;
    private String miUid;
    public AdaptadorChats(Context context, ArrayList<Chats> chatsArrayList) {
        this.context = context;
        this.chatsArrayList = chatsArrayList;
        this.filtroList = chatsArrayList;
        firebaseAuth = FirebaseAuth.getInstance();
        miUid = firebaseAuth.getUid();
    }

    @NonNull
    @Override
    public HolderChats onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemsChatsBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderChats(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderChats holder, int position) {
        Chats modeloChats = chatsArrayList.get(position);
        
        cargarUltimoMensaje(modeloChats, holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uidRecibimos = modeloChats.getUidRecibidos();
                if(uidRecibimos != null){
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("uid", uidRecibimos);
                    context.startActivity(intent);
                }
            }
        });
    }

    private void cargarUltimoMensaje(Chats modeloChats, HolderChats holder) {
        String chatKey = modeloChats.getKeyChat();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.child(chatKey).limitToLast(1)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String emisorUid = "" + ds.child("emisorUid").getValue();
                            String idMensaje = "" + ds.child("idMensaje").getValue();
                            String mensaje = "" + ds.child("mensaje").getValue();
                            String receptorUid = "" + ds.child("receptorUid").getValue();
                            long tiempo =(Long) ds.child("tiempo").getValue();
                            String tipoMensaje = "" + ds.child("tipoMensaje").getValue();

                            String formatoTiempo = Constantes.formatoFechaHora(tiempo);

                            modeloChats.setEmisorUid(emisorUid);
                            modeloChats.setIdMensaje(idMensaje);
                            modeloChats.setMensaje(mensaje);
                            modeloChats.setReceptorUid(receptorUid);
                            modeloChats.setTiempo(tiempo);
                            modeloChats.setTipoMensaje(tipoMensaje);

                            holder.tvFecha.setText(formatoTiempo);

                            if(tipoMensaje.equals(Constantes.MENSAJE_TIPO_TEXTO)){
                                holder.tvUltimoMensaje.setText(mensaje);
                            }else{
                                holder.tvUltimoMensaje.setText("Se ha enviado una imagen");
                            }

                        }

                        cargarInfoUsuarioRecibimos(modeloChats, holder);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void cargarInfoUsuarioRecibimos(Chats modeloChats, HolderChats holder) {
        String emisroUid = modeloChats.getEmisorUid();
        String receptorUid = modeloChats.getReceptorUid();

        String uidRecibimos;

        if(emisroUid.equals(miUid)){
            uidRecibimos = receptorUid;
        }else {
            uidRecibimos = emisroUid;
        }

        modeloChats.setUidRecibidos(uidRecibimos);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        reference.child(uidRecibimos)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String nombre = "" + snapshot.child("nombre").getValue();
                        String imagen = "" + snapshot.child("imagen").getValue();

                        modeloChats.setNombre(nombre);
                        modeloChats.setIdMensaje(imagen);

                        holder.tvNombres.setText(nombre);

                        try{
                            Glide.with(context)
                                    .load(imagen)
                                    .placeholder(R.drawable.icon_imagen_perfil)
                                    .into(holder.IvPerfil);

                        }catch (Exception e){

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    @Override
    public int getItemCount() {
        return chatsArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filtro ==null){
            filtro = new BuscarChat(this, filtroList);
        }
        return filtro;
    }

    class HolderChats extends RecyclerView.ViewHolder{

        ShapeableImageView IvPerfil;
        TextView tvNombres, tvUltimoMensaje, tvFecha;

        public HolderChats(@NonNull View itemView) {
            super(itemView);
            IvPerfil = binding.ivPerfil;
            tvNombres = binding.tvNombres;
            tvUltimoMensaje = binding.tvUltimoMensaje;
            tvFecha = binding.tvFecha;
        }
    }
}
