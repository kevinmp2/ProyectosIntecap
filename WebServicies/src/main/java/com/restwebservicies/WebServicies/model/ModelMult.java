package com.restwebservicies.WebServicies.model;

public class ModelMult {

    private int numeroUno;
    private int numeroDos;

    public ModelMult() {
    }

    public ModelMult(int numeroUno, int numeroDos) {
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

    public int Multiplicacion(int numeroUno,int numeroDos){
        return numeroUno * numeroDos;
    }
}
