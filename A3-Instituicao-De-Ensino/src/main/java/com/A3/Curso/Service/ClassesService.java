package com.A3.Curso.Service;

import com.A3.Curso.DTO.Classes.ClassesDTO;
import com.A3.Curso.DTO.Classes.ClassesProfessorResponseDTO;
import com.A3.Curso.DTO.Classes.SearchDTO;
import com.A3.Curso.DTO.Professors.ProfessorClassResponseDTO;
import com.A3.Curso.DTO.Students.StudentResponseDTO;
import com.A3.Curso.Enum.ClassDay;
import com.A3.Curso.Enum.ClassRoom;
import com.A3.Curso.Enum.Time;
import com.A3.Curso.Model.Classes;
import com.A3.Curso.Model.Professor;
import com.A3.Curso.Model.Student;
import com.A3.Curso.Repository.ClassesRepository;
import com.A3.Curso.Repository.ProfessorRepository;
import com.A3.Curso.Repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassesService {
    private final ClassesRepository classRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    public ClassesService(ClassesRepository classRepository, StudentRepository studentRepository, ProfessorRepository professorRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
    }

    private ProfessorClassResponseDTO professorClassResponseDTO = new ProfessorClassResponseDTO();
    private ClassesProfessorResponseDTO classesResponseDTO = new ClassesProfessorResponseDTO();
    private StudentResponseDTO studentResponseDTO = new StudentResponseDTO();

    public Model getClasses(Model model) {
        model.addAttribute("ClassTime", Time.values());
        model.addAttribute("ClassDay", ClassDay.values());
        model.addAttribute("ClassRoom", ClassRoom.values());
        model.addAttribute("Professors", professorClassResponseDTO.professorToDTOList(professorRepository.findAll()));
        model.addAttribute("Classes", classesResponseDTO.classesToDTOList(classRepository.findAll()));
        return model;
    }

    public ResponseEntity<ClassesProfessorResponseDTO> getClass(long id) {
        Optional<Classes> classes = classRepository.findById(id);
        if (classes.isPresent()) {
            return ResponseEntity.status(200).body(classesResponseDTO.classesToDTO(classes.get()));
        }
        return ResponseEntity.status(500).build();
    }

    public ResponseEntity<String> removeStudentOfClass(long classId, long studentId) {
        try {
            classRepository.removeStudentFromClass(studentId, classId);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).build();
    }

    public ResponseEntity<String> deleteClass(long classId) {
        Optional<Classes> classes = classRepository.findById(classId);

        if (classes.isPresent()) {
            List<Student> students = classes.get().getStudents();
            if (!students.isEmpty()) classRepository.removeAllStudentsFromClass(classId);
            classRepository.deleteById(classId);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(500).build();
    }

    public ModelAndView classDetails(long id, Model model) {
        Optional<Classes> classes = classRepository.findById(id);
        if (classes.isPresent()) {
            model.addAttribute("class", classesResponseDTO.classesToDTO(classes.get()));
            model.addAttribute("Students", studentResponseDTO.studentsToDTOList(studentRepository.findAllByClassesId(id)));
        }
        return new ModelAndView("classDetails");
    }

    public ResponseEntity<String> putClass(long id, ClassesDTO classesDTO) {
        Optional<Classes> classes = classRepository.findById(id);
        Professor professor = professorRepository.findById(classesDTO.getProfessor()).get();
        if (classes.isPresent()) {
            classRepository.save(classesDTO.DTOToClass(classes.get(), professor));
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(500).build();
    }

    public ResponseEntity<String> addClass(ClassesDTO classesDTO) {
        Optional<Professor> professor = professorRepository.findById(classesDTO.getProfessor());
        if (professor.isPresent()) {
            classRepository.save(classesDTO.DTOToClass(professor.get()));
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(500).build();
    }

    public ResponseEntity<String> addStudentsToClass(long classId, long studentId) {
        Classes classes = classRepository.findById(classId).get();
        if (classRepository.studentExistInClass(studentId, classId) != null) {
            return ResponseEntity.status(409).build();
        }
        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isPresent()) {
            student.get().getClasses().add(classes);
            studentRepository.save(student.get());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(500).build();
    }

    public ResponseEntity<List<SearchDTO>> searchStudent(long keyword, String time) {
        String shift = Time.getTime(time);
        List<Student> students = studentRepository.findByKeyword(keyword, shift);
        List<SearchDTO> searchDTO = students
                .stream()
                .map(Student -> new SearchDTO(Student.getId(), Student.getName()))
                .collect(Collectors.toList());

        if (students.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(searchDTO);
    }

}
