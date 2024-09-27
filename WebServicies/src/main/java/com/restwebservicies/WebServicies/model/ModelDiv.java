package com.restwebservicies.WebServicies.model;

public class ModelDiv {

    private int numeroUnos;
    private int numeroDos;



    public ModelDiv() {
    }

    public ModelDiv(int numeroUnos, int numeroDos) {
        this.numeroUnos = numeroUnos;
        this.numeroDos = numeroDos;
    }

    public int getNumeroUnos() {
        return numeroUnos;
    }

    public void setNumeroUnos(int numeroUnos) {
        this.numeroUnos = numeroUnos;
    }

    public int getNumeroDos() {
        return numeroDos;
    }

    public void setNumeroDos(int numeroDos) {
        this.numeroDos = numeroDos;
    }

    public int Division(int numeroUno, int numeroDos){

        int resultado;
        if(numeroDos == 0) {
            return -1;
        }else {
            resultado =  numeroUno / numeroDos;
        }

        return resultado;
    }
}
