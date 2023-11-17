package com.A3.Trabalho.Controller;

import com.A3.Trabalho.DTO.Professors.ProfessorDTO;
import com.A3.Trabalho.Service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping("/professor")
    public ModelAndView getProfessors(Model model) {
        professorService.getProfessors(model);
        return new ModelAndView("professors");
    }

    @GetMapping("/professor/update/{id}")
    public ResponseEntity<ProfessorDTO> getProfessor(@PathVariable long id) {
        return professorService.getProfessor(id);
    }

    @PutMapping("/professor/update/{id}")
    public ResponseEntity<Map<String, Boolean>> putProfessor(@PathVariable long id, @RequestBody ProfessorDTO professorDTO) {
        return professorService.putProfessor(id, professorDTO);
    }

    @PostMapping("/professor/add")
    public ResponseEntity<Map<String, Boolean>> addProfessor(@RequestBody ProfessorDTO professorDTO) {
        return professorService.addProfessor(professorDTO);
    }

    @GetMapping("/professor/details/{id}")
    public ModelAndView professorDetails(@PathVariable long id, Model model) {
        return professorService.professorDetails(id, model);
    }

    @DeleteMapping("/professor/delete/{id}")
    public ResponseEntity<String> deleteProfessor(@PathVariable long id) {
        return professorService.deleteProfessor(id);
    }
}
