package com.proyectofinal.appchat.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.proyectofinal.appchat.Chat.ChatActivity;
import com.proyectofinal.appchat.R;
import com.proyectofinal.appchat.Usuario;

import java.util.List;

public class AdaptadorUsuario  extends  RecyclerView.Adapter<AdaptadorUsuario.ViewHolder>{


    private Context context;
    private List<Usuario> usuarioList;

    public AdaptadorUsuario(Context context, List<Usuario> usuarioList) {
        this.context = context;
        this.usuarioList = usuarioList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_usuarios, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String uid = usuarioList.get(position).getUID();
        String nombre = usuarioList.get(position).getNombre();
        String email = usuarioList.get(position).getEmail();
        String imagen = usuarioList.get(position).getImagen();

        holder.UID.setText(uid);
        holder.nombre.setText(nombre);
        holder.email.setText(email);

        try {
            Glide.with(context)
                    .load(imagen)
                    .placeholder(R.drawable.icon_imagen_perfil)
                    .into(holder.imagen);

        } catch (Exception e) {
            //Toast.makeText(context, "Error viewHolder: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("uid", holder.UID.getText());
                Toast.makeText(context, "Usuario selecionado" + holder.nombre.getText(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView UID, nombre, email;
        ImageView imagen;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            UID = itemView.findViewById(R.id.item_uid);
            nombre = itemView.findViewById(R.id.item_nombre);
            email = itemView.findViewById(R.id.item_email);
            imagen = itemView.findViewById(R.id.item_perfil);
        }
    }


}
