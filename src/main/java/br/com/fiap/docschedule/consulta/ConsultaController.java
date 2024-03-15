package br.com.fiap.docschedule.consulta;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("consultas")
public class ConsultaController {

    @GetMapping
    public String index(Model model){
        model.addAttribute("consultas", List.of(
            "Cardiologista",
            "Clínico Geral",
            "Neurologista",
            "Dermatologista"
        ));
        return "consulta/index";
    }
    
}
