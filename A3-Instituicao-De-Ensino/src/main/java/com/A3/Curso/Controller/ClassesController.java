package com.A3.Curso.Controller;

import com.A3.Curso.DTO.Classes.ClassesDTO;
import com.A3.Curso.DTO.Classes.ClassesProfessorResponseDTO;
import com.A3.Curso.DTO.Classes.SearchDTO;
import com.A3.Curso.Service.ClassesService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@Controller
@RequestMapping("/admin")
public class ClassesController {

    private final ClassesService classesService;

    public ClassesController(ClassesService classesService) {
        this.classesService = classesService;
    }


    @GetMapping("/class")
    public ModelAndView getClasses(Model model) {
        classesService.getClasses(model);
        return new ModelAndView("class");
    }

    @PostMapping("/class/add")
    public ResponseEntity<String> addClass(@RequestBody ClassesDTO classesDTO) {
        return classesService.addClass(classesDTO);
    }


    @PostMapping("/class/add/student/{classId}/{studentId}")
    public ResponseEntity<String> addStudentsToClass(@PathVariable("classId") long classId, @PathVariable("studentId") long studentId) {
        return classesService.addStudentsToClass(classId, studentId);
    }

    @GetMapping("/class/update/{id}")
    public ResponseEntity<ClassesProfessorResponseDTO> getClass(@PathVariable long id) {
        return classesService.getClass(id);
    }

    @PutMapping("/class/update/{id}")
    public ResponseEntity<String> putClass(@PathVariable long id, @RequestBody ClassesDTO classesDTO) {
        return classesService.putClass(id, classesDTO);
    }

    @GetMapping("/class/details/{id}")
    public ModelAndView classDetails(@PathVariable long id, Model model) {
        return classesService.classDetails(id, model);
    }

    @DeleteMapping("/class/delete/{id}")
    public ResponseEntity<String> deleteClass(@PathVariable("id") long classId) {
        return classesService.deleteClass(classId);
    }

    @DeleteMapping("/class/remove/student/{classId}/{studentId}")
    public ResponseEntity<String> removeStudentOfClass(@PathVariable long classId, @PathVariable long studentId) {
        return classesService.removeStudentOfClass(classId, studentId);
    }

    @GetMapping("/class/search/student")
    public ResponseEntity<List<SearchDTO>> searchStudent(@RequestParam(name = "keyword") long keyword, @RequestParam(name = "time") String time) {
        return classesService.searchStudent(keyword, time);
    }
}
