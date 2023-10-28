package com.A3.Curso.Service;

import com.A3.Curso.DTO.Classes.ClassesDTO;
import com.A3.Curso.DTO.Professors.ProfessorDTO;
import com.A3.Curso.DTO.Professors.ProfessorResponseDTO;
import com.A3.Curso.Enum.Degree;
import com.A3.Curso.Model.Classes;
import com.A3.Curso.Model.Professor;
import com.A3.Curso.Repository.ClassesRepository;
import com.A3.Curso.Repository.ProfessorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ClassesRepository classesRepository;

    public ProfessorService(ProfessorRepository professorRepository,
                            ClassesRepository classesRepository) {
        this.professorRepository = professorRepository;
        this.classesRepository = classesRepository;
    }

    private ProfessorResponseDTO professorResponseDTO = new ProfessorResponseDTO();
    private ProfessorDTO professorDTO = new ProfessorDTO();
    private ClassesDTO classesDTO = new ClassesDTO();

    public Model getProfessors(Model model) {
        model.addAttribute("Professors", professorResponseDTO.professorToDTOList(professorRepository.findAll()));
        model.addAttribute("Degree", Degree.values());
        return model;
    }

    public ResponseEntity<ProfessorDTO> getProfessor(long id) {
        Optional<Professor> professor = professorRepository.findById(id);
        if (professor.isPresent()) {
            return ResponseEntity.status(200).body(professorDTO.ProfessorToDTO(professor.get()));
        }
        return ResponseEntity.status(500).build();
    }

    public ResponseEntity<Map<String, Boolean>> putProfessor(long id, ProfessorDTO professorDTO) {
        Optional<String> email = professorRepository.emailExists(professorDTO.getEmail(), id);
        Optional<String> cpf = professorRepository.cpfExists(professorDTO.getEmail(),id);
        Optional<Professor> professor = professorRepository.findById(id);

        if (professor.isPresent()) {
            if (!email.isPresent() && !cpf.isPresent()) {
                professorRepository.save(professorDTO.DTOToProfessorUpdate(professor.get()));
                return ResponseEntity.status(200).build();
            } return ResponseEntity.status(409).body(StudentService.validateEmailAndCpf(email,cpf));
        }
        return ResponseEntity.status(500).build();
    }

    public ResponseEntity<Map<String, Boolean>> addProfessor(ProfessorDTO professorDTO) {
        Optional<String> email = professorRepository.emailExists(professorDTO.getEmail(),0);
        Optional<String> cpf = professorRepository.cpfExists(professorDTO.getEmail(),0);

        if (!email.isPresent() && !cpf.isPresent()) {
            professorRepository.save(professorDTO.DTOToProfessor());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(409).body(StudentService.validateEmailAndCpf(email,cpf));
    }

    public ModelAndView professorDetails(long id, Model model) {
        List<Classes> classes = classesRepository.findClassesByProfessorId(id);
        Optional<Professor> professor = professorRepository.findById(id);

        if (professor.isPresent()) {
            model.addAttribute("Professor", professorDTO.ProfessorToDTO(professor.get()));
            model.addAttribute("Classes", classesDTO.classesToDTOList(classes));
            return new ModelAndView("professorDetails");
        }
        return new ModelAndView("error");
    }

    public ResponseEntity<String> deleteProfessor(long id) {
        try {
            professorRepository.deleteById(id);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            return ResponseEntity.status(409).build();
        }
    }

}
