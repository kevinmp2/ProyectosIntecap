package com.proyectofinal.appchat;

import android.text.format.DateFormat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class Constantes {
    public static final String MENSAJE_TIPO_TEXTO = "TEXTO";
    public static final String MENSAJE_TIPO_IMAGEN = "IMAGEN";

    public static long obtenerTiempo(){
        return System.currentTimeMillis();
    }

    public static String formatoFecha(Long tiempo) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(tiempo);

        String fecha = DateFormat.format("dd/MM/yyyy", calendar).toString();

        return fecha;
    }

    public static String formatoFechaHora(Long tiempo) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(tiempo);

        String fecha = DateFormat.format("dd/MM/yyyy hh:mm:a", calendar).toString();

        return fecha;
    }

    public static  String chatRuta(String receptorUid, String emisorUid){
        String[] arrayUid = new String[]{receptorUid, emisorUid};
        Arrays.sort(arrayUid);
        String chatRuta = arrayUid[0] + "_" + arrayUid[1];

        return chatRuta;
    }
}
