package br.com.fiap.docschedule.consulta;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import br.com.fiap.docschedule.user.User;
import br.com.fiap.docschedule.user.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsultaService {

    @Autowired
    ConsultaRepository repository;
    
    @Autowired
    UserRepository userRepository;

    public List<ConsultaDTO> findAllConsultas(DefaultOAuth2User user) {
        User loggedUser = new User(user);
        List<Consulta> consultas = repository.findAll();
        log.info("Consultas encontradas para o usuário {}: {}", loggedUser.getId(), consultas);

        if (consultas.isEmpty()) {
            log.info("Nenhuma consulta encontrada para o usuário {}", loggedUser.getId());
            return Collections.emptyList();
        } else {
            return convertConsultasToDto(consultas);
        }
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
        log.info("Consulta deletada com ID: {}", id);
    }

    public void saveConsulta(Consulta consulta, DefaultOAuth2User user) {
        String email = user.getAttribute("email");
        Optional<User> optionalUser = userRepository.findByEmail(email);
    
        if (optionalUser.isPresent()) {
            consulta.setUser(optionalUser.get());
        } else {
            throw new RuntimeException("User not found for email: " + email);
        }
        
        log.info("Salvando consulta: {}", consulta);
        repository.save(consulta);
    }

    private List<ConsultaDTO> convertConsultasToDto(List<Consulta> consultas) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return consultas.stream()
                .map(consulta -> new ConsultaDTO(
                        consulta.getId(),
                        consulta.getEspecialidade(),
                        consulta.getMotivo(),
                        consulta.getDataConsulta().format(formatter)))
                .collect(Collectors.toList());
    }
}
