package com.A3.Curso.Controller;

import com.A3.Curso.DTO.Students.StudentDTO;
import com.A3.Curso.DTO.Students.StudentResponseDTO;
import com.A3.Curso.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/student")
    public ModelAndView getStudents(Model model) {
        studentService.getStudents(model);
        return new ModelAndView("students");
    }

    @PostMapping("/student/add")
    public ResponseEntity<Map<String, Boolean>> addStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.addStudent(studentDTO);
    }

    @GetMapping("/student/update/{id}")
    public ResponseEntity<StudentResponseDTO> getStudent(@PathVariable long id) {
        return studentService.getStudent(id);
    }

    @PutMapping("/student/update/{id}")
    public ResponseEntity<Map<String, Boolean>> putStudent(@PathVariable long id, @RequestBody StudentDTO studentDTO) {
        return studentService.putStudent(id, studentDTO);
    }

    @GetMapping("/student/details/{idStudent}")
    public ModelAndView studentDetails(@PathVariable long idStudent, Model model) {
        return studentService.studentDetails(idStudent, model);

    }

    @DeleteMapping("/student/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable long id) {
        return studentService.deleteStudent(id);
    }

}
