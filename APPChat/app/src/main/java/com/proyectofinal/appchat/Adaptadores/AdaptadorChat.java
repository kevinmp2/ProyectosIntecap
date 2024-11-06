package com.proyectofinal.appchat.Adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyectofinal.appchat.Constantes;
import com.proyectofinal.appchat.Modelos.Chat;
import com.proyectofinal.appchat.R;

import java.util.ArrayList;

public class AdaptadorChat extends RecyclerView.Adapter<AdaptadorChat.HolderChat>{

    private Context context;
    private ArrayList<Chat> chatArrayList;
    private static final int MENSAJE_IZQUIERDO = 0;
    private static final int MENSAJE_DERECHO = 1;
    private FirebaseUser firebaseUser;
    String chatRuta = "";

    public AdaptadorChat(Context context, ArrayList<Chat> chatArrayList) {
        this.context = context;
        this.chatArrayList = chatArrayList;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public HolderChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MENSAJE_DERECHO){
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_derecho, parent, false);
            return new HolderChat(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_izquierda, parent, false);
            return new HolderChat(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HolderChat holder, int position) {
        Chat chat = chatArrayList.get(position);

        String mensaje = chat.getMensaje();
        String tipoMensaje = chat.getTipoMensaje();
        long tiempo = chat.getTiempo();

        String formatoFechaHora = Constantes.formatoFechaHora(tiempo);

        if(tipoMensaje.equals(Constantes.MENSAJE_TIPO_TEXTO)){
            holder.Tv_mensaje.setVisibility(View.VISIBLE);
            holder.Iv_mensaje.setVisibility(View.GONE);

            holder.Tv_mensaje.setText(mensaje);
            
            if(chat.getEmisorUid().equals(firebaseUser.getUid())){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String []opciones = {"Eliminar mensaje", "Cancelar"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                        builder.setTitle("Que desea hacer?");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0){
                                    eliminarMensaje(position,  holder, chat);
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }
        }else{
            holder.Tv_mensaje.setVisibility(View.GONE);
            holder.Iv_mensaje.setVisibility(View.VISIBLE);

            try {
                Glide.with(context)
                        .load(mensaje)
                        .placeholder(R.drawable.enviando)
                        .error(R.drawable.error)
                        .into(holder.Iv_mensaje);

            }catch (Exception e){

            }

            if(chat.getEmisorUid().equals(firebaseUser.getUid())){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String []opciones = {"Eliminar imagen", "Cancelar"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                        builder.setTitle("Que desea hacer?");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0){
                                    eliminarMensaje(position,holder,chat);
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }
        }

        holder.Tv_tiempo_mensaje.setText(formatoFechaHora);
    }

    private void eliminarMensaje(int position, HolderChat holder, Chat chat) {
        chatRuta = Constantes.chatRuta(chat.getReceptorUid(), chat.getEmisorUid());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.child(chatRuta).child(chatArrayList.get(position).getIdMensaje())
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(holder.itemView.getContext(), "El mensaje se ha eliminado", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(holder.itemView.getContext(), "El mensaje no se ha eliminado " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return chatArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(chatArrayList.get(position).getEmisorUid().equals(firebaseUser.getUid())){
            return MENSAJE_DERECHO;
        }else{
            return MENSAJE_IZQUIERDO;
        }
    }

    class HolderChat extends RecyclerView.ViewHolder{
        TextView Tv_mensaje, Tv_tiempo_mensaje;
        ShapeableImageView Iv_mensaje;

        public HolderChat(@NonNull View itemView) {
            super(itemView);
            Tv_mensaje = itemView.findViewById(R.id.Tv_mensaje);
            Tv_tiempo_mensaje = itemView.findViewById(R.id.tv_tiempo_mensaje);
            Iv_mensaje = itemView.findViewById(R.id.Iv_mensaje);
        }
    }


}
