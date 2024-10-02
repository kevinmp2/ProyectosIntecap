package com.proyectofinal.appchat;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class Constantes {

    public static long obtenerTiempo(){
        return System.currentTimeMillis();
    }

    public static String formatoFecha(Long tiempo) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(tiempo);

        String fecha = DateFormat.format("dd/MM/yyyy", calendar).toString();

        return fecha;
    }
}
