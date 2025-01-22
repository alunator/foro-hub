package com.ForoHub.Controller;

import com.ForoHub.Entity.Curso;
import com.ForoHub.Entity.Topico;
import com.ForoHub.Entity.Usuario;
import com.ForoHub.Repository.CursoRepository;
import com.ForoHub.Repository.TopicoRepository;
import com.ForoHub.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/topicos")
public class TopicoController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping
    public List<Topico> getAllTopicos() {
        return topicoRepository.findAll();
    }

    @PostMapping
    public Topico createTopico(@RequestBody Topico topico) {
        // Buscar el curso por ID
        Curso curso = cursoRepository.findById(topico.getCurso().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso not found"));

        // Establecer el curso en el tópico
        topico.setCurso(curso);

        // Asignar el autor
        Usuario autor = usuarioRepository.findById(topico.getAutor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario not found"));
        topico.setAutor(autor);

        // Establecer la fecha de creación 
        topico.setFechaCreacion(LocalDate.now());
        topico.setStatus("Activo"); // O cualquier otro valor que desees por defecto

        // Guardar el nuevo tópico
        return topicoRepository.save(topico);
    }

    @GetMapping("/{id}")
    public Topico getTopicoById(@PathVariable Long id) {
        return topicoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topico not found"));
    }

    @PutMapping("/{id}")
    public Topico updateTopico(@PathVariable Long id, @RequestBody Topico topicoDetails) {
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topico not found"));
        topico.setTitulo(topicoDetails.getTitulo());
        topico.setMensaje(topicoDetails.getMensaje());
        topico.setFechaCreacion(topicoDetails.getFechaCreacion());
        topico.setStatus(topicoDetails.getStatus());
        topico.setAutor(topicoDetails.getAutor());
        topico.setCurso(topicoDetails.getCurso());
        return topicoRepository.save(topico);
    }

    @DeleteMapping("/{id}")
    public void deleteTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topico not found"));
        topicoRepository.delete(topico);
    }
}
