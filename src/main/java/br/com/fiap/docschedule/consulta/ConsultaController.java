package br.com.fiap.docschedule.consulta;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequestMapping("consultas")
@Slf4j
public class ConsultaController {

    @Autowired
    ConsultaRepository repository;

    @GetMapping
    public String index(Model model){
        List<Consulta> consultas = repository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<ConsultaDTO> consultasDTO = consultas.stream()
                .map(consulta -> new ConsultaDTO(
                        consulta.getId(),
                        consulta.getEspecialidade(),
                        consulta.getMotivo(),
                        consulta.getDataConsulta().format(formatter)))
                .collect(Collectors.toList());

        model.addAttribute("consultas", consultasDTO);
        return "consulta/index";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        var task = repository.findById(id);

        if(task.isEmpty()) {
            redirect.addFlashAttribute("message", "Erro ao apagar! Tarefa não encontrada.");
            return "redirect:/consultas";
        }

        repository.deleteById(id);
        redirect.addFlashAttribute("message", "Consulta apagada com sucesso!");
        return "redirect:/consultas";
    }

    @GetMapping("new")
    public String form() {
        return "consulta/form";
    }

    @PostMapping
    public String create(Consulta consulta, RedirectAttributes redirect) {
        log.info("Agendando nova consulta - " + consulta);
        repository.save(consulta);
        redirect.addFlashAttribute("message", "Consulta agendada com sucesso!");
        return "redirect:/consultas";
    }
    
}
