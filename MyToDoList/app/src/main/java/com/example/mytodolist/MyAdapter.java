package com.example.mytodolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private List<Tarea> listTarea;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewNombre;
        public TextView textViewDescripcion;

        public Button buttonEliminar, buttonEditar;
        public MyViewHolder(View TareaView){
            super(TareaView);
            textViewNombre = TareaView.findViewById(R.id.nombre);
            textViewDescripcion = TareaView.findViewById(R.id.descripcion);
            buttonEliminar = itemView.findViewById(R.id.eliminar);
            buttonEditar = itemView.findViewById(R.id.editar);
        }
    }

    public MyAdapter(List<Tarea> listTarea, Context context){
        this.listTarea = listTarea;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarea_viewcard, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Tarea tarea = listTarea.get(position);
        holder.textViewNombre.setText(tarea.getNombre());
        holder.textViewDescripcion.setText(tarea.getDescripcion());

        // Manejar el clic del botón Eliminar
        holder.buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    eliminarTarea(currentPosition);
                }
            }
        });

        // Manejar el clic del botón Editar
        holder.buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    editarTarea(currentPosition);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTarea.size();
    }

    public void eliminarTarea(int position) {
        if (position >= 0 && position < listTarea.size()) {
            listTarea.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void editarTarea(int position) {
        Tarea tarea = listTarea.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_task, null);
        final EditText etTaskName = dialogView.findViewById(R.id.et_task_name);
        final EditText etTaskDescription = dialogView.findViewById(R.id.et_task_description);

        etTaskName.setText(tarea.getNombre());
        etTaskDescription.setText(tarea.getDescripcion());

        new AlertDialog.Builder(context)
                .setTitle("Editar Tarea")
                .setView(dialogView)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newTaskName = etTaskName.getText().toString();
                        String newTaskDescription = etTaskDescription.getText().toString();

                        if (!newTaskName.isEmpty() && !newTaskDescription.isEmpty()) {
                            tarea.setNombre(newTaskName);
                            tarea.setDescripcion(newTaskDescription);
                            notifyItemChanged(position);
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
