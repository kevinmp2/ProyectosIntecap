package com.proyectofinal.appchat;

import android.widget.Filter;

import com.proyectofinal.appchat.Adaptadores.AdaptadorChats;
import com.proyectofinal.appchat.Modelos.Chats;

import java.util.ArrayList;

public class BuscarChat extends Filter {

    private AdaptadorChats adaptadorChats;
    private ArrayList<Chats> filtroList;

    public BuscarChat(AdaptadorChats adaptadorChats, ArrayList<Chats> filtroList) {
        this.adaptadorChats = adaptadorChats;
        this.filtroList = filtroList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence filtro) {

        FilterResults resultados = new FilterResults();

        if(filtro != null && filtro.length() > 0){
            filtro = filtro.toString().toUpperCase();
            ArrayList<Chats> filtroModelo = new ArrayList<>();
            for(int i=0; i < filtroList.size(); i++){
                if(filtroList.get(i).getNombre().toUpperCase().contains(filtro)){
                    filtroModelo.add(filtroList.get(i));
                }
            }
            resultados.count = filtroModelo.size();
            resultados.values = filtroModelo;
        }else{
            resultados.count = filtroList.size();
            resultados.values = filtroList;
        }


        return resultados;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults resultados) {
        adaptadorChats.chatsArrayList = (ArrayList<Chats>) resultados.values;
        adaptadorChats.notifyDataSetChanged();
    }
}
