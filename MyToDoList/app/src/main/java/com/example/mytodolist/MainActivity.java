package com.example.mytodolist;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button bt_eleminar, bt_editar, bt_agregar;

    List<Tarea> listTarea = new ArrayList<>();

    MyAdapter adapter = new MyAdapter(listTarea, this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bt_agregar = findViewById(R.id.Bt_Agregar);

        RecyclerView recyclerView = findViewById(R.id.recicleViewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bt_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarTarea();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public void agregarTarea() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_task, null);
        final EditText etTaskName = dialogView.findViewById(R.id.et_task_name);
        final EditText etTaskDescription = dialogView.findViewById(R.id.et_task_description);

        new AlertDialog.Builder(this)
                .setTitle("Agregar Tarea")
                .setView(dialogView)
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String taskName = etTaskName.getText().toString();
                        String taskDescription = etTaskDescription.getText().toString();

                        if (!taskName.isEmpty() && !taskDescription.isEmpty()) {
                            listTarea.add(new Tarea(taskName, taskDescription));
                            adapter.notifyItemInserted(listTarea.size() - 1);
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}