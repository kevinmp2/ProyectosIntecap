package com.bibliotecadigital.biblioteca.controller;

import com.bibliotecadigital.biblioteca.model.LibroModel;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/libros")
public class LibroController {
    private List<LibroModel> listaLibros = new ArrayList<>();

    private Long generarNuevoId() {
        return listaLibros.isEmpty() ? 1L : listaLibros.get(listaLibros.size() - 1).getId() + 1;
    }

    @GetMapping
    public String listarLibros(Model model) {
        model.addAttribute("libros", listaLibros);
        return "libros";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoLibro(Model model) {
        model.addAttribute("libro", new LibroModel());
        return "nuevo-libro";
    }

    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute("libro") LibroModel libro) {
        libro.setId(generarNuevoId());
        listaLibros.add(libro);
        return "redirect:/libros";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable("id") Long id) {
        listaLibros.removeIf(libro -> libro.getId().equals(id));
        return "redirect:/libros";
    }
}
