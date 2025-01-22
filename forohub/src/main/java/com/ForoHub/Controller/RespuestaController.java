package com.ForoHub.Controller;

import com.ForoHub.Entity.Respuesta;
import com.ForoHub.Repository.RespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @GetMapping
    public List<Respuesta> getAllRespuestas() {
        return respuestaRepository.findAll();
    }

    @PostMapping
    public Respuesta createRespuesta(@RequestBody Respuesta respuesta) {
        return respuestaRepository.save(respuesta);
    }

    @GetMapping("/{id}")
    public Respuesta getRespuestaById(@PathVariable Long id) {
        return respuestaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Respuesta not found"));
    }

    @PutMapping("/{id}")
    public Respuesta updateRespuesta(@PathVariable Long id, @RequestBody Respuesta respuestaDetails) {
        Respuesta respuesta = respuestaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Respuesta not found"));
        respuesta.setMensaje(respuestaDetails.getMensaje());
        respuesta.setFechaCreacion(respuestaDetails.getFechaCreacion());
        respuesta.setTopico(respuestaDetails.getTopico());
        respuesta.setAutor(respuestaDetails.getAutor());
        respuesta.setSolucion(respuestaDetails.getSolucion());
        return respuestaRepository.save(respuesta);
    }

    @DeleteMapping("/{id}")
    public void deleteRespuesta(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Respuesta not found"));
        respuestaRepository.delete(respuesta);
    }
}
