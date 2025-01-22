package com.ForoHub.Controller;

import com.ForoHub.Entity.Curso;
import com.ForoHub.Repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }

    @PostMapping
    public Curso createCurso(@RequestBody Curso curso) {
        return cursoRepository.save(curso);
    }

    @GetMapping("/{id}")
    public Curso getCursoById(@PathVariable Long id) {
        return cursoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Curso not found"));
    }

    @PutMapping("/{id}")
    public Curso updateCurso(@PathVariable Long id, @RequestBody Curso cursoDetails) {
        Curso curso = cursoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Curso not found"));
        curso.setNombre(cursoDetails.getNombre());
        curso.setCategoria(cursoDetails.getCategoria());
        return cursoRepository.save(curso);
    }

    @DeleteMapping("/{id}")
    public void deleteCurso(@PathVariable Long id) {
        Curso curso = cursoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Curso not found"));
        cursoRepository.delete(curso);
    }
}
