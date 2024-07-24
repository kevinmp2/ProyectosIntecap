package com.example.appmovie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Resultado {

    @SerializedName("pages")
    @Expose
    private int paginas;
    @SerializedName("total_results")
    @Expose
    private int totalPaginas;
    @SerializedName("total_pages")
    @Expose
    private int totalResultados;
    @SerializedName("results")
    @Expose
    private List<Movie> resultados = null;

    public Resultado(){

    }
    public Resultado(int paginas, int totalPaginas, int totalResultados, List<Movie> resultados) {
        this.paginas = paginas;
        this.totalPaginas = totalPaginas;
        this.totalResultados = totalResultados;
        this.resultados = resultados;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public Integer getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public Integer getTotalResultados() {
        return totalResultados;
    }

    public void setTotalResultados(int totalResultados) {
        this.totalResultados = totalResultados;
    }

    public List<Movie> getResultados() {
        return resultados;
    }

    public void setResultados(List<Movie> resultados) {
        this.resultados = resultados;
    }
}
