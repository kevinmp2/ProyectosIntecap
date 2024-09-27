package com.restwebservicies.WebServicies.controller;

import com.restwebservicies.WebServicies.model.ModelDiv;
import com.restwebservicies.WebServicies.model.ModelMult;
import com.restwebservicies.WebServicies.model.ModelRest;
import com.restwebservicies.WebServicies.model.ModelSum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/calculadora")
public class Controller {
    @GetMapping("/sumar")
    public int sumar(@RequestParam int numeroUno, @RequestParam int numeroDos) {
        ModelSum sum = new ModelSum();
        int resultado = sum.Suma(numeroUno, numeroDos);
        return resultado;
    }

    @GetMapping("/restar")
    public int restar(@RequestParam int numeroUno, @RequestParam int numeroDos) {
        ModelRest resta = new ModelRest();
        int resultado = resta.Resta(numeroUno, numeroDos);
        return resultado;
    }

    @GetMapping("/multiplicar")
    public int multiplicar(@RequestParam int numeroUno, @RequestParam int numeroDos) {
        ModelMult multiplicacion = new ModelMult();
        int resultado = multiplicacion.Multiplicacion(numeroUno, numeroDos);
        return resultado;
    }

    @GetMapping("/dividir")
    public int dividir(@RequestParam int numeroUno, @RequestParam int numeroDos) {
        ModelDiv division = new ModelDiv();
        int resultado = division.Division(numeroUno, numeroDos);

        if (resultado == -1) {
            return 0;
        } else {
            return resultado;
        }
    }
}
