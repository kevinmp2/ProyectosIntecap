package com.restwebservicies.WebServicies.model;

public class ModelRest {

    private int numeroUno;
    private int numeroDos;

    public ModelRest(){

    }

    public ModelRest(int numeroUno, int numeroDos){
        this.numeroUno = numeroUno;
        this.numeroDos = numeroDos;
    }

    public int getNumeroUno() {
        return numeroUno;
    }

    public void setNumeroUno(int numeroUno) {
        this.numeroUno = numeroUno;
    }

    public int getNumeroDos() {
        return numeroDos;
    }

    public void setNumeroDos(int numeroDos) {
        this.numeroDos = numeroDos;
    }

    public int Resta(int numeroUno, int numeroDos){
        return numeroUno - numeroDos;
    }
}
