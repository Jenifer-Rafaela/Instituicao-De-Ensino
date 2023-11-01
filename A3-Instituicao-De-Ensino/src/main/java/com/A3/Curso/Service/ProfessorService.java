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


    /**
     * <strong>Método para disponibilizar informações dos professores na view (professors.html)</strong>
     * @param model onde será atribuído as informações dos professores.
     * @return model - para ser utilizado na view (professors.html).
     * */
    public Model getProfessors(Model model) {
        model.addAttribute("Professors", professorResponseDTO.professorToDTOList(professorRepository.findAll()));
        model.addAttribute("Degree", Degree.values());
        return model;
    }

    /**
     * <strong>Método para incluir um professor no banco de dados</strong>
     * @param professorDTO informações recebidas da view (professors.html).
     * @return ResponseEntity - com status 200, se inclusão foi feita.
     * @throws ResponseEntity com status 409, se email e/ou cpf já estiver em uso.
     * */
    public ResponseEntity<Map<String, Boolean>> addProfessor(ProfessorDTO professorDTO) {
        Optional<String> email = professorRepository.emailExists(professorDTO.getEmail(),0);
        Optional<String> cpf = professorRepository.cpfExists(professorDTO.getCpf(),0);

        if (!email.isPresent() && !cpf.isPresent()) {
            professorRepository.save(professorDTO.DTOToProfessor());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(409).body(StudentService.validateEmailAndCpf(email,cpf));
    }

    /**
     * <strong>Método para ver detalhes de um professor.</strong>
     * @param id id do professor a ser recuperado do banco de dados.
     * @param model onde será atribuído as informações do professor.
     * @return ModelAndView - redireciona para a página professorDetails.html.
     * @throws ModelAndView redireciona para a página error.html.
     * */
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

    /**
     * <strong>Método para pegar as informações do professor</strong>
     * @param id id do professor a ser recuperado do banco de dados.
     * @return ResponseEntity - com informações do professor para a view (professors.html) e status 200.
     * @throws ResponseEntity com status 500.
     * */
    public ResponseEntity<ProfessorDTO> getProfessor(long id) {
        Optional<Professor> professor = professorRepository.findById(id);
        if (professor.isPresent()) {
            return ResponseEntity.status(200).body(professorDTO.ProfessorToDTO(professor.get()));
        }
        return ResponseEntity.status(500).build();
    }

    /**
     * <strong>Método para atualizar um professor no banco de dados</strong>
     * @param id id do professor a ser atualizado no banco de dados.
     * @param professorDTO informações recebidas da view(professors.html) para atualização.
     * @return ResponseEntity - com status 200, se atualização foi feita.<br>
     * ResponseEntity - com status 409, se email e/ou cpf já estiver em uso.
     * @throws ResponseEntity com status 500.
     * */
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

    /**
     * <strong>Método para a excluir um professor do banco de dados</strong>
     * @param id id do professor que será excluído.
     * @return ResponseEntity - com status 200, se exclusão foi feita.
     * @throws ResponseEntity com status 409.
     * */
    public ResponseEntity<String> deleteProfessor(long id) {
        try {
            professorRepository.deleteById(id);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            return ResponseEntity.status(409).build();
        }
    }
}
