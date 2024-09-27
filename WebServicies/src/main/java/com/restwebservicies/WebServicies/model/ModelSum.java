package com.restwebservicies.WebServicies.model;

public class ModelSum {

    private int numeroUno;
    private int numeroDos;

    public ModelSum(){
    }

    public ModelSum(int numeroUno, int numeroDos) {
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


    public int Suma(int numeroUno, int numeroDos){
        return numeroUno + numeroDos;
    }
}
